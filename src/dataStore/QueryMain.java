package dataStore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sokam on 2/10/15.
 */
public abstract class QueryMain {
  public static final String SELECT = "s";
  public static final String ORDER = "o";
  public static final String FILTER = "f";
  public static final String GROUP = "g";
  public static final String COMMA = ",";
  public final static String FILENAME = "original";

  public static String createInput(String[] args) {
    String result = "";
    for (String arg : args) {
      result += arg;
    }
    return result;
  }

  public static String run(String[] args) {
    String result = "";
    List<List<Record>> current = new ArrayList<List<Record>>();

    DataStore dataStore = new TextFileDataStore(QueryMain.FILENAME);
    current = dataStore.getRecords();

    String input = QueryMain.createInput(args);
    QueryScanner scanner = new QueryScanner(input);

    QueryParser queryParser = new QueryParser(dataStore);
    queryParser.query(scanner.getTokens());

    Expression expression = queryParser.getQuery();
    Map<String, QueryArgument> commandList = expression.getExpression();

    if (commandList.containsKey(FILTER)) {
      current = dataStore.filter(commandList.get(FILTER).getArguments(), current);
    }

    if (commandList.containsKey(ORDER)) {
      current = dataStore.order(commandList.get(ORDER).getArguments(), current);
    }

    if (commandList.containsKey(SELECT)) {
        current = dataStore.select(commandList.get(SELECT).getArguments(), current);


    }



    result = MyUtil.ListOfListOfRecordToString(current, COMMA);

    return result;
  }

  public static void printResult(String result) {
    System.out.println(result);
  }

  public static void main(String[] args) {
    String result = QueryMain.run(args);
    QueryMain.printResult(result);
  }


}
