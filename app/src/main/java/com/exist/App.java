package com.exist;

import java.util.Scanner;
import com.exist.AppService;

public class App {
    public static void main( String[] args ) {
        Scanner in = new Scanner(System.in);
        AppService appService = new AppService();
        boolean continueProcess = true;
        while(continueProcess) {
            appService.commands();
            String choice = in.nextLine();
            while(!(choice.matches("([1-9]|1[012])$")) || choice.isEmpty()) {
                System.out.print("Please enter equivalent number of command: ");
                choice = in.nextLine();
            }
            switch(Integer.parseInt(choice)) {
                case 1: appService.addPerson();
                    break;
                case 2: appService.updatePersonById();
                    break;
                case 3: appService.deletePersonById();
                    break;
                case 4: appService.showAllPersons();
                    break;
                case 5: appService.addContactById();
                    break;
                case 6: appService.updateContactById();
                    break;
                case 7: appService.deleteContactById();
                    break;
                case 8: appService.showAllRoles();
                    break;
                case 9: appService.addRoleById();
                    break;
                case 10: appService.updateRoleById();
                    break;         
                case 11: appService.deleteRoleById();
                    break;              
                case 12: continueProcess = false;
                    System.exit(0);
                    break;
                default: System.out.println("Command not found!");
                    break;
            }
        }
    }
}
