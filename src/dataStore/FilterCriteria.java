package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public class FilterCriteria extends Criteria {
  private final String EQUAL = "=";

  private String match;

  public FilterCriteria (String column, String match) {
    super(column);
    this.match = match;
  }

  @Override
  public boolean equals (Criteria filterCriteria) {
    boolean isMatch = super.equals(filterCriteria);
    if (filterCriteria instanceof FilterCriteria) {
      isMatch &= this.match.equals(((FilterCriteria) filterCriteria).match);
    } else {
      isMatch = false;
    }
    return isMatch;
  }

  @Override
  public String toString() {
    return super.toString() + EQUAL + this.match;
  }
}
