package dataStore;

/**
 * Created by sokam on 2/9/15.
 */
public abstract class Criteria {
  private String column;

  public Criteria (String column) {
    this.column = column;
  }

  public boolean equals(Criteria match) {
    return this.column.equals(match.column);
  }
}
