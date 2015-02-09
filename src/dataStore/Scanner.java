package dataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/8/15.
 */
public class Scanner {
  public static final String USAGE = "USAGE: ./query " +
      "-s column[:aggregate]{,column[:aggregate]} " +
      "[-o column{,column}] " +
      "[-g column] " +
      "[-f [(]column=data {[(] (AND|OR) column=data} [)]]";
  public static final String EXPECTED_QUOTE = "Expect a matching quote." + "\n" + USAGE;
  public final String EOL = "|";
  public final String BLANK = " ";
  public final String COMMA = ",";
  public final String COLON = ":";
  public final String EQUAL = "=";
  public final String OPEN = "(";
  public final String CLOSE = ")";
  public final String DOUBLE_QUOTE = "\"";
  public final String SINGLE_QUOTE = "'";

  private String stream;
  private String nextChar;
  private int nextIndex;
  private List<String> tokens;

  public Scanner()  {
    this.stream = "";
    this.tokens = new ArrayList<String>();
    this.nextChar = "";
    this.nextIndex = 0;
  }

  public Scanner(String stream){
    this.stream = stream;
    this.tokens = new ArrayList<String>();
    this.nextChar = "";
    this.nextIndex = 0;
    tokenizer();
  }

  public List<String> getTokens() {
    return this.tokens;
  }

  private void tokenizer() {
    getChar();

    while (!isEOL()) {
      String token = getNextToken();
      if (token.length() > 0)
        this.tokens.add(token);
    }
  }

  private String getNextToken() {
    String currentToken = "";

    if (isDoubleQuote()) {
      currentToken += getDoubleQuotedToken();
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
    return isBLANK() || isSingleQuote();
  }

  private boolean isSingleQuote() {
    return this.nextChar.equals(SINGLE_QUOTE);
  }

  private boolean isBLANK() {
    return this.nextChar.equals(BLANK);
  }

  private String getDoubleQuotedToken() {
    String currentToken = nextChar;
    getChar();

    while (!isDoubleQuote() && !isEOL()) {
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

  private boolean isDoubleQuote() {
    return this.nextChar.equals(DOUBLE_QUOTE);
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
