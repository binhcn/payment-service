package dev.binhcn.controller.impl;

import dev.binhcn.enums.CommandEnum;
import dev.binhcn.controller.IController;
import dev.binhcn.service.IFlowHandler;
import dev.binhcn.service.impl.FlowHandler;

import java.util.Scanner;

public class ConsoleController implements IController {

    private static ConsoleController instance;

    private ConsoleController() {
    }

    public static IController getInstance() {
        if (instance == null) {
            instance = new ConsoleController();
        }
        return instance;
    }

    @Override
    public void init() {
        Scanner sc = new Scanner(System.in);
        CommandEnum command;
        do {
            System.out.print("Enter your command: ");
            String input = sc.nextLine().trim();
            int blankIdx = input.indexOf(' ');
            command = CommandEnum.get(blankIdx > 0 ? input.substring(0, blankIdx) : input);
            if (command == null) {
                System.out.println("Wrong command, please do again");
                continue;
            }
            String[] arguments = blankIdx > 0 && blankIdx < input.length() - 1
                    ? input.substring(blankIdx + 1).split(" ") : new String[0];

            IFlowHandler flowHandler = FlowHandler.getInstance();
            flowHandler.process(command, arguments);
        } while (command != CommandEnum.EXIT);

    }
}
