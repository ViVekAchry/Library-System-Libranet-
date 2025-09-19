package libranet;

import java.time.LocalDate;

public class FineRecord {
    private final int itemId;
    private final double amount;
    private final long overdueDays;
    private final LocalDate returnDate;

    public FineRecord(int itemId, double amount, long overdueDays, LocalDate returnDate) {
        this.itemId = itemId;
        this.amount = amount;
        this.overdueDays = overdueDays;
        this.returnDate = returnDate;
    }

    public int getItemId() { return itemId; }
    public double getAmount() { return amount; }
    public long getOverdueDays() { return overdueDays; }
    public LocalDate getReturnDate() { return returnDate; }
}