package dataStore;

/**
 * Created by sokam on 2/8/15.
 */
public class Query {
  private static final String USAGE = "USAGE: ./query " +
      "-s column[:aggregate]{,column[:aggregate]} " +
      "[-o column{,column}] " +
      "[-g column] " +
      "[-f [(]column=data {[(] (AND|OR) column=data} [)]]";
  public final String EOL = "\n";
  public final String BLANK = " ";
  public final String COMMA = ",";
  public final String COLON = ":";
  public final String EQUAL = "=";
  public final String DASH = "-";
  public final String SELECT = "s";
  public final String ORDER = "o";
  public final String FILTER = "f";
  public final String GROUP = "g";
  public final String AND = "AND";
  public final String OR = "OR";
  public final String OPEN = "(";
  public final String CLOSE = ")";

  public final String MAX = "MAX";
  public final String MIN = "MIN";
  public final String SUM = "SUM";
  public final String COUNT = "COUNT";
  public final String COLLECT = "COLLECT";

  private DataStore dataStore;
  private String queryInput;
  private String nextChar;
  private int nextIndex;

  public Query(DataStore dataStore) {
    this.dataStore = dataStore;

  }

  public String query(String queryInput) {
    this.queryInput = queryInput;
    String output = "";

    try {
      getNextChar();
      expression();

      if (!nextChar.equals(EOL)) {
        throw new IllegalArgumentException(USAGE);
      }
    } catch (IllegalArgumentException msg) {
      System.out.println("Syntax Error" + msg);
    }

    return output;
  }

  private void expression() {
    while (this.nextChar.equals(DASH)) {
      getNextChar();
      commands();
    }

  }

  private void commands() {
    if (this.nextChar.equals(SELECT)) {
      getNextChar();
      select();
    }
  }

  private void select() {
    selectArgs();
    while (this.nextChar.equals(COMMA)) {
      getNextChar();
      selectArgs();
    }
  }

  private void selectArgs() {
    columns();
  }

  private void columns() {

  }


  private void getNextChar() {
    do {
      this.nextChar = getChar();
    } while (this.nextChar.equals(BLANK));
  }

  private String getChar() {
    int len = queryInput.length();
    if (this.nextIndex >= len) {
      return EOL;
    }
    return this.queryInput.substring(nextIndex, ++nextIndex);
  }


}
