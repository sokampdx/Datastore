package dataStore;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sokam on 2/9/15.
 */
public class Expression {
  private final String COLON = ":";
  private final String EOL = "\n";

  private Map<String, QueryArgument> expression;

  public Expression() {
    this.expression = new HashMap<String, QueryArgument>();
  }

  public void add (String queryCommand, QueryArgument queryArgument) {
    this.expression.put(queryCommand, queryArgument);
  }

  public boolean equals(Expression match) {
    boolean isMatch = true;

    for (Map.Entry<String, QueryArgument> pair : this.expression.entrySet()) {
      isMatch &= hasKey(match, pair) && isMatchArgs(match, pair);
    }

    return isMatch;
  }

  private boolean isMatchArgs(Expression match, Map.Entry<String, QueryArgument> pair) {
    return pair.getValue().equals(match.expression.get(pair.getKey()));
  }

  private boolean hasKey(Expression match, Map.Entry<String, QueryArgument> pair) {
    return match.expression.containsKey(pair.getKey());
  }

  @Override
  public String toString() {
    String string = "";
    for (Map.Entry<String, QueryArgument> pair : this.expression.entrySet()) {
      string += pair.getKey() + COLON + pair.getValue().toString() + EOL;
    }
    return string.substring(0, string.length()-1);
  }
}
