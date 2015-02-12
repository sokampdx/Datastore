package dataStore;

import com.sun.xml.internal.bind.v2.TODO;

import java.util.*;

/**
 * Created by sokam on 2/7/15.
 */
public abstract class DataStore {
  public static final String MAX = "max";
  public static final String MIN = "min";
  public static final String SUM = "sum";
  public static final String COUNT = "count";
  public static final String COLLECT = "collect";

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


  protected void create(String name,
                        List<String> keys,
                        List<String> columns,
                        List<String> types) {
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
  }

  public List<List<String>> getRecordsToString() {
    List<List<String>> records = new ArrayList<List<String>>();
    for (Map.Entry<String, List<Record>> pair : this.records.entrySet()) {
      List<Record> row = pair.getValue();
      List<String> stringList =  new ArrayList<String>();
      for (Record aRow : row) {
        stringList.add(aRow.toString());
      }

      records.add(stringList);
    }
    return records;
  }

  public List<List<Record>> select(List<Criteria> criteria, List<List<Record>> records) {
    List<List<Record>> result = new ArrayList<List<Record>>();
    List<Integer> index = new ArrayList<Integer>();
    List<String> aggregate = new ArrayList<String>();
    List<Record> aggregateValue = new ArrayList<Record>();

    boolean hasAggregate = setupCriteria(criteria, index, aggregate);

    if (hasAggregate) {
      createAggregateResult(records, index, aggregate, aggregateValue);
      result.add(aggregateValue);
    } else {
      createSimpleSelectResult(records, result, index);
    }
    return result;
  }

  private void createSimpleSelectResult(List<List<Record>> records,
                                        List<List<Record>> result,
                                        List<Integer> index) {
    for (List<Record> record : records) {
      List<Record> current = new ArrayList<Record>();
      for (Integer i : index) {
        current.add(record.get(i));
      }
      result.add(current);
    }
  }

  private void createAggregateResult(List<List<Record>> records,
                                     List<Integer> index,
                                     List<String> aggregate,
                                     List<Record> aggregateValue) {

    setInitialAggregateValue(records, index, aggregate, aggregateValue);
    findAggregateValueResult(records, index, aggregate, aggregateValue);
  }

  private void findAggregateValueResult(List<List<Record>> records,
                                        List<Integer> index,
                                        List<String> aggregate,
                                        List<Record> aggregateValue) {
    int numOfRow = records.size();
    int numOfCol = index.size();
    Record record;
    Record aggValue;
    List<Record> currentRow;

    for (int i = 0; i < numOfCol; ++i) {
      String aggType = aggregate.get(i);
      List<Record> columnRecords = new ArrayList<Record>();

      for (int j = 0; j < numOfRow; ++j) {
        currentRow = records.get(j);

        aggValue = aggregateValue.get(i);
        record = currentRow.get(index.get(i));

        if (isMIN(record, aggType, aggValue)) {
          aggregateValue.set(i, record);
        } else if (isMAX(record, aggType, aggValue)) {
          aggregateValue.set(i, record);
        } else if (isCOUNT(aggType) || isCOLLECT(aggType))  {
          columnRecords.add(record);
        } else if (isSUM(record, aggType)) {
          aggValue.add(record);
        }
      }
      Set<Record> set = new TreeSet<Record>(columnRecords);
      if (isCOUNT(aggType)) {
        aggregateValue.set(i, new IntegerRecord(set.size()));
      } else if (isCOLLECT(aggType)) {
        List<Record> unique = new ArrayList<Record>(set);
        aggregateValue.get(i).setData(unique.toString());
      }
    }
  }

  private void setInitialAggregateValue(List<List<Record>> records,
                                        List<Integer> index,
                                        List<String> aggregate,
                                        List<Record> aggregateValue) {
    int numOfCol = index.size();
    Record record = null;
    int currentIndex = 0;
    List<Record> currentRow = records.get(currentIndex);
    for (int i = 0; i < numOfCol; ++i) {
      String aggType = aggregate.get(i);
      if (aggType.equals(MIN) || aggType.equals(MAX) || aggType.equals("")) {
        record = currentRow.get(index.get(i));
      } else if (aggType.equals(SUM)) {
        record = new MoneyRecord("0.00");
      } else if (isCOUNT(aggType)) {
        record = new IntegerRecord(0);
      } else if (isCOLLECT(aggType)) {
        record = new TextRecord("");
      }

      aggregateValue.add(record);
    }
  }

  private boolean setupCriteria(List<Criteria> criteria,
                                List<Integer> index,
                                List<String> aggregate) {
    boolean hasAggregate = false;
    for (Criteria c : criteria) {
      if (this.columns.contains(c.getColumn())) {
        index.add(this.columns.indexOf(c.getColumn()));

        String cAggregate = ((SelectCriteria) c).getAggregate();
        hasAggregate |= cAggregate.length() > 0;
        aggregate.add(cAggregate);
      }
    }
    return hasAggregate;
  }

  private boolean isMIN(Record record, String aggType, Record aggValue) {
    return (aggType.equals(MIN) && record.compareTo(aggValue) < 0);
  }

  private boolean isMAX(Record record, String aggType, Record aggValue) {
    return (aggType.equals(MAX) && record.compareTo(aggValue) > 0);
  }

  private boolean isCOUNT(String aggType) {
    return aggType.equals(COUNT);
  }

  private boolean isCOLLECT(String aggType) {
    return aggType.equals(COLLECT);
  }

  private boolean isSUM(Record record, String aggType) {
    return (aggType.equals(SUM) && record.isSummable());
  }

  public List<List<Record>> filter(List<Criteria> criteria, List<List<Record>> records) {
    List<List<Record>> result = new ArrayList<List<Record>>();

    int criteriaIndex = 0;
    Criteria c = criteria.get(criteriaIndex);

    // TODO: use the tree structure to recursively to combine filter results

    while (c.isBinOp()) {
      ++criteriaIndex;
      c = criteria.get(criteriaIndex);
    }

    int index = this.columns.indexOf(c.getColumn());
    String match = ((FilterCriteria) c).getMatch();

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
    Collections.sort(result, comparator);

    return result;
  }

  public abstract void insert(String[] data);

  public abstract void open(String name);

  public abstract void close();

}
