package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public class FilterCriteria extends Criteria {

  private String match;
  private String binOp;

  public FilterCriteria (String column, String match) {
    super(column);
    this.match = match;
    this.binOp = "";
  }

  public FilterCriteria (String binOp) {
    super("");
    this.match = "";
    this.binOp = binOp;
  }

  @Override
  public boolean isBinOp () {
    return this.binOp.length() > 0;
  }

  public String getMatch() {
    return this.match;
  }

  public String getBinOp() {
    return this.binOp;
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
    String EQUAL = "=";
    if (this.binOp.length() > 0) {
      return binOp;
    } else {
      return super.toString() + EQUAL + this.match;
    }
  }
}
