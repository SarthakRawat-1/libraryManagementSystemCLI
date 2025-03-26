/**
 * This class starts the application and handles the command-line interface
 * running an interactive menu system for users to manage library resources.
 */
package librarysystem;

import librarysystem.controllers.LibraryController;
import librarysystem.views.LibraryView;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Library Management System ===");
        System.out.println("Starting application...");

        LibraryController controller = new LibraryController(new LibraryView());

        controller.addSampleData();

        controller.runMenu();

        System.out.println("Thank you for using Library Management System!");
    }
}