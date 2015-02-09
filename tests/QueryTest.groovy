import dataStore.DataStore
import dataStore.Query
import dataStore.Scanner
import dataStore.TextFileDataStore

/**
 * Created by sokam on 2/8/15.
 */
class QueryTest extends GroovyTestCase {
    public final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    public final ByteArrayOutputStream errContent = new ByteArrayOutputStream();

    public final ORIGINAL = "original";
    public final String query1 = "-s TITLE,REV,DATE";
    public final String query1Scan = "[-s, TITLE, ,, REV, ,, DATE]";
    public final String query1Answer = "the matrix,4.00,2014-04-01\n" +
            "unbreakable,6.00,2014-04-03\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n";
    public final String query2 = "-s TITLE,REV,DATE -o DATE,TITLE";
    public final String query2Scan = "[-s, TITLE, ,, REV, ,, DATE, -o, DATE, ,, TITLE]";
    public final String query2Answer = "the matrix,4.00,2014-04-01\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n" +
            "unbreakable,6.00,2014-04-03\n";
    public final String query3 = "-s TITLE,REV,DATE -f DATE=2014-04-01";
    public final String query3Scan = "[-s, TITLE, ,, REV, ,, DATE, -f, DATE, =, 2014-04-01]";

    public final String query4 = "-s TITLE,REV:sum,STB:collect -g TITLE";
    public final String query4Scan = "[-s, TITLE, ,, REV, :, sum, ,, STB, :, collect, -g, TITLE]"

    public final String query5 = "-s TITLE,REV -f 'TITLE=\"the hobbit\" OR TITLE=\"the matrix\"'";
    public final String query5Scan = "[-s, TITLE, ,, REV, -f, TITLE, =, \"the hobbit\", OR, TITLE, =, \"the matrix\"]";
    
    public final String query6 = "-s TITLE,REV -f 'TITLE=\"the hobbit\" OR TITLE=\"the matrix' -o TITLE";
    
    
    private DataStore dataStore = new TextFileDataStore(ORIGINAL);
    private Query queryParser = new Query(dataStore);

    public void testScannerForSelect() {
        Scanner scanner = new Scanner(query1);
        assertEquals(query1Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectOrder() {
        Scanner scanner = new Scanner(query2);
        assertEquals(query2Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectFilter() {
        Scanner scanner = new Scanner(query3);
        assertEquals(query3Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectAggregateGroup() {
        Scanner scanner = new Scanner(query4);
        assertEquals(query4Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectAdvanceFilter() {
        Scanner scanner = new Scanner(query5);
        assertEquals(query5Scan, scanner.getTokens().toString());
    }

    public void testScannerForIncorrectAdvanceFilter() {
        def msg = shouldFail(IllegalArgumentException) {
            Scanner scanner = new Scanner(query6);
        }
        assertEquals(Scanner.EXPECTED_QUOTE, msg);
    }

}
