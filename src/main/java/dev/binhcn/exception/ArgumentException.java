package dev.binhcn.exception;

import dev.binhcn.enums.CommandEnum;
import dev.binhcn.enums.StateEnum;

public class ArgumentException extends IllegalArgumentException {

    private final CommandEnum command;
    private final int argumentCount;

    public ArgumentException(CommandEnum command, int argumentCount) {
        this.command = command;
        this.argumentCount = argumentCount;
    }

    public CommandEnum getCommand() {
        return command;
    }

    public int getArgumentCount() {
        return argumentCount;
    }
}
