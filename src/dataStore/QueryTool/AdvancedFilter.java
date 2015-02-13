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
    if(!moreCriteria()) {
      return new ArrayList<List<Record>>();
    }

    return filterOne();
  }

  private boolean moreCriteria() {
    return !this.criteriaList.isEmpty();
  }

  private void getCriteria() {
    this.criteria = this.criteriaList.remove(0);
  }

  public List<List<Record>> filterOne() {
    List<List<Record>> result = new ArrayList<List<Record>>();
    if (!moreCriteria()) {
      return result;
    }

    getCriteria();
    if (this.criteria.isBinOp()) {
      if (this.criteria.getBinOp().equals(AND)) {
        result.addAll(andBinOp(filterOne(), filterOne()));
      } else if (this.criteria.getBinOp().equals(OR)) {
        result.addAll(orBinOp(filterOne(), filterOne()));
      }
    } else {
      String match = this.criteria.getMatch();
      int index = this.columns.indexOf(this.criteria.getColumn());

      for (List<Record> record : this.records) {
        if (record.get(index).getData().equals(match)) {
          result.add(new ArrayList<Record>(record));
        }
      }
    }

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

    if (first.isEmpty() || second.isEmpty())
      return result;

    for (List<Record> fst : first) {
      boolean isContain = false;
      for (List<Record> snd : second) {
        if(fst.equals(snd)) {
          isContain |= true;
        }
      }
      if (isContain) {
        result.add(fst);
      }
    }

    return result;
  }

}
