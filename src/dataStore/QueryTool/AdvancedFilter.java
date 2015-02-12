package dataStore.QueryTool;

import dataStore.QueryStruct.Criteria;
import dataStore.Records.Record;
import dataStore.Util.MyUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by sokam on 2/12/15.
 */
public class AdvancedFilter implements Keywords{
  private List<Criteria> criteriaList;
  private List<List<Record>> records;
  private List<String> columns;

  private int nextIndex;

  public AdvancedFilter (List<List<Record>> records,
                         List<String> columns,
                         List<Criteria> criteriaList) {
    this.records = new ArrayList<List<Record>>(records);
    this.criteriaList = criteriaList;
    this.columns = columns;
    this.nextIndex = 0;
  }

  public List<List<Record>> filter () {
    if (noMoreCriteria()) {
      return null;
    }



    Criteria c = getCriteria();
    if (c.isBinOp()) {
      if (c.getBinOp().equals(AND)) {
        return andBinOp(filter(), filter());
      } else if (c.getBinOp().equals(OR)) {
        return orBinOp(filter(), filter());
      } else {
        return null;
      }
    } else {
      return filterOne();
    }
  }

  private boolean noMoreCriteria() {
    boolean isDone = this.nextIndex >= this.criteriaList.size();
    System.out.println(nextIndex + " " + criteriaList.size());
    return isDone;
  }

  private Criteria getCriteria() {
    Criteria c = this.criteriaList.get(this.nextIndex);
    ++this.nextIndex;
    System.out.println(nextIndex);
    return c;
  }

  private List<List<Record>> filterOne() {
    if (noMoreCriteria()) {
      return null;
    }

    List<List<Record>> result = new ArrayList<List<Record>>();
    Criteria c = getCriteria();
    String match = c.getMatch();
    int index = this.columns.indexOf(c.getColumn());

    for (List<Record> record : this.records) {
      if (record.get(index).getData().equals(match)) {
        result.add(new ArrayList<Record>(record));
      }
    }

    return result;
  }

  private List<List<Record>> andBinOp (List<List<Record>> first,
                                       List<List<Record>> second) {
    List<List<Record>> result = new ArrayList<List<Record>>();

    System.out.println(AND + MyUtil.DIVIDER);

    if (first.isEmpty() || second.isEmpty())
      return result;

    for (List<Record> f : first) {
      if (second.contains(f))
        result.add(f);
    }
    return result;
  }

  private List<List<Record>> orBinOp (List<List<Record>> first,
                                      List<List<Record>> second) {
    List<List<Record>> result = new ArrayList<List<Record>>();

    System.out.println(OR + MyUtil.DIVIDER);

    if (first.isEmpty() && second.isEmpty())
      return result;

    if (first.isEmpty())
      return second;

    if (second.isEmpty())
      return first;

    result.addAll(first);

    for (List<Record> f : first) {
      if (second.contains(f))
        result.remove(f);
    }

    result.addAll(second);

    return result;
  }
}
