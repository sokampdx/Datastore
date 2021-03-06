package dataStore.QueryStruct;

/**
 * Created by sokam on 2/9/15.
 */
public abstract class Criteria {
  private String column;

  public Criteria (String column) {
    this.column = column;
  }

  public String getMatch() {
    return "";
  }

  public String getBinOp() {
    return "";
  }

  public boolean equals(Criteria match) {
    return this.column.equals(match.column);
  }

  public String getColumn() {
    return this.column;
  }

  public boolean isBinOp () {
    return false;
  }

  @Override
  public String toString() {
    return this.column;
  }
}
