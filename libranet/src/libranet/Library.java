package libranet;


import java.time.LocalDate;
import java.util.*;

public class Library {
    private final Map<Integer, LibraryItem> items = new HashMap<>();
    private final Map<Integer, BorrowRecord> activeBorrowsByItem = new HashMap<>();
    private final Map<Integer, List<FineRecord>> finesByUser = new HashMap<>();
    private final double finePerDay;

    public Library(double finePerDay) {
        this.finePerDay = finePerDay;
    }

    public void addItem(LibraryItem item) {
        Objects.requireNonNull(item, "item must not be null");
        if (items.containsKey(item.getId())) {
            throw new IllegalArgumentException("Duplicate item id: " + item.getId());
        }
        items.put(item.getId(), item);
    }

    // Borrow using duration string (so parsing is handled inside)
    public void borrowItem(int itemId, int userId, String durationStr) {
        LibraryItem item = items.get(itemId);
        if (item == null) throw new IllegalArgumentException("Item not found: " + itemId);
        if (!item.isAvailable()) throw new IllegalStateException("Item not available: " + itemId);

        int days = DurationParser.parseDays(durationStr);
        if (days <= 0) days = 1;
        LocalDate due = LocalDate.now().plusDays(days);

        item.borrowItem();
        BorrowRecord br = new BorrowRecord(itemId, userId, LocalDate.now(), due, durationStr);
        activeBorrowsByItem.put(itemId, br);

        System.out.println(item.getTitle() + " borrowed by user " + userId + " for " + days + " days.");
    }

    public void returnItem(int itemId, int userId, LocalDate returnDate) {
        BorrowRecord br = activeBorrowsByItem.get(itemId);
        if (br == null) throw new IllegalStateException("Item is not currently borrowed: " + itemId);
        if (br.getUserId() != userId) throw new IllegalStateException("Item was borrowed by a different user");

        if (returnDate == null) {
            returnDate = LocalDate.now();
        }

        LocalDate due = br.getDueDate();
        long overdue = 0;
        if (returnDate.isAfter(due)) {
            // Use until() instead of ChronoUnit
            overdue = due.until(returnDate).getDays();
        }

        if (overdue > 0) {
            double fine = overdue * finePerDay;
            FineRecord fr = new FineRecord(itemId, fine, overdue, returnDate);
            finesByUser.computeIfAbsent(userId, k -> new ArrayList<>()).add(fr);
            System.out.println("Returned late. Fine: Rs." + fine);
        } else {
            System.out.println("Returned on time.");
        }

        // clear borrow state
        LibraryItem item = items.get(itemId);
        item.returnItem();
        activeBorrowsByItem.remove(itemId);
    }

    public List<LibraryItem> searchByType(Class<?> type) {
        List<LibraryItem> out = new ArrayList<>();
        for (LibraryItem it : items.values()) {
            if (type.isInstance(it)) {
                out.add(it);
            }
        }
        return out;
    }

    public double getTotalFineForUser(int userId) {
        return finesByUser.getOrDefault(userId, Collections.emptyList())
                .stream()
                .mapToDouble(FineRecord::getAmount)
                .sum();
    }

    public void showFinesForUser(int userId) {
        List<FineRecord> fines = finesByUser.get(userId);
        if (fines == null || fines.isEmpty()) {
            System.out.println("No fines for user " + userId);
        } else {
            fines.forEach(f -> System.out.println("Item " + f.getItemId() + " fine: Rs." + f.getAmount()));
        }
    }
}