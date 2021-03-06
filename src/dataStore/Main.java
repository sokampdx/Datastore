package dataStore;

import dataStore.DataStorage.*;
import dataStore.QueryStruct.CommandArgumentList;
import dataStore.QueryStruct.Expression;
import dataStore.QueryTool.*;
import dataStore.Records.Record;
import dataStore.Util.MyUtil;

import java.util.List;
import java.util.Map;

/**
 * Created by sokam on 2/10/15.
 */
public abstract class Main {
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
    List<List<Record>> records = dataStore.getRecords();
    List<String> columns = dataStore.getColumns();

    Tokenizer tokenizer = new Tokenizer(input);
    Parser queryParser = new Parser(columns);
    queryParser.query(tokenizer.getTokens());

    Expression expression = queryParser.getQuery();
    Map<String, CommandArgumentList> commandList = expression.getExpression();
    DataFactory dataFactory = new DataFactory(records, columns, commandList);

    return MyUtil.ListOfListOfRecordToString(dataFactory.getResult());
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
