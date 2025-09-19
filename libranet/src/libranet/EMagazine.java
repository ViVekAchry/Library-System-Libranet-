package libranet;

public class EMagazine extends LibraryItem {
    private final int issueNumber;
    private boolean archived = false;

    public EMagazine(int id, String title, String author, int issueNumber) {
        super(id, title, author);
        this.issueNumber = issueNumber;
    }

    public void archiveIssue() {
        if (!archived) {
            archived = true;
            System.out.println("Archiving issue " + issueNumber + " of " + title);
        } else {
            System.out.println(title + " issue " + issueNumber + " already archived.");
        }
    }

    public boolean isArchived() {
        return archived;
    }
}
