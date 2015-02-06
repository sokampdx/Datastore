import com.example.sokam.DataStore;

/**
 * Created by sokam on 2/5/15.
 */
class DataStoreTest extends GroovyTestCase {
    private final String[] header = "STB|TITLE|PROVIDER|DATE|REV|VIEW_TIME".split("\\|");
    private final String name = "sample";
    private final String[] keys = "STB|TITLE|DATE".split("\\|");
    private final String[] data1 = "stb1|the matrix|warner bros|2014-04-01|4.00|1:30".split("\\|");
    private final String[] data2 = "stb1|unbreakable|buena vista|2014-04-03|6.00|2:05".split("\\|");

    public void testNewDataStore() {
        DataStore dataStore = new DataStore();
    }

    public void testCreateTable() {
        DataStore dataStore = new DataStore();
        dataStore.create(name, header, keys);
    }

    public void testCreateTableVerifyName() {
        DataStore dataStore = new DataStore();
        dataStore.create(name, header, keys);
        assertEquals(name, dataStore.getName());
    }

    public void testNewDataStoreWithArgConstructor() {
        DataStore dataStore = new DataStore(name, header, keys);
        assertEquals(name, dataStore.getName());
    }


    public void testVerifyHeader() {
        DataStore dataStore = new DataStore(name, header, keys);
        assertEquals(header.toString(), dataStore.getHeader().toString());
    }

    public void testAddData() {
        DataStore dataStore = new DataStore(name, header, keys);
        
    }
}
