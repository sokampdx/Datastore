import dataStore.DataStore
import dataStore.Record
import dataStore.TextFileDataStore

/**
 * Created by sokam on 2/8/15.
 */
class DataStoreTest extends GroovyTestCase {
    private final String[] columns = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split("\\|");
    private final String sample = "sample";
    private final String test = "test";
    private final String original = "original";
    private final String[] keys = "STB|TITLE|DATE".split("\\|");
    private final String[] types = "TEXT|TEXT|TEXT|DATE|MONEY|TIME".split("\\|");
    private final String[] data1 = "stb1|the matrix|warner bros|2014-04-01|4.00|1:30".split("\\|");
    private final String[] data2 = "stb1|unbreakable|buena vista|2014-04-03|6.00|2:05".split("\\|");
    private final String[] data3 = "stb1|the matrix|buena vista|2014-04-01|6.00|2:05".split("\\|");

    public void testNewDataStore() {
        DataStore dataStore = new TextFileDataStore();
    }

    public void testNewDataStoreWithAllArg() {
        List<List<Record>> records = new ArrayList<List<Record>>();

        records.add(TextFileDataStore.createRecord(data1, Arrays.asList(types)));
        records.add(TextFileDataStore.createRecord(data2, Arrays.asList(types)));
        records.add(TextFileDataStore.createRecord(data3, Arrays.asList(types)));

        DataStore dataStore = new TextFileDataStore(test,
                Arrays.asList(keys),
                Arrays.asList(columns),
                Arrays.asList(types),
                records);

        assertEquals(test, dataStore.getName());
        assertEquals(keys.toString(), dataStore.getListOfKey());
        assertEquals(columns.toString(), dataStore.getListOfColumn());
        assertEquals(types.toString(), dataStore.getListOfType());
        dataStore.close();
    }

    public void testNewDataStoreWithFileArg() {
        DataStore dataStore = new TextFileDataStore(test);
        assertEquals(test, dataStore.getName());
        assertEquals(keys.toString(), dataStore.getListOfKey());
        assertEquals(columns.toString(), dataStore.getListOfColumn());
        assertEquals(types.toString(), dataStore.getListOfType());
    }

}
