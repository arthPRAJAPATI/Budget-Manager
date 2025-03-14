package budget;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.List;
import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        File file = new File("purchases.txt");
        float income = 0;
        HashMap<Integer, List<String>> purchaseTypeItemList = new HashMap<>();
        purchaseTypeItemList.put(5, new ArrayList<>());
        purchaseTypeItemList.put(1, new ArrayList<>());
        purchaseTypeItemList.put(2, new ArrayList<>());
        purchaseTypeItemList.put(3, new ArrayList<>());
        purchaseTypeItemList.put(4, new ArrayList<>());

        boolean toContinue = true;
        do {
            printMenu();
            int type = 0;
            int choice = Integer.parseInt(sc.nextLine());
            System.out.println();
            switch (choice) {
                case 1:
                    System.out.println("Enter income:");
                    income = Float.parseFloat(sc.nextLine());
                    System.out.println("Income was added!");
                    break;
                case 2:
                    while(type != 5) {
                    System.out.println("Choose the type of purchase\n" +
                            "1) Food\n" +
                            "2) Clothes\n" +
                            "3) Entertainment\n" +
                            "4) Other\n" +
                            "5) Back");
                    type = Integer.parseInt(sc.nextLine());
                        System.out.println();
                        if(type == 5) {
                            break;
                        }

                        StringBuilder purchaseType = new StringBuilder();
                        System.out.println("Enter purchase name:");
                        purchaseType.append(sc.nextLine());
                        System.out.println("Enter its price:");
                        String costOfItem = String.format("%.2f", Float.parseFloat(sc.nextLine()));
                        purchaseType.append(" $").append(costOfItem);
                        income -= Float.parseFloat(costOfItem);
                        purchaseTypeItemList.get(type).add(purchaseType.toString());
                        purchaseTypeItemList.get(5).add(purchaseType.toString());
                        System.out.println("Purchase was added!");
                        System.out.println();
                    }

                    break;
                case 3:
                    while(type != 6) {
                    System.out.println("Choose the type of purchase\n" +
                            "1) Food\n" +
                            "2) Clothes\n" +
                            "3) Entertainment\n" +
                            "4) Other\n" +
                            "5) All\n" +
                            "6) Back");
                    type = Integer.parseInt(sc.nextLine());
                        System.out.println();
                    if(type == 6) {
                        break;
                    }
                        if (purchaseTypeItemList.get(5).isEmpty()) {
                            System.out.println("The purchase list is empty");
                        } else {
                            printItems(purchaseTypeItemList, type);
                        }
                        System.out.println();
                    }

                    break;
                case 4:
                    System.out.println("Balance: $" + income);
                    break;
                case 5:
                    saveToFile(purchaseTypeItemList, file, income);
                    break;
                case 6:
                    income = loadFromFile(purchaseTypeItemList, file, income);
                    break;
                case 7:
                    while(type != 4) {
                        System.out.println("How do you want to sort?\n" +
                                "1) Sort all purchases\n" +
                                "2) Sort by type\n" +
                                "3) Sort certain type\n" +
                                "4) Back");
                        type = Integer.parseInt(sc.nextLine());
                        if (type == 4) {
                            break;
                        } else {
                            getSorting(purchaseTypeItemList, type);
                        }
                    }
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

    private static void getSorting(HashMap<Integer, List<String>> purchaseTypeItemList, int type) {
        switch (type) {
            case 1: // Sort All Purchases
                if (purchaseTypeItemList.get(5).isEmpty()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    System.out.println("All:");
                    List<String> allPurchases = new ArrayList<>(purchaseTypeItemList.get(5));
                    allPurchases.sort((a, b) -> Float.compare(extractPrice(b), extractPrice(a))); // Descending order
                    printSortedItems(allPurchases);
                }
                break;
            case 2: // Sort By Type
                HashMap<String, Float> typeTotals = new HashMap<>();
                typeTotals.put("Food", calculateTotal(purchaseTypeItemList.get(1)));
                typeTotals.put("Clothes", calculateTotal(purchaseTypeItemList.get(2)));
                typeTotals.put("Entertainment", calculateTotal(purchaseTypeItemList.get(3)));
                typeTotals.put("Other", calculateTotal(purchaseTypeItemList.get(4)));

                System.out.println("Types:");
                typeTotals.entrySet().stream()
                        .sorted((a, b) -> Float.compare(b.getValue(), a.getValue())) // Descending order
                        .forEach(entry -> System.out.printf("%s - $%.2f%n", entry.getKey(), entry.getValue()));
                System.out.printf("Total sum: $%.2f%n", typeTotals.values().stream().mapToDouble(Float::doubleValue).sum());
                break;
            case 3: // Sort Certain Type
                Scanner sc = new Scanner(System.in);
                System.out.println("Choose the type of purchase");
                System.out.println("1) Food");
                System.out.println("2) Clothes");
                System.out.println("3) Entertainment");
                System.out.println("4) Other");
                int category = Integer.parseInt(sc.nextLine());
                System.out.println();

                if (purchaseTypeItemList.get(category).isEmpty()) {
                    System.out.println("The purchase list is empty!");
                } else {
                    String categoryName = switch (category) {
                        case 1 -> "Food";
                        case 2 -> "Clothes";
                        case 3 -> "Entertainment";
                        case 4 -> "Other";
                        default -> "Unknown";
                    };
                    System.out.println(categoryName + ":");
                    List<String> categoryPurchases = new ArrayList<>(purchaseTypeItemList.get(category));
                    categoryPurchases.sort((a, b) -> Float.compare(extractPrice(b), extractPrice(a))); // Descending order
                    printSortedItems(categoryPurchases);
                }
                break;
        }
    }

    // Extracts price from a purchase string like "Almond 250g $35.43"
    private static float extractPrice(String purchase) {
        String[] parts = purchase.split(" \\$");
        return Float.parseFloat(parts[parts.length - 1]);
    }

    // Calculates total sum of a given purchase list
    private static float calculateTotal(List<String> purchases) {
        float total = 0;
        for (String purchase : purchases) {
            total += extractPrice(purchase);
        }
        return total;
    }

    // Prints sorted items with total sum
    private static void printSortedItems(List<String> purchases) {
        float sum = 0;
        for (String purchase : purchases) {
            System.out.println(purchase);
            sum += extractPrice(purchase);
        }
        System.out.printf("Total sum: $%.2f%n", sum);
    }


    private static float loadFromFile(HashMap<Integer, List<String>> purchaseTypeItemList, File file, float income) {
        if (!file.exists()) {
            System.out.println("File not found!");
            return -1;
        }
        try {
            Scanner scanner = new Scanner(file);
            income = Float.parseFloat(scanner.nextLine());
            while (scanner.hasNextLine()) {
                String[] purchaseLine = scanner.nextLine().split(", ");
                purchaseTypeItemList.get(Integer.parseInt(purchaseLine[0])).add(purchaseLine[1]);
            }
            scanner.close();
            System.out.println("Purchases were loaded!");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        return income;
    }

    private static void saveToFile(HashMap<Integer, List<String>> purchaseTypeItemList, File file, float income) {
        try {
            FileWriter writer = new FileWriter(file);
            writer.write(String.valueOf(income) + "\n");
            for (int i = 1; i <= purchaseTypeItemList.size(); i++) {
                if (!purchaseTypeItemList.get(i).isEmpty()) {
                    for (String s : purchaseTypeItemList.get(i)) {
                        writer.write(i + ", " + s + "\n");
                    }
                }
            }
            writer.close();
            System.out.println("Purchases were saved!");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void printItems(HashMap<Integer, List<String>> purchaseTypeItemList, int type) {
        float sum = 0;
        List<String> purchaseItem = purchaseTypeItemList.get(type);
        for (String s : purchaseItem) {
            System.out.println(s);
            String[] cost = s.split(" \\$");
            sum += Float.parseFloat(cost[cost.length - 1]);
        }
        System.out.printf("Total sum: $%.2f%n", sum);
    }

    private static void printMenu() {
        System.out.println("Choose your action:");
        System.out.println("1) Add income");
        System.out.println("2) Add purchase");
        System.out.println("3) Show list of purchases");
        System.out.println("4) Balance");
        System.out.println("5) Save");
        System.out.println("6) Load");
        System.out.println("7) Analyze (Sort)");
        System.out.println("0) Exit");
    }
}
