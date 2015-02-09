package dataStore;

import java.util.*;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class DataStore {



  protected String name;
  protected List<String> keys;
  protected List<String> columns;
  protected List<String> types;
  protected Map<String, List<Record>> records;

  public DataStore() {
    this.name = "";
    this.keys = new ArrayList<String>();
    this.columns = new ArrayList<String>();
    this.types = new ArrayList<String>();
    this.records = new HashMap<String, List<Record>>();
  }

  public void create(String name,
                     List<String> keys,
                     List<String> columns,
                     List<String> types,
                     List<List<Record>> records) {
    this.create(name, keys, columns, types);

    for (List<Record> record : records) {
      String hashKey = createHashKey(record);
      this.records.put(hashKey, record);
    }
  }

  public String createHashKey(List<Record> record) {
    String hashKey = "";
    int [] index = new int [this.keys.size()];

    for (int i = 0; i < this.keys.size(); ++i) {
      index[i] = this.columns.indexOf(this.keys.get(i));
      hashKey += record.get(index[i]);
    }
    return hashKey;
  }


  protected void create(String name, List<String> keys, List<String> columns, List<String> types) {
    this.name = name;
    this.keys = new ArrayList<String>(keys);
    this.columns = new ArrayList<String>(columns);
    this.types = new ArrayList<String>(types);
  }


  public String getName() {
    return this.name;
  }

  public String getListOfKey() {
    return this.keys.toString();
  }

  public String getListOfColumn() {
    return this.columns.toString();
  }

  public String getListOfType() {
    return this.types.toString();
  }

  private String ArrayListToString (List<String> strings) {
    String finalString = "";
    for (String string : strings) {
      finalString += string + " ";
    }
    return finalString;
  }
  public abstract void insert(String[] data);

  public abstract void open(String name);

  public abstract void close();

}
