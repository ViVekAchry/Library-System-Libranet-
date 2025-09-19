package libranet;

public class Book extends LibraryItem {
    private int pageCount;

    public Book(int id, String title, String author, int pageCount) {
        super(id, title, author);
        this.pageCount = pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }
}
