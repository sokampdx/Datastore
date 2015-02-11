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
    public final String query2Answer = "the matrix,4.00,2014-04-01\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n" +
            "unbreakable,6.00,2014-04-03\n";
    public final String query3 = "-f DATE=2014-04-02";
    public final String query3Answer =
            "stb2,the hobbit,warner bros,2014-04-02,8.00,2:45\n" +
            "stb3,the matrix,warner bros,2014-04-02,4.00,1:05";

    public final String query4 = "-s TITLE,REV,DATE -f DATE=2014-04-01";
    public final String query4Answer = "the matrix,4.00,2014-04-01";


    public void testSimpleSelect () {
        String result = QueryMain.run(query1);
        String[] expected = query1Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
        MyUtil.print(actual.toString());
    }

    public void testSimpleFilter () {
        String result = QueryMain.run(query3);
        String[] expected = query3Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
        MyUtil.print(actual.toString());
    }

    public void testSimpleSelectWithFilter () {
        String result = QueryMain.run(query4);
        String[] expected = query4Answer.split(EOL);
        String[] actual = result.split(EOL);
        Arrays.sort(expected);
        Arrays.sort(actual);
        assertEquals(expected.toString(), actual.toString());
        MyUtil.print(actual.toString());
    }
}
