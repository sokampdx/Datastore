package dataStore.QueryTool;

import dataStore.QueryStruct.*;
import dataStore.Records.*;
import java.util.*;

public class DataFactory implements Keywords {
  private List<List<Record>> records;
  private List<String> columns;
  private Map<String, CommandArgumentList> commandList;

  public DataFactory(List<List<Record>> records,
                     List<String> columns,
                     Map<String, CommandArgumentList> commandList) {
    this.records = records;
    this.columns = columns;
    this.commandList = commandList;
  }

  public List<List<Record>> getResult () {
    query();
    return this.records;
  }

  public void query () {

    if (this.commandList.containsKey(FILTER)) {
      this.records = filter();
    }

    if (this.commandList.containsKey(ORDER)) {
      this.records = order();
    }

    if (this.commandList.containsKey(GROUP)) {
      this.records = group();
    } else if (this.commandList.containsKey(SELECT)) {
      this.records = select();
    }
  }

  public List<List<Record>> group() {
    List<Criteria> criteria = this.commandList.get(GROUP).getArguments();
    List<List<Record>> result = new ArrayList<List<Record>>();
    List<Integer> index = new ArrayList<Integer>();
    Map<String, List<List<Record>>> grouped = new HashMap<String, List<List<Record>>>();

    createCriteriaIndex(criteria, index);

    for (List<Record> record : this.records) {
      String key = "";
      for (Integer i : index) {
        key += record.get(i).toString();
      }

      List<List<Record>> current = new ArrayList<List<Record>>();
      if (grouped.containsKey(key)) {
        current = grouped.get(key);
      }
      current.add(record);
      grouped.put(key, current);
    }

    for (Map.Entry<String, List<List<Record>>> pair : grouped.entrySet()) {
      List<List<Record>> current = select(pair.getValue());
      result.addAll(current);
    }

    return result;
  }

  public List<List<Record>> select() {
    return select(this.records);
  }

  public List<List<Record>> select(List<List<Record>> records) {
    List<Criteria> criteria = this.commandList.get(SELECT).getArguments();
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

  public void createSimpleSelectResult(List<List<Record>> records,
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

  public boolean setupCriteria(List<Criteria> criteria,
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

  public void createAggregateResult(List<List<Record>> records,
                                    List<Integer> index,
                                    List<String> aggregate,
                                    List<Record> aggregateValue) {

    setInitialAggregateValue(records, index, aggregate, aggregateValue);
    findAggregateValueResult(records, index, aggregate, aggregateValue);
  }

  public void findAggregateValueResult(List<List<Record>> records,
                                       List<Integer> index,
                                       List<String> aggregate,
                                       List<Record> aggregateValue) {
    int numOfCol = index.size();
    Record record;
    Record aggValue;

    for (int i = 0; i < numOfCol; ++i) {
      String aggType = aggregate.get(i);
      List<Record> columnRecords = new ArrayList<Record>();

      for (List<Record> currentRow : records) {
        aggValue = aggregateValue.get(i);
        record = currentRow.get(index.get(i));

        if (isMIN(record, aggType, aggValue)) {
          aggregateValue.set(i, record);
        } else if (isMAX(record, aggType, aggValue)) {
          aggregateValue.set(i, record);
        } else if (isCOUNT(aggType) || isCOLLECT(aggType)) {
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

  public void setInitialAggregateValue(List<List<Record>> records,
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

  public boolean isMIN(Record record, String aggType, Record aggValue) {
    return (aggType.equals(MIN) && record.compareTo(aggValue) < 0);
  }

  public boolean isMAX(Record record, String aggType, Record aggValue) {
    return (aggType.equals(MAX) && record.compareTo(aggValue) > 0);
  }

  public boolean isCOUNT(String aggType) {
    return aggType.equals(COUNT);
  }

  public boolean isCOLLECT(String aggType) {
    return aggType.equals(COLLECT);
  }

  public boolean isSUM(Record record, String aggType) {
    return (aggType.equals(SUM) && record.isSummable());
  }

  public List<List<Record>> order() {
    List<Criteria> criteria = this.commandList.get(ORDER).getArguments();
    List<List<Record>> result = new ArrayList<List<Record>>(this.records);
    List<Integer> index = new ArrayList<Integer>();

    createCriteriaIndex(criteria, index);

    RecordListComparator comparator = new RecordListComparator(index);
    Collections.sort(result, comparator);

    return result;
  }

  private void createCriteriaIndex(List<Criteria> criteria, List<Integer> index) {
    for (Criteria c : criteria) {
      if (this.columns.contains(c.getColumn())) {
        index.add(this.columns.indexOf(c.getColumn()));
      }
    }
  }

 /* public List<List<Record>> filter() {
    List<Criteria> criteria = this.commandList.get(FILTER).getArguments();
    AdvancedFilter advancedFilter = new AdvancedFilter(this.records, this.columns, criteria);
    return advancedFilter.filter();
  }*/

  public List<List<Record>> filter() {
    List<Criteria> criteria = this.commandList.get(FILTER).getArguments();
    List<List<Record>> result = new ArrayList<List<Record>>();

    int criteriaIndex = 0;
    Criteria c = criteria.get(criteriaIndex);

    // TODO: use the tree structure recursively combine filter results
    while (c.isBinOp()) {
      ++criteriaIndex;
      c = criteria.get(criteriaIndex);
    }

    int index = this.columns.indexOf(c.getColumn());
    String match = c.getMatch();

    for (List<Record> record : this.records) {
      if (record.get(index).getData().equals(match)) {
        result.add(new ArrayList<Record>(record));
      }
    }

    return result;
  }


}