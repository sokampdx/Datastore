import com.example.sokam.Record;
import com.example.sokam.TextRecord;
import com.example.sokam.DateRecord
import org.junit.Test

import java.text.ParseException;

/**
 * Created by sokam on 2/5/15.
 */
class RecordTest extends GroovyTestCase {

    @Test
    public void testNewRecord() {
        String str = fill("X", TextRecord.MAX_CHAR*2);
        Record record = new Record(str);
        assertEquals(str, record.getData());
    }

    @Test
    private String fill(String c, int len) {
        String str = "";
        for (int i = 0; i < len; ++i)
            str += c;
        return str;
    }

    @Test
    public void testNewExtraLengthTextRecord() {
        String str = fill("X", TextRecord.MAX_CHAR*2);
        TextRecord record = new TextRecord(str);
        assertEquals(str.substring(0, TextRecord.MAX_CHAR), record.getData());
    }

    @Test
    public void testNewCorrectDateRecord() {
        String str = "2014-11-12";
        DateRecord record = new DateRecord(str);
        assertEquals(str, record.getDate());
    }

    @Test
    public void testNewIncorrectDateRecord() {
        String str = "2014-15-33";
        shouldFail(RuntimeException) {
            DateRecord record = new DateRecord(str);
        }

    }
}
