import com.example.sokam.DollarRecord
import com.example.sokam.Record;
import com.example.sokam.TextRecord;
import com.example.sokam.DateRecord
import com.example.sokam.TimeRecord
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

    @Test
    public void testNewCorrectDollarRecord() {
        String str = "2.59";
        DollarRecord record = new DollarRecord(str);
        assertEquals(str, record.getData());
    }

    @Test
    public void testNewCorrectDollarRecordAsValue() {
        String str = "2.59";
        DollarRecord record = new DollarRecord(str);
        assertEquals(Double.parseDouble(str), record.getValue());
    }

    @Test
    public void testNewInCorrectDollarRecord() {
        String str = "abc";
        shouldFail(RuntimeException) {
            DollarRecord record = new DollarRecord(str);
        }
    }

    @Test
    public void testNewCorrectTimeRecord() {
        String str = "13:59";
        TimeRecord record = new TimeRecord(str);
        assertEquals(str, record.getData());
    }

    @Test
    public void testNewInCorrectTimeRecord() {
        String str = "24:77";
        shouldFail(RuntimeException) {
            TimeRecord record = new TimeRecord(str);
        }
    }


}
