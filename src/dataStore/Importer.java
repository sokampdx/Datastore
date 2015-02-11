package dataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class Importer {
  private List<List<String>> data;
  private String source;

  public Importer() {
    this.data = new ArrayList<List<String>>();
    this.source = "";
  }

  public Importer(String source) {
    this.data = new ArrayList<List<String>>();
    this.source = source;
  }

  public void addData(List<String> line) {
    this.data.add(line);
  }

  public String getSource() {
    return this.source;
  }

  public List<List<String>> getData() {
    return this.data;
  }
}
