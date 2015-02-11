import dataStore.DataStore
import dataStore.MyUtil
import dataStore.QueryMain
import dataStore.Record
import dataStore.RecordListComparator
import dataStore.TextFileDataStore

/**
 * Created by sokam on 2/10/15.
 */
class RecordListComparatorTest extends GroovyTestCase {
    DataStore dataStore = new TextFileDataStore(QueryMain.FILENAME);
    List<List<Record>> current = dataStore.getRecords();

    public void testCompareTwoRowAtIndex1And3IsGreater() {
        List<Integer> index = new ArrayList<Integer>();
        index.add(1);
        index.add(3);

        RecordListComparator comparator = new RecordListComparator(index);
        List<Record> first = current.get(0);
        List<Record> second = current.get(1);
//        MyUtil.print(first.toString());
//        MyUtil.print(second.toString());
        assertTrue(comparator.compare(first, second) > 0);
    }

    public void testCompareTwoRowAtIndex3And4IsLesser() {
        List<Integer> index = new ArrayList<Integer>();
        index.add(3);
        index.add(4);
        RecordListComparator comparator = new RecordListComparator(index);
        List<Record> first = current.get(0);
        List<Record> second = current.get(1);
        assertTrue(comparator.compare(first, second) < 0);
    }

    public void testCompareTwoRowAtIndex2And3IsEqual() {
        List<Integer> index = new ArrayList<Integer>();
        index.add(2);
        index.add(3);
        RecordListComparator comparator = new RecordListComparator(index);
        List<Record> first = current.get(0);
        List<Record> second = current.get(1);
        assertTrue(comparator.compare(first, second) == 0);
    }

    public void testCompareTwoRowAtIndex2And3And5IsLesser() {
        List<Integer> index = new ArrayList<Integer>();
        index.add(2);
        index.add(3);
        index.add(5);
        RecordListComparator comparator = new RecordListComparator(index);
        List<Record> first = current.get(0);
        List<Record> second = current.get(1);
        assertTrue(comparator.compare(first, second) < 0);
    }

    public void testCompareTwoRowAtIndex2And3And0IsGreater() {
        List<Integer> index = new ArrayList<Integer>();
        index.add(2);
        index.add(3);
        index.add(0);
        RecordListComparator comparator = new RecordListComparator(index);
        List<Record> first = current.get(0);
        List<Record> second = current.get(1);
        assertTrue(comparator.compare(first, second) > 0);
    }
}
