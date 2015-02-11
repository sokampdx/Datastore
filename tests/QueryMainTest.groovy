import dataStore.MyUtil
import dataStore.QueryMain

/**
 * Created by sokam on 2/10/15.
 */
class QueryMainTest extends GroovyTestCase  {
    public final String COMMA = ",";
    public final String EOL = "\n";

    public final String query1 = "-s TITLE,REV,DATE";
    public final String query1Answer =
            "the matrix,4.00,2014-04-01\n" +
            "unbreakable,6.00,2014-04-03\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n";
    public final String query2 = "-s TITLE,REV,DATE -o DATE,TITLE";
    public final String query2Answer =
            "the matrix,4.00,2014-04-01\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n" +
            "unbreakable,6.00,2014-04-03\n";
    public final String query3 = "-f DATE=2014-04-02";
    public final String query3Answer =
            "stb2,the hobbit,warner bros,2014-04-02,8.00,2:45\n" +
            "stb3,the matrix,warner bros,2014-04-02,4.00,1:05";

    public final String query4 = "-s TITLE,REV,DATE -f DATE=2014-04-01";
    public final String query4Answer = "the matrix,4.00,2014-04-01";

    public final String query5 = "-o DATE, TITLE";
    public final String query5Answer =
            "stb1,the matrix,warner bros,2014-04-01,4.00,1:30\n" +
            "stb2,the hobbit,warner bros,2014-04-02,8.00,2:45\n" +
            "stb3,the matrix,warner bros,2014-04-02,4.00,1:05\n" +
            "stb1,unbreakable,buena vista,2014-04-03,6.00,2:05\n";

    public final String query6 = "-s TITLE,REV,DATE -o DATE,REV -f DATE=2014-04-02";
    public final String query6Answer =
            "the matrix,4.00,2014-04-02\n" +
            "the hobbit,8.00,2014-04-02\n";

    public final String query7 = "-s REV:min";
    public final String query7Answer = "4.00\n"

    public final String query8 = "-s VIEW_TIME:min";
    public final String query8Answer = "1:05\n"

    public final String query9 = "-s DATE:max";
    public final String query9Answer = "2014-04-03\n"

    public final String query10 = "-s VIEW_TIME:max";
    public final String query10Answer = "2:45\n"

    public final String query11 = "-s TITLE,PROVIDER:max,VIEW_TIME:max";
    public final String query11Answer = "the matrix,warner bros,2:45\n"

    public void testSimpleSelect () {
        String result = QueryMain.run(query1);
        String[] expected = query1Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testSimpleFilter () {
        String result = QueryMain.run(query3);
        String[] expected = query3Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testSimpleSelectWithFilter () {
        String result = QueryMain.run(query4);
        String[] expected = query4Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
    }

    public void testOrder () {
        String result = QueryMain.run(query5);
        assertEquals(query5Answer.toString(), result.toString());
    }

    public void testSimpleSelectWithOrder () {
        String result = QueryMain.run(query2);
        assertEquals(query2Answer.toString(), result.toString());
    }

    public void testSimpleSelectWithOrderWithSimpleFilter () {
        String result = QueryMain.run(query6);
        assertEquals(query6Answer.toString(), result.toString());
    }

    public void testMinRev () {
        String result = QueryMain.run(query7);
        assertEquals(query7Answer.toString(), result.toString());
    }

    public void testMinTIME () {
        String result = QueryMain.run(query8);
        assertEquals(query8Answer.toString(), result.toString());
    }

    public void testMaxDATE () {
        String result = QueryMain.run(query9);
        assertEquals(query9Answer.toString(), result.toString());
    }

    public void testMaxTIME () {
        String result = QueryMain.run(query10);
        assertEquals(query10Answer.toString(), result.toString());
    }

    public void testMaxPROVIDERSelectTITLE () {
        String result = QueryMain.run(query11);
        assertEquals(query11Answer.toString(), result.toString());
    }

}
