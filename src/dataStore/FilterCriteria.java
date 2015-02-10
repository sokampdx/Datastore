package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public class FilterCriteria extends Criteria {
  private final String EQUAL = "=";

  private String match;
  private String binOp;

  public FilterCriteria (String column, String match) {
    super(column);
    this.match = match;
  }

  public FilterCriteria (String binOp) {
    super("");
    this.match = "";
    this.binOp = binOp;
  }

  @Override
  public boolean equals (Criteria filterCriteria) {
    boolean isMatch = super.equals(filterCriteria);
    if (filterCriteria instanceof FilterCriteria) {
      isMatch &= this.match.equals(((FilterCriteria) filterCriteria).match);
      isMatch &= this.binOp.equals(((FilterCriteria) filterCriteria).binOp);
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
