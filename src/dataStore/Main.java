package dataStore;

import dataStore.DataStorage.DataStore;
import dataStore.DataStorage.TextFileDataStore;
import dataStore.QueryStruct.CommandArgumentList;
import dataStore.QueryStruct.Expression;
import dataStore.QueryTool.QueryDataFactory;
import dataStore.QueryTool.QueryParser;
import dataStore.QueryTool.QueryScanner;
import dataStore.Records.Record;
import dataStore.Util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by sokam on 2/10/15.
 */
public abstract class Main {
  // TODO: Remove constant after query logic has moved

  public final static String FILENAME = "original";

  public static String createInput(String[] args) {
    String result = "";
    for (String arg : args) {
      result += arg + " ";
    }
    return result;
  }

  public static String run(String[] args) {
    String input = Main.createInput(args);
    DataStore dataStore = new TextFileDataStore(FILENAME);
    List<List<Record>> current = dataStore.getRecords();
    List<String> columns = dataStore.getColumns();

    QueryScanner scanner = new QueryScanner(input);
    QueryParser queryParser = new QueryParser(dataStore);
    queryParser.query(scanner.getTokens());

    Expression expression = queryParser.getQuery();
    Map<String, CommandArgumentList> commandList = expression.getExpression();
    QueryDataFactory queryDataFactory= new QueryDataFactory(current, columns, commandList);

    return MyUtil.ListOfListOfRecordToString(queryDataFactory.getResult());
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
