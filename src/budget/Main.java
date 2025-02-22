package budget;

import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);


        float income = 0;
        List<String> purchaseItem = new ArrayList<>();
        List<String> cost = new ArrayList<>();
        boolean toContinue = true;
        do {
            printMenu();
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("Enter income:");
                    income = Float.parseFloat(sc.nextLine());
                    System.out.println("Income was added!");
                    break;
                case 2:
                    System.out.println("Enter purchase name:");
                    purchaseItem.add(sc.nextLine());
                    System.out.println("Enter its price:");
                    String costOfItem = sc.nextLine();
                    cost.add(costOfItem);
                    income -= Float.parseFloat(costOfItem);
                    System.out.println("Purchase was added!");
                    break;
                case 3:
                    if (purchaseItem.isEmpty() && cost.isEmpty()) {
                        System.out.println("The purchase list is empty");
                    } else {
                        printItems(purchaseItem, cost);
                    }
                    break;
                case 4:
                    System.out.println("Balance: $" + income);
                    break;
                case 0:
                    System.out.println("Bye!");
                    toContinue = false;
                    break;
                default:
                    System.out.println("Invalid Action");
                    break;
            }
            System.out.println();
        }
        while(toContinue);
    }

    private static void printItems(List<String> purchaseItem, List<String> cost) {
        float sum = 0;
        for (int i = 0; i < purchaseItem.size(); i++) {
            System.out.println(purchaseItem.get(i) + " $" + cost.get(i));
            sum += Float.parseFloat(cost.get(i));
        }
        System.out.println("Total sum: $" + sum);
    }

    private static void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("0) Exit");
    }
}
