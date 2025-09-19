package libranet;


import java.time.LocalDate;

public class BorrowRecord {
    private final int itemId;
    private final int userId;
    private final LocalDate borrowDate;
    private final LocalDate dueDate;
    private final String durationStr;

    public BorrowRecord(int itemId, int userId, LocalDate borrowDate, LocalDate dueDate, String durationStr) {
        this.itemId = itemId;
        this.userId = userId;
        this.borrowDate = borrowDate;
        this.dueDate = dueDate;
        this.durationStr = durationStr;
    }

    public int getItemId() { return itemId; }
    public int getUserId() { return userId; }
    public LocalDate getBorrowDate() { return borrowDate; }
    public LocalDate getDueDate() { return dueDate; }
    public String getDurationStr() { return durationStr; }
}
