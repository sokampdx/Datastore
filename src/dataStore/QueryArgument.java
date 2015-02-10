package dataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/9/15.
 */
public class QueryArgument {
  private final String COMMA = ",";

  private List<Criteria> arguments;

  public QueryArgument () {
    this.arguments = new ArrayList<Criteria>();
  }

  public void add(Criteria criteria) {
    this.arguments.add(criteria);
  }

  public boolean equals (QueryArgument match) {
    boolean isEqual = true;
    int len = this.arguments.size();

    if (len != match.arguments.size()) {
      isEqual = false;
    } else {

      for (int i = 0; i < len; ++i) {
        isEqual &= this.arguments.get(i).equals(match.arguments.get(i));
      }
    }

    return isEqual;
  }

  @Override
  public String toString() {
    String string = "";
    for (Criteria criteria : arguments) {
      string += criteria.toString() + COMMA;
    }

    return string.substring(0, string.length()-1);
  }
}
