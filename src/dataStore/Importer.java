package dataStore;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class Importer {
  private ArrayList<List<String>> data;
  private String source;

  public Importer() {
    this.data = new ArrayList<List<String>>();
    this.source = "";
  }

  public Importer(String source) {
    this.data = new ArrayList<List<String>>();
    this.source = source;
  }

  public void addData(ArrayList<String> line) {
    this.data.add(line);
  }

  public String getSource() {
    return this.source;
  }

  public ArrayList<List<String>> getData() {
    return this.data;
  };
}
