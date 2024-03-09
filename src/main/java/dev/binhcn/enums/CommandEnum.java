package dev.binhcn.enums;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public enum CommandEnum {

    EXIT,
    CASH_IN,
    LIST_BILL,
    PAY,
    DUE_DATE,
    SCHEDULE,
    LIST_PAYMENT,
    SEARCH_BILL_BY_PROVIDER;

    private static final Map<String, CommandEnum> ENUM_MAP;

    static {
        Map<String, CommandEnum> map = new ConcurrentHashMap<>();
        for (CommandEnum instance : CommandEnum.values()) {
            map.put(instance.name(), instance);
        }
        ENUM_MAP = Collections.unmodifiableMap(map);
    }

    public static CommandEnum get (String name) {
        return ENUM_MAP.get(name.toUpperCase());
    }
}
