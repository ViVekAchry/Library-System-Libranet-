
  <h1>LibraNet — Java Library Platform</h1>
  <p class="badge">Simple • Reusable • Extensible</p>

  <h2>Project summary</h2>
  <p>
    <strong>LibraNet</strong> is a small Java application that models an online library platform
    managing <em>Books</em>, <em>Audiobooks</em>, and <em>E-Magazines</em>. The design focuses on
    reusability, extensibility, and robust data handling while keeping the code simple.
  </p>

  <h2>What this project implements (requirements)</h2>
  <ul>
    <li><strong>Common operations</strong>: Borrow, return, and check availability for items.</li>
    <li><strong>Fines</strong>: Numeric fine calculation (example: Rs. 10/day). Fines are stored in collections per user.</li>
    <li><strong>Specialized behaviors</strong>:
      <ul>
        <li>Books: <code>getPageCount()</code></li>
        <li>Audiobooks: implements <code>Playable</code> (methods: <code>play()</code>, <code>pause()</code>, <code>stop()</code>, <code>getPlaybackDuration()</code>)</li>
        <li>E-magazines: <code>archiveIssue()</code> and <code>isArchived()</code></li>
      </ul>
    </li>
    <li><strong>Data handling</strong>: Borrow durations parsed from strings (e.g. <code>"7d"</code>, <code>"2w"</code>, <code>"1 month"</code>), item IDs stored as integers, and validation with clear exceptions.</li>
    <li><strong>Design goals</strong>: Clean base class + interfaces, service layer for borrow/fine management, and small helper DTO/record classes for robust state handling.</li>
  </ul>

  <h2>Project structure (important files)</h2>
  <div class="cols">
    <div class="col">
      <h3>Package: <code>com.libranet</code></h3>
      <ul>
        <li><code>LibraryItem.java</code> — abstract base item (id, title, author, available)</li>
        <li><code>Book.java</code> — subclass with <code>getPageCount()</code></li>
        <li><code>Audiobook.java</code> — implements <code>Playable</code> using <code>Duration</code></li>
        <li><code>EMagazine.java</code> — has <code>archiveIssue()</code></li>
        <li><code>Playable.java</code> — interface for playable items</li>
        <li><code>Library.java</code> — main service: add, borrow, return, search, fines</li>
        <li><code>BorrowRecord.java</code>, <code>FineRecord.java</code> — small record classes</li>
        <li><code>DurationParser.java</code> — robust duration-string → days parser</li>
      </ul>
    </div>
    <div class="col">
      <h3>Package: <code>org.example</code></h3>
      <ul>
        <li><code>Main.java</code> — interactive console menu for demo/testing</li>
      </ul>
      <h3>Build</h3>
      <ul>
        <li>Project layout follows Maven standard: <code>src/main/java</code>.</li>
        <li>Works in any Java 8+ environment (recommended Java 17 or higher).</li>
      </ul>
    </div>
  </div>

  <h2>How to run (quick)</h2>
  <p>Using the command line (if not using an IDE):</p>
  <pre><code>javac -d out $(find src/main/java -name "*.java")
java -cp out org.example.Main</code></pre>

  <p>In Eclipse: Import as existing Java project and run <code>org.example.Main</code>.</p>
  <p>In IntelliJ: mark <code>src/main/java</code> as Sources Root, set Project SDK, Rebuild, then run <code>Main</code>.</p>

  <h2>Interactive menu (features)</h2>
  <ul>
    <li>Borrow item (enter item id, user id, duration string)</li>
    <li>Return item (enter item id, user id, return offset)</li>
    <li>Search by type (Book / Audiobook / E-Magazine / All)</li>
    <li>Show user fines</li>
    <li>List all items</li>
  </ul>

  <h2>Duration formats supported</h2>
  <p>The parser accepts patterns like:</p>
  <ul>
    <li><code>7</code> — plain days</li>
    <li><code>7d</code>, <code>7 days</code></li>
    <li><code>2w</code>, <code>2 weeks</code> (converted to days by ×7)</li>
    <li><code>1 month</code> (approximate: 30 days)</li>
  </ul>

  <h2>Sample console run (expected)</h2>
  <pre><code>===== LibraNet Menu =====
1. Borrow Item
2. Return Item
3. Search by Type
4. Show User Fines
5. List All Items
6. Exit
Choose an option: 1
Available items:
1: Java Fundamentals by James Gosling
2: Clean Code (Audio) by Robert Martin
...
Enter Item ID to borrow: 1
Enter User ID: 101
Enter duration (e.g., 5d, 2w, 7): 5d
Java Fundamentals borrowed by user 101 for 5 days.
Item borrowed successfully!

Choose an option: 2
Enter Item ID to return: 1
Enter User ID: 101
Enter return offset (e.g., 0, 3d, 1w): 7d
Returned late. Fine: Rs.20.0
</code></pre>

  <h2>Design notes — why this meets the brief</h2>
  <ul>
    <li><em>Reusability:</em> common fields and behavior live in <code>LibraryItem</code>; new item types can extend it.</li>
    <li><em>Extensibility:</em> new behaviors can be exposed via interfaces (e.g. <code>Playable</code>) without modifying existing code.</li>
    <li><em>Robust data handling:</em> durations are parsed, IDs are integers with validation, borrow/fine history is preserved in records and collections.</li>
    <li><em>Error handling:</em> invalid operations throw clear exceptions with helpful messages (invalid id, item not available, wrong user returning, invalid duration string).</li>
  </ul>

  <h2>Possible improvements (optional)</h2>
  <ul>
    <li>Add a <code>User</code> class to store user details (name, contact) instead of plain integer IDs.</li>
    <li>Persist data to JSON/CSV or a small embedded database (H2) for persistence between runs.</li>
    <li>Add unit tests (JUnit) for borrow/return/fine logic.</li>
    <li>Expose a REST API (Spring Boot) for remote clients.</li>
  </ul>

  <h2>License</h2>
  <p>MIT-style — feel free to adapt for your coursework.</p>

  <footer>
    <strong>Author:</strong> Your Name • <em>LibraNet — simple library demo</em><br>
    Created as part of an exercise to demonstrate clean object-oriented design, error handling and data management.
  </footer>
</body>
</html>
