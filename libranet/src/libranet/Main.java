package libranet;
import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Library library = new Library(10.0); // fine per day

        // Add some default items
        library.addItem(new Book(1, "Java Fundamentals", "James Gosling", 500)); 
        library.addItem(new Audiobook(2, "Clean Code (Audio)", "Robert Martin", 600)); 
        library.addItem(new EMagazine(3, "Tech Today", "TechPress", 42));
        library.addItem(new Book(4, "Wings of Fire", "A.P.J. Abdul Kalam", 180));
        library.addItem(new Book(5, "The White Tiger", "Aravind Adiga", 320));
        library.addItem(new Book(6, "God of Small Things", "Arundhati Roy", 340));
        library.addItem(new Book(7, "Train to Pakistan", "Khushwant Singh", 290));
        library.addItem(new Book(8, "Malgudi Days", "R.K. Narayan", 245));
        
        library.addItem(new Audiobook(9, "The Discovery of India (Audio)", "Jawaharlal Nehru", 1200));
        library.addItem(new Audiobook(10, "I Too Had a Love Story (Audio)", "Ravinder Singh", 480));
        library.addItem(new Audiobook(11, "The Great Indian Novel (Audio)", "Shashi Tharoor", 900));
        library.addItem(new Audiobook(12, "Ignited Minds (Audio)", "A.P.J. Abdul Kalam", 300));
        library.addItem(new Audiobook(13, "India After Gandhi (Audio)", "Ramachandra Guha", 1500));

        library.addItem(new EMagazine(14, "India Today", "Living Media", 2025));
        library.addItem(new EMagazine(15, "Outlook India", "Outlook Publishing", 2025));
        library.addItem(new EMagazine(16, "Frontline", "The Hindu Group", 2025));
        library.addItem(new EMagazine(17, "Business India", "Business India Publications", 2025));
        library.addItem(new EMagazine(18, "Filmfare", "Worldwide Media", 2025));


        Scanner sc = new Scanner(System.in);

        while (true) { 
            System.out.println("\n===== LibraNet Menu =====");
            System.out.println("1. Borrow Item");
            System.out.println("2. Return Item");
            System.out.println("3. Search by Type");
            System.out.println("4. Show User Fines");
            System.out.println("5. List All Items");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");

            String choiceLine = sc.nextLine().trim();
            if (choiceLine.isEmpty()) {
                System.out.println("Please enter a choice.");
                continue;
            }

            int choice;
            try {
                choice = Integer.parseInt(choiceLine);
            } catch (NumberFormatException e) {
                System.out.println("Invalid choice. Enter a number between 1 and 6.");
                continue;
            }

            switch (choice) {
                case 1 -> { // Borrow
                    // Show available items
                    System.out.println("\nAvailable items:");
                    List<LibraryItem> all = library.searchByType(LibraryItem.class);
                    all.stream().filter(LibraryItem::isAvailable)
                            .forEach(it -> System.out.println(it.getId() + ": " + it.getTitle() + " by " + it.getAuthor()));

                    try {
                        System.out.print("Enter Item ID to borrow: ");
                        String idStr = sc.nextLine().trim();
                        int itemId = Integer.parseInt(idStr);

                        System.out.print("Enter User ID: ");
                        String userStr = sc.nextLine().trim();
                        int userId = Integer.parseInt(userStr);

                        System.out.print("Enter duration (e.g., 5d, 2w, 7): ");
                        String duration = sc.nextLine().trim();

                        library.borrowItem(itemId, userId, duration);
                        System.out.println("Item borrowed successfully!");
                    } catch (NumberFormatException ne) {
                        System.out.println("Invalid number format. Please enter numeric IDs.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 2 -> { // Return
                    // Show borrowed items
                    System.out.println("\nCurrently borrowed items (by item id):");
                    List<LibraryItem> all = library.searchByType(LibraryItem.class);
                    all.stream().filter(it -> !it.isAvailable())
                            .forEach(it -> System.out.println(it.getId() + ": " + it.getTitle() + " by " + it.getAuthor()));

                    try {
                        System.out.print("Enter Item ID to return: ");
                        String idStr = sc.nextLine().trim();
                        int itemId = Integer.parseInt(idStr);

                        System.out.print("Enter User ID: ");
                        String userStr = sc.nextLine().trim();
                        int userId = Integer.parseInt(userStr);

                        System.out.print("Enter return offset (e.g., 0, 3d, 1w) relative to now: ");
                        String offset = sc.nextLine().trim();
                        int daysOffset = DurationParser.parseDays(offset); // parses "3d","1w","5"

                        LocalDate returnDate = LocalDate.now().plusDays(daysOffset);
                        library.returnItem(itemId, userId, returnDate);
                        System.out.println("Item returned successfully!");
                    } catch (NumberFormatException ne) {
                        System.out.println("Invalid number format. Please enter numeric IDs.");
                    } catch (IllegalArgumentException | IllegalStateException e) {
                        System.out.println("Error: " + e.getMessage());
                    } catch (Exception e) {
                        System.out.println("Unexpected error: " + e.getMessage());
                        e.printStackTrace();
                    }
                }

                case 3 -> { // Search by type
                    System.out.println("Search Type Options: 1=Book, 2=Audiobook, 3=E-Magazine, 4=All");
                    System.out.print("Enter choice: ");
                    String t = sc.nextLine().trim();
                    int tChoice;
                    try {
                        tChoice = Integer.parseInt(t);
                    } catch (NumberFormatException nfe) {
                        System.out.println("Invalid input.");
                        break;
                    }
                    Class<?> type = switch (tChoice) {
                        case 1 -> Book.class;
                        case 2 -> Audiobook.class;
                        case 3 -> EMagazine.class;
                        default -> LibraryItem.class;
                    };
                    List<LibraryItem> results = library.searchByType(type);
                    if (results.isEmpty()) {
                        System.out.println("No items found for that type.");
                    } else {
                        results.forEach(it -> System.out.println(it.getId() + ": " + it.getTitle()
                                + " by " + it.getAuthor()
                                + " (available=" + it.isAvailable() + ")"));
                    }
                }

                case 4 -> { // Show fines for a user
                    try {
                        System.out.print("Enter User ID: ");
                        int userId = Integer.parseInt(sc.nextLine().trim());
                        double total = library.getTotalFineForUser(userId);
                        System.out.println("Total fine for user " + userId + ": Rs." + total);
                        library.showFinesForUser(userId); // optional method if implemented
                    } catch (NumberFormatException ne) {
                        System.out.println("Invalid user id.");
                    } catch (Exception e) {
                        System.out.println("Error: " + e.getMessage());
                    }
                }

                case 5 -> { // List all items
                    List<LibraryItem> allItems = library.searchByType(LibraryItem.class);
                    System.out.println("\nAll items:");
                    allItems.forEach(it -> System.out.println(it.getId() + ": " + it.getTitle()
                            + " by " + it.getAuthor()
                            + " (available=" + it.isAvailable() + ")"));
                }

                case 6 -> { // Exit
                    System.out.println("Exiting. Goodbye!");
                    sc.close();
                    return;
                }

                default -> System.out.println("Invalid choice, enter 1-6.");
            }
        }
    }
}