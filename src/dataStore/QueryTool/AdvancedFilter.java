package dataStore.QueryTool;

import dataStore.QueryStruct.Criteria;
import dataStore.Records.Record;
import dataStore.Util.MyUtil;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by sokam on 2/12/15.
 */
public class AdvancedFilter implements Keywords{
  private List<Criteria> criteriaList;
  private List<List<Record>> records;
  private List<String> columns;
  private Criteria criteria;

  public AdvancedFilter (List<List<Record>> records,
                         List<String> columns,
                         List<Criteria> criteriaList) {
    this.records = records;
    this.criteriaList = criteriaList;
    this.columns = columns;
    this.criteria = criteriaList.get(0);
  }

  public List<List<Record>> filter () {
    List<List<Record>> result = new ArrayList<List<Record>>();
    if(!moreCriteria()) {
      return result;
    }

    do {
      getCriteria();
      if (this.criteria.isBinOp()) {
        if (this.criteria.getBinOp().equals(AND)) {
          result.addAll(andBinOp(filter(), filter()));
        } else if (this.criteria.getBinOp().equals(OR)) {
          result.addAll(orBinOp(filter(), filter()));
        }
      } else {
        result.addAll(filterOne());
      }
    } while (moreCriteria());

    MyUtil.printResultAt("filter", result);
    return result;
  }

  private boolean moreCriteria() {
    MyUtil.print("MoreCriteria", this.criteriaList.size()+"");
    return !this.criteriaList.isEmpty();
  }

  private void getCriteria() {
    this.criteria = this.criteriaList.remove(0);
    String text = "";
    if (this.criteria.isBinOp()) {
      text = this.criteria.getBinOp();
    } else {
      text = this.criteria.getColumn();
    }
    MyUtil.print("getCriteria", MyUtil.DIVIDER, text);
  }

  public List<List<Record>> filterOne() {
    List<List<Record>> result = new ArrayList<List<Record>>();

    String match = this.criteria.getMatch();
    int index = this.columns.indexOf(this.criteria.getColumn());

    for (List<Record> record : this.records) {
      if (record.get(index).getData().equals(match)) {
        result.add(new ArrayList<Record>(record));
      }
    }

    MyUtil.printResultAt("filterOne", result);
    return result;
  }

  private List<List<Record>> orBinOp (List<List<Record>> first,
                                      List<List<Record>> second) {
    Set<List<Record>> result = new HashSet<List<Record>>();
    result.addAll(first);
    result.addAll(second);

    return new ArrayList<List<Record>>(result);
  }

  private List<List<Record>> andBinOp (List<List<Record>> first,
                                       List<List<Record>> second) {
    List<List<Record>> result = new ArrayList<List<Record>>();

    for (List<Record> row : first) {
      if(second.contains(row)) {
        result.add(row);
      }
    }
    return result;
  }


/*  private List<List<Record>> andBinOp (List<List<Record>> first,
                                       List<List<Record>> second) {
    List<List<Record>> result = new ArrayList<List<Record>>();
    System.out.println(AND + MyUtil.DIVIDER);

    if (first.isEmpty() || second.isEmpty())
      return result;

    for (List<Record> f : first) {
      if (second.contains(f))
        result.add(f);
    }

    MyUtil.printResultAt("andBinOp", result);
    return MyUtil.cloneRecords(result);
  }

  private List<List<Record>> orBinOp (List<List<Record>> first,
                                      List<List<Record>> second) {
    List<List<Record>> result = new ArrayList<List<Record>>();
    System.out.println(OR + MyUtil.DIVIDER);

    if (first.isEmpty() && second.isEmpty())
      return result;

    if (first.isEmpty()) {
      result = second;
    } else if (second.isEmpty()) {
      result = first;
    } else {
      result.addAll(first);
      for (List<Record> f : first) {
        if (second.contains(f))
          result.remove(f);
      }
      result.addAll(second);
     }

    MyUtil.printResultAt("orBinOp", result);
    return MyUtil.cloneRecords(result);
  }*/
}
