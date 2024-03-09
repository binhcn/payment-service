package dev.binhcn;

import dev.binhcn.enums.CommandEnum;
import dev.binhcn.repository.AccountRepository;
import dev.binhcn.repository.BillRepository;
import dev.binhcn.service.IFlowHandler;
import dev.binhcn.service.impl.FlowHandler;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import static org.junit.Assert.assertEquals;

public class FlowHandlerTest {

    @Test
    public void CashInTest_expected() {
        AccountRepository.init();

        long balance = 1000000;

        IFlowHandler flowHandler = FlowHandler.getInstance();
        flowHandler.process(CommandEnum.CASH_IN, new String[] {String.valueOf(balance)});
        assertEquals(balance, AccountRepository.getInstance().getAccount().getBalance());
    }

    @Test
    public void CashInTest_emptyAmount() {
        AccountRepository.init();

        IFlowHandler flowHandler = FlowHandler.getInstance();
        flowHandler.process(CommandEnum.CASH_IN, new String[0]);
        assertEquals(0, AccountRepository.getInstance().getAccount().getBalance());
    }

    @Test
    public void PayTest_theNumberOfPaidBills() {
        AccountRepository.init();
        BillRepository.init();

        IFlowHandler flowHandler = FlowHandler.getInstance();
        assertEquals(3, BillRepository.getInstance().getBillsNotPaid().size());
        flowHandler.process(CommandEnum.CASH_IN, new String[] {"1000000"});
        flowHandler.process(CommandEnum.PAY, new String[] {"1", "2"});
        assertEquals(1, BillRepository.getInstance().getBillsNotPaid().size());
    }

    @Test
    public void PayTest_PayWithEmptyBalance() {
        AccountRepository.init();
        BillRepository.init();

        IFlowHandler flowHandler = FlowHandler.getInstance();
        assertEquals(3, BillRepository.getInstance().getBillsNotPaid().size());
        flowHandler.process(CommandEnum.CASH_IN, new String[] {"0"});
        flowHandler.process(CommandEnum.PAY, new String[] {"1", "2"});
        assertEquals(3, BillRepository.getInstance().getBillsNotPaid().size());
    }

    @Test
    public void ScheduleTest_update() throws ParseException {
        BillRepository.init();

        String date = "28/10/2020";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

        IFlowHandler flowHandler = FlowHandler.getInstance();
        flowHandler.process(CommandEnum.SCHEDULE, new String[] {"2", date});

        assertEquals(dateFormat.parse(date), BillRepository.getInstance().getBill("2").getDueDate());
    }

    @Test
    public void Search_byProvider() {
        String provider = "VNPT";
        assertEquals(1, BillRepository.getInstance().getBillsByProvider(provider).size());
    }
}
