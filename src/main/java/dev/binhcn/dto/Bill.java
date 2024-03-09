package dev.binhcn.dto;

import java.text.SimpleDateFormat;
import java.util.Date;
import dev.binhcn.enums.StateEnum;

public class Bill {

    private String id;
    private String type;
    private long amount;
    private Date dueDate;
    private Date paymentDate;

    private StateEnum state;
    private String provider;

    public Bill(String id, String type, long amount, Date dueDate, StateEnum state, String provider) {
        this.id = id;
        this.type = type;
        this.amount = amount;
        this.dueDate = dueDate;
        this.state = state;
        this.provider = provider;
    }

    public String getId() {
        return this.id;
    }

    public long getAmount() {
        return this.amount;
    }

    public Date getDueDate() {
        return this.dueDate;
    }

    public String getProvider() {
        return this.provider;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public void setPaymentDate(Date paymentDate) {
        this.paymentDate = paymentDate;
    }

    public StateEnum getState() {
        return this.state;
    }

    public void setState(StateEnum state) {
        this.state = state;
    }

    public String printBill() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-12s%-12s%-8s%-12s%-12s%-12s", id, type, amount, dateFormat.format(dueDate), state.name(), provider);
    }

    public String printPayment(int no) {
        StateEnum paymentState = StateEnum.PENDING;
        if (this.state == StateEnum.PROCESSED) {
            paymentState = StateEnum.PROCESSED;
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return String.format("%-4s%-8s%-16s%-12s%-8s", no, amount, paymentDate != null ? dateFormat.format(paymentDate) : "-", paymentState.name(), id);
    }
}
