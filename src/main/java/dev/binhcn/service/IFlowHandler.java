package dev.binhcn.service;

import dev.binhcn.enums.CommandEnum;

import java.util.List;

public interface IFlowHandler {

    void process(CommandEnum commandEnum, String[] arguments);
}
