package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public class FilterCriteria extends Criteria {
  private String match;

  public FilterCriteria (String column, String match) {
    super(column);
    this.match = match;
  }
}
