package dataStore;

import java.util.List;

/**
 * Created by sokam on 2/8/15.
 */
public class QueryTool {
  public static final String USAGE = "USAGE: ./query " +
      "-s column[:aggregate]{,column[:aggregate]} " +
      "[-o column{,column}] " +
      "[-g column] " +
      "[-f [(]column=data {[(] (AND|OR) column=data} [)]]";
  public static final String SYNTAX_ERR = "Syntax Error.";
  public static final String COLUMN_ERR = "Column Name is not in the Database.";
  private static final String UNKNOWN_COMMAND_ERR = "Unknown command.";
  public static final String EOL = "\n";
  public static final String BLANK = " ";
  public static final String COMMA = ",";
  public static final String COLON = ":";
  public static final String EQUAL = "=";
  public static final String DASH = "-";
  public static final String SELECT = "s";
  public static final String ORDER = "o";
  public static final String FILTER = "f";
  public static final String GROUP = "g";
  public static final String AND = "AND";
  public static final String OR = "OR";
  public static final String OPEN = "(";
  public static final String CLOSE = ")";

  public static final String MAX = "max";
  public static final String MIN = "min";
  public static final String SUM = "sum";
  public static final String COUNT = "count";
  public static final String COLLECT = "collect";

  private DataStore dataStore;
  private List<String> tokens;
  private String nextToken;
  private int nextIndex;

  private Expression expression;

  public QueryTool(DataStore dataStore) {
    this.dataStore = dataStore;
    this.expression = new Expression();
    this.tokens = null;
    this.nextToken = "";
    this.nextIndex = 0;
  }

  public String query(String queryString) {
    QueryScanner queryScanner = new QueryScanner(queryString);
    return query(queryScanner.getTokens());
  }

  public String query(List<String> tokens) {
    this.tokens = tokens;
    String output = "";

    try {
      getToken();
      buildDataStoreQuery();

      if (!nextToken.equals(EOL)) {
        throw new IllegalArgumentException(USAGE);
      }
    } catch (IllegalArgumentException msg) {
      System.out.println(SYNTAX_ERR + EOL + msg);
    }

    return output;
  }

  public Expression getQuery() {
    return this.expression;
  }

  private void buildDataStoreQuery() {
    while (isValidCommand()) {
      print("In buildDataStoreQuery while");
      getCommandArgument();
    }
  }

  private boolean isValidCommand() {
    return this.nextToken.equals(DASH + SELECT) ||
        this.nextToken.equals(DASH + ORDER);
  }

  private void getCommandArgument() {
    print("IN getCommandArgument");

    String command = this.nextToken.substring(1,2);
    getToken();
    this.expression.add(command, getListOfArguments(command));
  }



  private QueryArgument getListOfArguments(String command) {
    print("In Arguments");
    QueryArgument arguments = new QueryArgument();
    arguments.add(getCriteria(command));
    getToken();
    while (this.nextToken.equals(COMMA)) {
      print ("In Argument While");
      getToken();
      arguments.add(getCriteria(command));
      getToken();
    }
    return arguments;
  }

  private Criteria getCriteria(String command) {
    Criteria criteria = null;

    if (command.equals(SELECT)) {
      criteria = getSelectCriteria();
    } else if (command.equals(ORDER)) {
      criteria = getOrderCriteria();
    } else {
      throw new IllegalArgumentException(UNKNOWN_COMMAND_ERR);
    }

    return criteria;
  }

  private Criteria getOrderCriteria() {
    return new OrderCriteria(columns());
  }

  private Criteria getSelectCriteria() {
    return new SelectCriteria(columns());
  }

  private String columns() {
    if (dataStore.getListOfColumn().contains(this.nextToken))
      return this.nextToken;
    else
      throw new IllegalArgumentException(COLUMN_ERR);
  }


  private void getToken() {
    int len = tokens.size();
    if (this.nextIndex < len) {
      this.nextToken = this.tokens.get(nextIndex++);
    } else {
      this.nextToken = EOL;
    }
    print(this.nextToken, this.nextIndex+"", tokens.toString());
  }

  private void print(String... msgs) {
    for (String msg : msgs)
      System.out.print(msg + " ");
    System.out.println();
  }
}
