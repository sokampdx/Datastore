package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public class SelectCriteria extends Criteria {
  public static final String COLON = ":";

  private String aggregate;

  public SelectCriteria(String column) {
    super(column);
    this.aggregate = "";
  }

  public SelectCriteria(String column, String aggregate) {
    super(column);
    this.aggregate = aggregate;
  }

  public String getAggregate() {
    return this.aggregate;
  }

  @Override
  public String toString() {
    String string = super.toString();

    if (this.aggregate.length()>0) {
      string += COLON + this.aggregate;
    }

    return string;
  }
}
