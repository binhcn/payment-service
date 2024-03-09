package dev.binhcn.service.impl;

import dev.binhcn.dto.Account;
import dev.binhcn.dto.Bill;
import dev.binhcn.enums.CommandEnum;
import dev.binhcn.enums.StateEnum;
import dev.binhcn.exception.ArgumentException;
import dev.binhcn.repository.AccountRepository;
import dev.binhcn.repository.BillRepository;
import dev.binhcn.service.IFlowHandler;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.List;

public class FlowHandler implements IFlowHandler {

    private static FlowHandler instance;

    private FlowHandler() {
    }

    public static IFlowHandler getInstance() {
        if (instance == null) {
            instance = new FlowHandler();
        }
        return instance;
    }

    @Override
    public void process(CommandEnum commandEnum, String[] arguments) {
        try {
            switch (commandEnum) {
                case CASH_IN:
                    if (arguments.length < 1) {
                        throw new ArgumentException(CommandEnum.CASH_IN, 1);
                    }

                    long amount;
                    try {
                        amount = Long.parseLong(arguments[0]);
                    } catch (NumberFormatException ex) {
                        System.out.printf("argument = '%s' is not numeric\n", arguments[0]);
                        return;
                    }

                    Account account = AccountRepository.getInstance().getAccount();
                    account.addBalance(amount);
                    System.out.printf("Your available balance: %d\n", account.getBalance());
                    break;

                case LIST_BILL:
                    List<Bill> bills = BillRepository.getInstance().getBills();
                    printBill(bills);
                    break;

                case PAY:
                    if (arguments.length < 1) {
                        throw new ArgumentException(CommandEnum.PAY, 1);
                    }

                    account = AccountRepository.getInstance().getAccount();
                    if (account.getBalance() < BillRepository.getInstance().getTotalBillAmount(arguments)) {
                        System.out.println("Sorry! Not enough fund to proceed with payment");
                        break;
                    }

                    for (String billId : arguments) {
                        Bill bill = BillRepository.getInstance().getBill(billId);
                        if (bill == null) {
                            System.out.printf("Sorry! Not found a bill with such id %s\n", billId);
                        } else if (bill.getState() == StateEnum.NOT_PAID) {
                            account.addBalance(-bill.getAmount());
                            bill.setState(StateEnum.PROCESSED);
                            bill.setPaymentDate(Date.valueOf(LocalDate.now()));
                            System.out.printf("Payment has been completed for Bill with id %s\n", billId);
                            System.out.printf("Your current balance is: %d\n", account.getBalance());
                        } else {
                            System.out.printf("Sorry! Bill status not NOT_PAID with id %s\n", billId);
                        }
                    }
                    break;

                case DUE_DATE:
                    bills = BillRepository.getInstance().getBillsNotPaid();
                    printBill(bills);
                    break;

                case SCHEDULE:
                    if (arguments.length < 2) {
                        throw new ArgumentException(CommandEnum.SCHEDULE, 2);
                    }

                    Bill bill = BillRepository.getInstance().getBill(arguments[0]);
                    if (bill != null) {
                        try {
                            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                            bill.setDueDate(dateFormat.parse(arguments[1]));
                            System.out.printf("Payment for bill id %s is scheduled on %s\n",
                                    bill.getId(), dateFormat.format(bill.getDueDate()));
                        } catch (java.text.ParseException ex) {
                            System.out.println("SCHEDULE command needs a Date argument with format dd/MM/yyyy");
                        }
                    } else {
                        System.out.printf("Sorry! Not found a bill with such id %s\n", arguments[0]);
                    }
                    break;

                case LIST_PAYMENT:
                    bills = BillRepository.getInstance().getBills();
                    printPayment(bills);
                    break;

                case SEARCH_BILL_BY_PROVIDER:
                    if (arguments.length < 1) {
                        throw new ArgumentException(CommandEnum.SEARCH_BILL_BY_PROVIDER, 1);
                    }

                    bills = BillRepository.getInstance().getBillsByProvider(arguments[0]);
                    printBill(bills);
                    break;

                case EXIT:
                    System.out.println("Good bye!");
                    break;
            }
        } catch (ArgumentException ex) {
            System.out.printf("%s command needs %s argument\n", ex.getCommand().name(), ex.getArgumentCount());
        }
    }

    private void printBill(List<Bill> bills) {
        System.out.println("Bill No.\tType\t\tAmount\tDue Date\tState\t\tProvider");
        for (Bill bill : bills) {
            System.out.println(bill.printBill());
        }
    }

    private void printPayment(List<Bill> bills) {
        System.out.println("No.\tAmount\tPayment Date\tState\t\tBill Id");
        for (int i = 0; i < bills.size(); i++) {
            System.out.println(bills.get(i).printPayment(i + 1));
        }
    }
}
