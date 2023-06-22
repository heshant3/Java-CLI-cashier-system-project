import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class FoodCenterProgram {
    private static ArrayList<ArrayList<String>> queues;
    private static ArrayList<String> stock;
    
    public static void main(String[] args) {
        queues = new ArrayList<>();
        queues.add(new ArrayList<>()); // Cashier 1 queue
        queues.add(new ArrayList<>()); // Cashier 2 queue
        queues.add(new ArrayList<>()); // Cashier 3 queue
        
        stock = new ArrayList<>();
        for (int i = 1; i <= 50; i++) {
            stock.add("Burger " + i);
        }
        
        Scanner scanner = new Scanner(System.in);
        int option;
        
        do {
            displayMenu();
            option = scanner.nextInt();
            
            switch (option) {
                case 100:
                case 300:
                    viewAllQueues();
                    break;
                case 101:
                case 301:
                    viewEmptyQueues();
                    break;
                case 102:
                case 302:
                    addCustomerToQueue(scanner);
                    break;
                case 103:
                case 303:
                    removeCustomerFromQueue(scanner);
                    break;
                case 104:
                case 304:
                    removeServedCustomer();
                    break;
                case 105:
                case 305:
                    viewCustomersSorted();
                    break;
                case 106:
                case 306:
                    storeProgramData();
                    break;
                case 107:
                case 307:
                    loadProgramData();
                    break;
                case 108:
                case 308:
                    viewRemainingStock();
                    break;
                case 109:
                case 309:
                    addBurgersToStock(scanner);
                    break;
                case 999:
                    System.out.println("Exiting the program...");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
                    break;
            }
        } while (option != 999);
        
        scanner.close();
    }
    
    private static void displayMenu() {
        System.out.println("***************************");
        System.out.println("* Foodies Fave Food Center *");
        System.out.println("***************************");
        System.out.println("100 or VFQ: View all Queues");
        System.out.println("101 or VEQ: View all Empty Queues");
        System.out.println("102 or ACQ: Add customer to a Queue");
        System.out.println("103 or RCQ: Remove a customer from a Queue (From a specific location)");
        System.out.println("104 or PCQ: Remove a served customer");
        System.out.println("105 or VCS: View Customers Sorted in alphabetical order");
        System.out.println("106 or SPD: Store Program Data into file");
        System.out.println("107 or LPD: Load Program Data from file");
        System.out.println("108 or STK: View Remaining burgers Stock");
        System.out.println("109 or AFS: Add burgers to Stock");
        System.out.println("999 or EXT: Exit the Program");
        System.out.println("Enter your option: ");
    }
    
    private static void viewAllQueues() {
        System.out.println("*****************");
        System.out.println("*   Cashiers   *");
        System.out.println("*****************");
        
        for (int i = 0; i < queues.size(); i++) {
            ArrayList<String> queue = queues.get(i);
            
            for (int j = 0; j < queue.size(); j++) {
                System.out.print("O ");
            }
            
            int emptySlots = getEmptySlots(i);
            
            for (int j = 0; j < emptySlots; j++) {
                System.out.print("X ");
            }
            
            System.out.println();
        }
    }
    
    private static void viewEmptyQueues() {
        System.out.println("*****************");
        System.out.println("* Empty Queues *");
        System.out.println("*****************");
        
        for (int i = 0; i < queues.size(); i++) {
            int emptySlots = getEmptySlots(i);
            System.out.println("Cashier " + (i + 1) + ": " + emptySlots + " empty slots");
        }
    }
    
    private static int getEmptySlots(int cashierIndex) {
        ArrayList<String> queue = queues.get(cashierIndex);
        int occupiedSlots = queue.size();
        int maxSlots = getMaxSlots(cashierIndex);
        
        return maxSlots - occupiedSlots;
    }
    
    private static int getMaxSlots(int cashierIndex) {
        int maxSlots;
        
        switch (cashierIndex) {
            case 0:
                maxSlots = 2;
                break;
            case 1:
                maxSlots = 3;
                break;
            case 2:
                maxSlots = 5;
                break;
            default:
                maxSlots = 0;
                break;
        }
        
        return maxSlots;
    }
    
    private static void addCustomerToQueue(Scanner scanner) {
        System.out.print("Enter the cashier number (1-3): ");
        int cashierNumber = scanner.nextInt();
        
        if (cashierNumber < 1 || cashierNumber > 3) {
            System.out.println("Invalid cashier number.");
            return;
        }
        
        int cashierIndex = cashierNumber - 1;
        ArrayList<String> queue = queues.get(cashierIndex);
        
        if (queue.size() == getMaxSlots(cashierIndex)) {
            System.out.println("The queue is full for Cashier " + cashierNumber);
            return;
        }
        
        System.out.print("Enter customer name: ");
        String customerName = scanner.next();
        
        queue.add(customerName);
        
        // Update stock
        int burgersUsed = queue.size() * 5;
        int remainingStock = stock.size() - burgersUsed;
        
        if (remainingStock <= 10) {
            System.out.println("Warning: Low stock! Remaining burgers: " + remainingStock);
        }
    }
    
    private static void removeCustomerFromQueue(Scanner scanner) {
        System.out.print("Enter the cashier number (1-3): ");
        int cashierNumber = scanner.nextInt();
        
        if (cashierNumber < 1 || cashierNumber > 3) {
            System.out.println("Invalid cashier number.");
            return;
        }
        
        int cashierIndex = cashierNumber - 1;
        ArrayList<String> queue = queues.get(cashierIndex);
        
        if (queue.isEmpty()) {
            System.out.println("The queue is already empty for Cashier " + cashierNumber);
            return;
        }
        
        System.out.print("Enter the position of the customer to remove: ");
        int position = scanner.nextInt();
        
        if (position < 1 || position > queue.size()) {
            System.out.println("Invalid position.");
            return;
        }
        
        String removedCustomer = queue.remove(position - 1);
        System.out.println("Removed customer: " + removedCustomer);
    }
    
    private static void removeServedCustomer() {
        for (ArrayList<String> queue : queues) {
            if (!queue.isEmpty()) {
                String removedCustomer = queue.remove(0);
                System.out.println("Removed served customer: " + removedCustomer);
                return;
            }
        }
        
        System.out.println("No served customers found.");
    }
    
    private static void viewCustomersSorted() {
        ArrayList<String> customers = new ArrayList<>();
        
        for (ArrayList<String> queue : queues) {
            customers.addAll(queue);
        }
        
        Collections.sort(customers);
        
        System.out.println("Customers Sorted in alphabetical order:");
        for (String customer : customers) {
            System.out.println(customer);
        }
    }
    
    private static void storeProgramData() {
        try (FileWriter writer = new FileWriter("program_data.txt")) {
            writer.write("***************************\n");
            writer.write("* Foodies Fave Food Center *\n");
            writer.write("***************************\n");
            
            for (ArrayList<String> queue : queues) {
                writer.write("Cashier Queue:\n");
                for (String customer : queue) {
                    writer.write(customer + "\n");
                }
                writer.write("\n");
            }
            
            writer.write("Remaining burgers stock: " + stock.size() + "\n");
            
            System.out.println("Program data stored successfully.");
        } catch (IOException e) {
            System.out.println("Error occurred while storing program data.");
            e.printStackTrace();
        }
    }
    
    private static void loadProgramData() {
        // Implement the logic to load program data from a file
        System.out.println("Loading program data from a file...");
    }
    
    private static void viewRemainingStock() {
        int remainingStock = stock.size();
        System.out.println("Remaining burgers stock: " + remainingStock);
    }
    
    private static void addBurgersToStock(Scanner scanner) {
        System.out.print("Enter the number of burgers to add: ");
        int burgersToAdd = scanner.nextInt();
        
        for (int i = 1; i <= burgersToAdd; i++) {
            stock.add("Burger " + (stock.size() + 1));
        }
        
        System.out.println(burgersToAdd + " burgers added to stock.");
    }
}
