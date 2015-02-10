import dataStore.Criteria
import dataStore.DataStore
import dataStore.Expression
import dataStore.FilterCriteria
import dataStore.GroupCriteria
import dataStore.OrderCriteria
import dataStore.QueryArgument
import dataStore.QueryTool
import dataStore.QueryScanner
import dataStore.SelectCriteria
import dataStore.TextFileDataStore

/**
 * Created by sokam on 2/8/15.
 */
class QueryToolTest extends GroovyTestCase {
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
    public final String query5Scan = "[-s, TITLE, ,, REV, -f, TITLE, =, the hobbit, OR, TITLE, =, the matrix]";
    
    public final String query6 = "-s TITLE,REV -f 'TITLE=\"the hobbit\" OR TITLE=\"the matrix' -o TITLE";
    public final String query7 = "-s TITLE,REV,STB -g TITLE";
    
    private DataStore dataStore = new TextFileDataStore(ORIGINAL);
    private QueryTool queryTool = new QueryTool(dataStore);

    public void testScannerForSelect() {
        QueryScanner scanner = new QueryScanner(query1);
        assertEquals(query1Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectOrder() {
        QueryScanner scanner = new QueryScanner(query2);
        assertEquals(query2Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectFilter() {
        QueryScanner scanner = new QueryScanner(query3);
        assertEquals(query3Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectAggregateGroup() {
        QueryScanner scanner = new QueryScanner(query4);
        assertEquals(query4Scan, scanner.getTokens().toString());
    }

    public void testScannerForSelectAdvanceFilter() {
        QueryScanner scanner = new QueryScanner(query5);
        assertEquals(query5Scan, scanner.getTokens().toString());
    }

    public void testScannerForIncorrectAdvanceFilter() {
        def msg = shouldFail(IllegalArgumentException) {
            QueryScanner scanner = new QueryScanner(query6);
        }
        assertEquals(QueryScanner.EXPECTED_QUOTE, msg);
    }

    public void testQueryForSelect() {
        Expression expression = select_TITLE_REV_DATE()
        queryTool.query(query1);
        assertTrue(expression.equals(queryTool.getQuery()));
    }

    private Expression select_TITLE_REV_DATE() {
        Expression expression = new Expression();
        QueryArgument arguments = new QueryArgument();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV");
        arguments.add(criteria);
        criteria = new SelectCriteria("DATE");
        arguments.add(criteria);
        expression.add(QueryTool.SELECT, arguments);
        return expression;
    }

    public void testQueryForSelectOrder() {
        Expression expression = select_TITLE_REV_DATE_order_DATE_TITLE();
        queryTool.query(query2);
        assertTrue(expression.equals(queryTool.getQuery()));
    }

    private Expression select_TITLE_REV_DATE_order_DATE_TITLE() {
        Expression expression = select_TITLE_REV_DATE();
        QueryArgument arguments = new QueryArgument();
        Criteria criteria = new OrderCriteria("DATE");
        arguments.add(criteria);
        criteria = new OrderCriteria("TITLE");
        arguments.add(criteria);
        expression.add(QueryTool.ORDER, arguments);
        return expression;
    }

    public void testQueryForSelectFilter() {
        Expression expression = select_TITLE_REV_DATE_filter_DATE_2014_04_01();
        queryTool.query(query3);
        assertTrue(expression.equals(queryTool.getQuery()));
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_DATE_filter_DATE_2014_04_01() {
        Expression expression = select_TITLE_REV_DATE();
        QueryArgument arguments = new QueryArgument();
        Criteria criteria = new FilterCriteria("DATE", "2014-04-01");
        arguments.add(criteria);
        expression.add(QueryTool.FILTER, arguments);
        return expression;
    }

    public void testQueryForSelectGroup() {
        Expression expression = select_TITLE_REV_STB_group_TITLE();
        queryTool.query(query7);
        assertTrue(expression.equals(queryTool.getQuery()));
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_STB_group_TITLE() {
        Expression expression = new Expression();
        QueryArgument arguments = new QueryArgument();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV");
        arguments.add(criteria);
        criteria = new SelectCriteria("STB");
        arguments.add(criteria);
        expression.add(QueryTool.SELECT, arguments);

        QueryArgument argument2 = new QueryArgument();
        criteria = new GroupCriteria("TITLE");
        argument2.add(criteria);
        expression.add(QueryTool.GROUP, argument2);
        return expression;
    }

}
