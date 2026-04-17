package com.expertsystem;

import com.expertsystem.controller.SystemController;
import com.expertsystem.view.ConsoleUI;

public class MainApp {
    public static void main(String[] args) {
        SystemController controller = new SystemController();
        
        if (args.length > 0 && args[0].equals("--gui")) {
            new com.expertsystem.view.GuiUI().start(controller);
        } else {
            new ConsoleUI().start(controller);
        }
    }
}
