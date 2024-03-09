package dev.binhcn;

import dev.binhcn.controller.impl.ConsoleController;
import dev.binhcn.controller.IController;

public class Main {

    public static void main(String[] args) {
        IController controller = ConsoleController.getInstance();
        controller.init();
    }
}