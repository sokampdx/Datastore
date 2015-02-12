import dataStore.QueryStruct.*
import dataStore.DataStorage.*
import dataStore.QueryTool.*

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
    public final String query7 = "-s TITLE,REV:max,STB -g TITLE";

    public final String query8 = "-s TITLE,REV -f STB=\"stb1\" OR (TITLE=\"the hobbit\" OR TITLE=\"unbreakable\") AND DATE=2014-04-01";
    public final String query8Scan = "[-s, TITLE, ,, REV, -f, STB, =, stb1, OR, (, TITLE, =, the hobbit, OR, TITLE, =, unbreakable, ), AND, DATE, =, 2014-04-01]";

    public final String queryErr1 = "-s -o DATE,TITLE";
    public final String queryErr2 = "-s TITLE,REV:,STB:collect -g TITLE";
    public final String queryErr3 = "-s TITLE,REV,DATE -f DATE";
    public final String queryErr4 = "-s TITLE,REV,DATE -w DATE,TITLE";
    public final String queryErr5 = "-s TITLE,PROVIDER,STB -g TITLE";

    private DataStore dataStore = new TextFileDataStore(ORIGINAL);
    private Parser queryTool = new Parser(dataStore.getColumns());

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

    public void testScannerForSelectAdvanceFilterWithParen() {
        Scanner scanner = new Scanner(query8);
        assertEquals(query8Scan, scanner.getTokens().toString());
    }

    public void testScannerForIncorrectAdvanceFilter() {
        def msg = shouldFail(IllegalArgumentException) {
            new Scanner(query6);
        }
        assertEquals(Scanner.EXPECTED_QUOTE, msg);
    }

    public void testQueryForSelect() {
        Expression expression = select_TITLE_REV_DATE()
        queryTool.query(query1);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_DATE() {
        Expression expression = new Expression();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV");
        arguments.add(criteria);
        criteria = new SelectCriteria("DATE");
        arguments.add(criteria);
        expression.add(Parser.SELECT, arguments);
        return expression;
    }

    public void testQueryForSelectOrder() {
        Expression expression = select_TITLE_REV_DATE_order_DATE_TITLE();
        queryTool.query(query2);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_DATE_order_DATE_TITLE() {
        Expression expression = select_TITLE_REV_DATE();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new OrderCriteria("DATE");
        arguments.add(criteria);
        criteria = new OrderCriteria("TITLE");
        arguments.add(criteria);
        expression.add(Parser.ORDER, arguments);
        return expression;
    }

    public void testQueryForSelectFilter() {
        Expression expression = select_TITLE_REV_DATE_filter_DATE_2014_04_01();
        queryTool.query(query3);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_DATE_filter_DATE_2014_04_01() {
        Expression expression = select_TITLE_REV_DATE();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new FilterCriteria("DATE", "2014-04-01");
        arguments.add(criteria);
        expression.add(Parser.FILTER, arguments);
        return expression;
    }

    public void testQueryForSelectGroup() {
        Expression expression = select_TITLE_REV_STB_group_TITLE();
        queryTool.query(query7);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_STB_group_TITLE() {
        Expression expression = new Expression();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV", "max");
        arguments.add(criteria);
        criteria = new SelectCriteria("STB");
        arguments.add(criteria);
        expression.add(Parser.SELECT, arguments);

        CommandArgumentList argument2 = new CommandArgumentList();
        criteria = new GroupCriteria("TITLE");
        argument2.add(criteria);
        expression.add(Parser.GROUP, argument2);
        return expression;
    }

    public void testQueryForSelectAggregateGroup() {
        Expression expression = select_TITLE_REVsum_STBcollect_group_TITLE();
        queryTool.query(query4);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REVsum_STBcollect_group_TITLE() {
        Expression expression = new Expression();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV", "sum");
        arguments.add(criteria);
        criteria = new SelectCriteria("STB", "collect");
        arguments.add(criteria);
        expression.add(Parser.SELECT, arguments);

        CommandArgumentList argument2 = new CommandArgumentList();
        criteria = new GroupCriteria("TITLE");
        argument2.add(criteria);
        expression.add(Parser.GROUP, argument2);
        return expression;
    }

    public void testQueryForSelectAdvanceFilterWithParen() {
        Expression expression = select_TITLE_REV_filter_With_Paren();
        queryTool.query(query8);
        assertEquals(expression.toString(), queryTool.getQuery().toString());
    }

    private Expression select_TITLE_REV_filter_With_Paren() {
        Expression expression = new Expression();
        CommandArgumentList arguments = new CommandArgumentList();
        Criteria criteria = new SelectCriteria("TITLE");
        arguments.add(criteria);
        criteria = new SelectCriteria("REV");
        arguments.add(criteria);
        expression.add(Parser.SELECT, arguments);

        CommandArgumentList argument1 = new CommandArgumentList();
        criteria = new FilterCriteria("STB", "stb1");
        argument1.add(criteria);
        criteria = new FilterCriteria(Parser.OR);
        argument1.addBinOp(criteria);

        CommandArgumentList argument2 = new CommandArgumentList();
        criteria = new FilterCriteria("TITLE", "the hobbit");
        argument2.add(criteria);
        criteria = new FilterCriteria(Parser.OR);
        argument2.addBinOp(criteria);
        criteria = new FilterCriteria("TITLE", "unbreakable");
        argument2.add(criteria);
        criteria = new FilterCriteria(Parser.AND);
        argument2.addBinOp(criteria);
        criteria = new FilterCriteria("DATE", "2014-04-01");
        argument2.add(criteria);

        argument1.addAll(argument2);
        expression.add(Parser.FILTER, argument1);
        return expression;
    }

    public void testQueryNoSelectColumn() {
        def msg = shouldFail(IllegalArgumentException) {
            queryTool.query(queryErr1);
        }
        assertEquals(Parser.COLUMN_ERR, msg);
    }

    public void testQueryNoAggregateAfterColon() {
        def msg = shouldFail(IllegalArgumentException) {
            queryTool.query(queryErr2);
        }
        assertEquals(Parser.UNKNOWN_AGGREGATE_ERR, msg);
    }

    public void testQueryNoEqualInFilter() {
        def msg = shouldFail(IllegalArgumentException) {
            queryTool.query(queryErr3);
        }
        assertEquals(Parser.INCORRECT_FILTER_ERR, msg);
    }

    public void testQueryUnknownCommand() {
        def msg = shouldFail(IllegalArgumentException) {
            queryTool.query(queryErr4);
        }
        assertEquals(Parser.USAGE, msg);
    }

    public void testQueryGroupWithoutAggregate() {
        def msg = shouldFail(IllegalArgumentException) {
            queryTool.query(queryErr5);
        }
        assertEquals(Parser.GROUP_AND_AGGREGATE_ERR, msg);
    }
}
