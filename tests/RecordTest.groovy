import com.example.sokam.Record
import com.example.sokam.TextRecord

/**
 * Created by sokam on 2/5/15.
 */
class RecordTest extends GroovyTestCase {

    public void testNewRecord() {
        String str = fill("X", TextRecord.MAX_CHAR*2);
        Record record = new Record(str);
        assertEquals(str, record.getData());
    }

    private String fill(String c, int len) {
        String str = "";
        for (int i = 0; i < len; ++i)
            str += c;
        return str;
    }

    public void testNewRecordTest() {
        String str = fill("X", TextRecord.MAX_CHAR*2);
        TextRecord record = new TextRecord(str);
        assertEquals(str.substring(0, TextRecord.MAX_CHAR), record.getData());
    }


}
