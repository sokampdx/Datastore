package dataStore.Records;

import java.util.Comparator;
import java.util.List;

/**
 * Created by sokam on 2/10/15.
 */
public class RecordListComparator implements Comparator<List<Record>> {
  private List<Integer> index;

  public RecordListComparator(List<Integer> index) {
    this.index = index;
  }

  public int compare(List<Record> first, List<Record> second) {
    int len = this.index.size() - 1;

    int i = 0;
    int compare = first.get(this.index.get(i)).compareTo(second.get(this.index.get(i)));
    while (compare == 0 && i < len) {
      ++i;
      compare = first.get(this.index.get(i)).compareTo(second.get(this.index.get(i)));
    }

    return compare;
  }
}
