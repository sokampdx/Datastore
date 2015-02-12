package dataStore;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by sokam on 2/10/15.
 */
public abstract class Main {
  public static final String SELECT = "s";
  public static final String ORDER = "o";
  public static final String FILTER = "f";
  public static final String GROUP = "g";
  public static final String COMMA = ",";
  public final static String FILENAME = "original";

  public static String createInput(String[] args) {
    String result = "";
    for (String arg : args) {
      result += arg + " ";
    }
    return result;
  }

  public static String run(String[] args) {
    DataStore dataStore = new TextFileDataStore(Main.FILENAME);
    List<List<Record>> current = dataStore.getRecords();

    String input = Main.createInput(args);
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

    return MyUtil.ListOfListOfRecordToString(current);
}

  public static void printResult(String result) {
    System.out.println(result);
  }

  public static void main(String[] args) {
//    System.out.println(Arrays.asList(args).toString());
    String result = Main.run(args);
    Main.printResult(result);
  }


}
