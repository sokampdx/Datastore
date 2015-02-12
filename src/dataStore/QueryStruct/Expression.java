package dataStore.QueryStruct;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sokam on 2/9/15.
 */
public class Expression {

  private Map<String, CommandArgumentList> expression;

  public Expression() {
    this.expression = new HashMap<String, CommandArgumentList>();
  }

  public void add(String queryCommand, CommandArgumentList commandArgumentList) {
    this.expression.put(queryCommand, commandArgumentList);
  }

  public boolean equals(Expression match) {
    boolean isMatch = true;

    for (Map.Entry<String, CommandArgumentList> pair : this.expression.entrySet()) {
      isMatch &= hasKey(match, pair) && isMatchArgs(match, pair);
    }

    return isMatch;
  }

  public Map<String, CommandArgumentList> getExpression() {
    return this.expression;
  }

  private boolean isMatchArgs(Expression match, Map.Entry<String, CommandArgumentList> pair) {
    return pair.getValue().equals(match.expression.get(pair.getKey()));
  }

  private boolean hasKey(Expression match, Map.Entry<String, CommandArgumentList> pair) {
    return match.expression.containsKey(pair.getKey());
  }

  @Override
  public String toString() {
    String COLON = ":";
    String EOL = "\n";
    String string = "";
    for (Map.Entry<String, CommandArgumentList> pair : this.expression.entrySet()) {
      string += pair.getKey() + COLON + pair.getValue().toString() + EOL;
    }
    return string.substring(0, string.length()-1);
  }
}
