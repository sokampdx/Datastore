package dataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/8/15.
 */
public class Scanner {
  private static final String USAGE = "USAGE: ./query " +
      "-s column[:aggregate]{,column[:aggregate]} " +
      "[-o column{,column}] " +
      "[-g column] " +
      "[-f [(]column=data {[(] (AND|OR) column=data} [)]]";
  private static final String EXPECTED_QUOTE = "Expect a matching quote.";
  public final String EOL = "|";
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
  public final String QUOTE = "\"";
  public final String SINGLE = "'";
  
  public final String MAX = "MAX";
  public final String MIN = "MIN";
  public final String SUM = "SUM";
  public final String COUNT = "COUNT";
  public final String COLLECT = "COLLECT";
  
  private String stream;
  private String nextChar;
  private int nextIndex;
  private List<String> tokens;


  public Scanner(String stream) throws InterruptedException {
    this.stream = stream;
    this.tokens = new ArrayList<String>();
    this.nextChar = "";
    this.nextIndex = 0;
    tokenizer();
  }

  public List<String> getTokens() {
    return this.tokens;
  }

  private void tokenizer() throws InterruptedException {
    getChar();

    while (!isEOL()) {
      String token = getNextToken();
      if (token.length() > 0)
        this.tokens.add(token);
    }
  }

  private String getNextToken() {
    String currentToken = "";

    if (isQuote()) {
      currentToken += getQuotedToken();
    } else if (isOPEN()) {
      currentToken += OPEN;
      getChar();
    } else if (isCLOSE()) {
      currentToken += CLOSE;
      getChar();
    } else if (isCOMMA()) {
      currentToken += COMMA;
      getChar();
    } else if (isCOLON()) {
      currentToken += COLON;
      getChar();
    } else if (isEQUAL()) {
      currentToken += EQUAL;
      getChar();
    } else if (isWhiteSpace()) {
      getChar();
    } else {
      currentToken += getNormalToken();
    }

    return currentToken;
  }

  private boolean isOPEN() {
    return this.nextChar.equals(OPEN);
  }

  private boolean isCLOSE() {
    return this.nextChar.equals(CLOSE);
  }

  private boolean isCOMMA() {
    return this.nextChar.equals(COMMA);
  }

  private boolean isCOLON() {
    return this.nextChar.equals(COLON);
  }

  private boolean isEQUAL() {
    return this.nextChar.equals(EQUAL);
  }

  private String getNormalToken() {
    String currentToken = "";
    while (!isSeparator()) {
      currentToken += this.nextChar;
      getChar();
    }
    return currentToken;
  }

  private boolean isSeparator() {
    return isWhiteSpace() || isOPEN() || isCLOSE() || isEOL() || isCOMMA() || isCOLON() || isEQUAL();
  }

  private boolean isWhiteSpace() {
    return this.nextChar.equals(BLANK) || this.nextChar.equals(SINGLE);
  }

  private String getQuotedToken() {
    String currentToken = nextChar;
    getChar();

    while (!isQuote() && !isEOL()) {
      currentToken += nextChar;
      getChar();
    }

    if(isEOL()) {
      throw new IllegalArgumentException(EXPECTED_QUOTE);
    } else {
      currentToken += nextChar;
      getChar();
    }
    return currentToken;
  }

  private boolean isQuote() {
    return this.nextChar.equals(QUOTE);
  }

  private boolean isEOL() {
    return this.nextChar.equals(EOL);
  }

  private void getChar() {
    int len = this.stream.length() - 1;
    if (this.nextIndex > len) {
      this.nextChar = EOL;
    } else {
      this.nextChar = this.stream.substring(nextIndex, nextIndex + 1);
      ++nextIndex;
    }
  }
}
