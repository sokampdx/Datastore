import dataStore.QueryMain

/**
 * Created by sokam on 2/10/15.
 */
class QueryMainTest extends GroovyTestCase  {
    public final String COMMA = ",";
    public final String EOL = "\n";

    public final String query1 = "-s TITLE,REV,DATE";
    public final String query1Answer = "the matrix,4.00,2014-04-01\n" +
            "unbreakable,6.00,2014-04-03\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n";
    public final String query2 = "-s TITLE,REV,DATE -o DATE,TITLE";
    public final String query2Answer = "the matrix,4.00,2014-04-01\n" +
            "the hobbit,8.00,2014-04-02\n" +
            "the matrix,4.00,2014-04-02\n" +
            "unbreakable,6.00,2014-04-03\n";

    public void testSimpleSelect () {
        String result = QueryMain.run(query1);
        String[] expected = Arrays.sort(query1Answer.split(EOL));
        String[] actual = Arrays.sort(result.split(EOL));
        assertEquals(expected.toString(), actual.toString());
    }
}
