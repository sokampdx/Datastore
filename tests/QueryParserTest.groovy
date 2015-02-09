import dataStore.QueryParser

/**
 * Created by sokam on 2/8/15.
 */
class QueryParserTest extends GroovyTestCase {
    private final String query1 = "-s TITLE,REV,DATE -o DATE,TITLE";

    public void testNewQueryParser() {
        QueryParser queryParser = new QueryParser();
    }
}
