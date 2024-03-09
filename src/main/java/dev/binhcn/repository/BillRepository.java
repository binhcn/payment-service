package dev.binhcn.repository;

import dev.binhcn.dto.Bill;
import dev.binhcn.enums.StateEnum;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

public class BillRepository {

    private final Map<String, Bill> billMap;

    private static BillRepository instance;

    private BillRepository() {
        billMap = new HashMap<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            billMap.put("1", new Bill("1", "ELECTRIC", 200000, dateFormat.parse("25/10/2020"), StateEnum.NOT_PAID, "EVN HCMC"));
            billMap.put("2", new Bill("2", "WATER", 175000, dateFormat.parse("30/10/2020"), StateEnum.NOT_PAID, "SAVACO HCMC"));
            billMap.put("3", new Bill("3", "INTERNET", 800000, dateFormat.parse("30/11/2020"), StateEnum.NOT_PAID, "VNPT"));
        } catch (java.text.ParseException ex) {
            System.out.println("Init bill list fail");
        }
    }

    public static void init() {
        instance = new BillRepository();
    }

    public static BillRepository getInstance() {
        if (instance == null) {
            instance = new BillRepository();
        }
        return instance;
    }

    public List<Bill> getBills() {
        return new LinkedList<>(billMap.values());
    }

    public Bill getBill(String id) {
        return billMap.get(id);
    }

    public long getTotalBillAmount(String[] billIds) {
        long totalAmount = 0;
        for (String billId : billIds) {
            Bill bill = getBill(billId);
            if (bill != null) {
                totalAmount += bill.getAmount();
            }
        }
        return totalAmount;
    }

    public List<Bill> getBillsNotPaid() {
        return billMap.values().stream()
                .filter(bill -> bill.getState() == StateEnum.NOT_PAID)
                .collect(Collectors.toList());
    }

    public List<Bill> getBillsByProvider(String provider) {
        return billMap.values().stream()
                .filter(bill -> bill.getProvider().equals(provider))
                .collect(Collectors.toList());
    }
}
