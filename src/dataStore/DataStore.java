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

  public String getTypeOf(String column) {
    if (this.columns.contains(column)) {
      return this.types.get(this.columns.indexOf(column));
    } else {
      return null;
    }
  }

  public String getName() {
    return this.name;
  }

  public String getKeyToString() {
    return this.keys.toString();
  }

  public String getColumnToString() {
    return this.columns.toString();
  }

  public String getTypeToString() {
    return this.types.toString();
  }

  public List<String> getColumns() {
    return this.columns;
  }

  public List<String> getKeys() {
    return this.keys;
  }

  public List<String> getTypes() {
    return this.types;
  }

    public List<List<Record>> getRecords() {
    List<List<Record>> records = new ArrayList<List<Record>>();
    for (Map.Entry<String, List<Record>> pair : this.records.entrySet()) {
      records.add(pair.getValue());
    }
    return records;
  };

  public List<List<String>> getRecordsToString() {
    List<List<String>> records = new ArrayList<List<String>>();
    for (Map.Entry<String, List<Record>> pair : this.records.entrySet()) {;
      List<Record> row = pair.getValue();
      int len = row.size();
      List<String> stringList =  new ArrayList<String>();
      for (int i = 0; i < len; ++i) {
        stringList.add(row.get(i).toString());
      }

      records.add(stringList);
    }
    return records;
  }

  public List<List<Record>> select(List<Criteria> criteria, List<List<Record>> records) {
    List<List<Record>> result = new ArrayList<List<Record>>();
    List<Integer> index = new ArrayList<Integer>();
//    List<String> aggregate = new ArrayList<String>();

    for (Criteria c : criteria) { // simple select only ... no aggregate function
      if (this.columns.contains(c.getColumn())) {
        index.add(this.columns.indexOf(c.getColumn()));
      }
    }

    for (List<Record> record : records) {
      List<Record> current = new ArrayList<Record>();
      for (Integer i : index) {
        current.add(record.get(i));
      }
      result.add(current);
    }

    return result;
  }

  public List<List<Record>> filter(List<Criteria> criteria, List<List<Record>> records) {
    List<List<Record>> result = new ArrayList<List<Record>>();
    int index = 0;
    String match = "";

    int criteriaIndex = 0;
    Criteria c = criteria.get(criteriaIndex);
    while (c.isBinOp()) { // simple filter only... take first non-BinOp criteria
      ++criteriaIndex;
      c = criteria.get(criteriaIndex);
    }

    index = this.columns.indexOf(c.getColumn());
    match = ((FilterCriteria) c).getMatch();

    for (List<Record> record : records) {
      if (record.get(index).getData().equals(match)) {
        result.add(new ArrayList<Record>(record));
      }
    }

    return result;
  }

  public List<List<Record>> order(List<Criteria> criteria, List<List<Record>> records) {
    List<List<Record>> result = new ArrayList<List<Record>>(records);
    List<Integer> index = new ArrayList<Integer>();

    for (Criteria c : criteria) {
      if (this.columns.contains(c.getColumn())) {
        index.add(this.columns.indexOf(c.getColumn()));
      }
    }

    RecordListComparator comparator = new RecordListComparator(index);
    Collections.sort(records, comparator);

    return result;
  }

  public abstract void insert(String[] data);

  public abstract void open(String name);

  public abstract void close();

}
