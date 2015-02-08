import dataStore.DateRecord
import dataStore.DollarAndCentRecord
import dataStore.HourAndMinuteRecord
import dataStore.Record
import dataStore.TextRecord

/**
 * Created by sokam on 2/7/15.
 */
class RecordTest extends GroovyTestCase {
    public void testCreateATextRecordWithOutAnyChar() {
        Record record = new TextRecord("", 0);
        assertEquals("", record.getData());
    }

    public void testCreateATextRecordDataGreaterThanMaxCharAllow() {
        Record record = new TextRecord("helloworld", 5);
        assertEquals("hello", record.getData());
    }

    public void testCreateATextRecordDataSmallerThanMaxCharAllow() {
        Record record = new TextRecord("hellow", 6);
        assertEquals("hellow", record.getData());
    }

    public void testCompareTwoDifferentTextRecordData() {
        String text = "helloworld";
        Record record1 = new TextRecord(text, 5);
        Record record2 = new TextRecord(text, 6);
        assertTrue(record1.compareTo(record2) < 0);
    }

    public void testCompareTwoSameTextRecordData() {
        Record record1 = new TextRecord("helloworld", 5);
        Record record2 = new TextRecord("hello", 6);
        assertTrue(record1.compareTo(record2) == 0);
    }

    public void testValidTextRecordWithCorrectLength() {
        Record record = new TextRecord("helloworld", 5);
        assertTrue(record.isValid());
    }

    public void testCreateDollarAndCentRecordCorrectly() {
        String value = "1.00";
        Record record = new DollarAndCentRecord(value);
        assertEquals(value, record.getData());
    }

    public void testCreateDollarAndCentRecordIncorrectly() {
        def msg = shouldFail(IllegalArgumentException) {
            Record record = new DollarAndCentRecord("abc");
        }
        assertEquals(DollarAndCentRecord.DATA_NOT_IN_CORRECT_FORMAT, msg);
    }

    public void testCompareTwoEqualAndCorrectDollarAndCent() {
        String value = "6.15";
        Record record1 = new DollarAndCentRecord(value);
        Record record2 = new DollarAndCentRecord(value);
        assertTrue(record1.compareTo(record2) == 0);
    }

    public void testCompareTwoDifferentAndCorrectDollarAndCent() {
        Record record1 = new DollarAndCentRecord("123.78");
        Record record2 = new DollarAndCentRecord("123.77");
        assertTrue(record1.compareTo(record2) > 0);
    }

    public void testCompareDollarAndCentRecordToTextRecordShouldFail() {
        Record record1 = new DollarAndCentRecord("123.78");
        Record record2 = new TextRecord("one hundred dollar and two cents");
        def msg = shouldFail(IllegalArgumentException) {
            record1.compareTo(record2);
        }
        assertEquals(DollarAndCentRecord.COMPARED_TO_DIFFERENT_OBJECT, msg);
    }

    public void testCompareTextRecordToDollarAndCentRecordIsOK() {
        Record record1 = new DollarAndCentRecord("123.78");
        Record record2 = new TextRecord("one hundred dollar and two cents");
        record2.compareTo(record1);
    }

    public void testCorrectDollarAndCentRecordIsValid() {
        Record record = new DollarAndCentRecord("123.78");
        assertTrue(record.isValid());
    }

    public void textInCorrectDollarAndCentRecordIsInValid() {
        Record record1 = new TextRecord("one hundred dollar and two cents");
        Record record2 = (DollarAndCentRecord) record1;
        assertFalse(record2.isValid());
    }

    public void testCreateDateRecordCorrectly() {
        String date = "2014-02-24";
        Record record = new DateRecord(date);
        assertEquals(date, record.getData());
    }

    public void testCreateDateRecordIncorrectly() {
        def msg = shouldFail(IllegalArgumentException) {
            Record record = new DateRecord("1900-02-29");
        }
        assertEquals(DateRecord.DATA_NOT_IN_CORRECT_FORMAT, msg);
    }

    public void testCompareSameDateRecordData() {
        String date = "2011-03-31";
        Record record1 = new DateRecord(date);
        Record record2 = new DateRecord(date);
        assertTrue(record1.compareTo(record2) == 0);
    }

    public void testCompareDifferentDateRecord() {
        Record record1 = new DateRecord("2011-03-30");
        Record record2 = new DateRecord("2011-03-29");
        assertTrue(record1.compareTo(record2) > 0);
    }

    public void testCompareDateRecordToTextRecordShouldFail() {
        Record record1 = new DateRecord("2011-12-31");
        Record record2 = new TextRecord("Dec 31, 2011");
        def msg = shouldFail(IllegalArgumentException) {
           record1.compareTo(record2);
        }
        assertEquals(DateRecord.COMPARED_TO_DIFFERENT_OBJECT, msg);
    }

    public void testCorrectDateRecordIsValid() {
        Record record = new DateRecord("2012-02-29");
        assertTrue(record.isValid());
    }

    public void testCorrectHourAndMinuteRecord() {
        String time = "23:30";
        Record record = new HourAndMinuteRecord(time);
        assertEquals(time, record.getData());
    }

    public void testIncorrectMinuteDataInHourAndMinuteRecord() {
        def msg = shouldFail(IllegalArgumentException) {
            Record record = new HourAndMinuteRecord("00:60");
        }
        assertEquals(HourAndMinuteRecord.DATA_NOT_IN_CORRECT_FORMAT, msg);
    }

    public void testIncorrectHourDataInHourAndMinuteRecord() {
        def msg = shouldFail(IllegalArgumentException) {
            Record record = new HourAndMinuteRecord("24:30");
        }
        assertEquals(HourAndMinuteRecord.DATA_NOT_IN_CORRECT_FORMAT, msg);
    }

    public void testCompareSameHourAndMinuteRecord() {
        String time = "07:01";
        Record record1 = new HourAndMinuteRecord(time);
        Record record2 = new HourAndMinuteRecord(time);
        assertTrue(record1.compareTo(record2) == 0);
    }

    public void testCompareLogicalSameHourAndMinuteRecord() {
        String time = "6:15";
        Record record1 = new HourAndMinuteRecord(time);
        Record record2 = new HourAndMinuteRecord("0"+time);
        assertTrue(record1.compareTo(record2) == 0);
    }

    public void testCompareDifferentHourDataInHourAndMinuteRecord() {
        Record record1 = new HourAndMinuteRecord("23:50");
        Record record2 = new HourAndMinuteRecord("22:51");
        assertTrue(record1.compareTo(record2) > 0);
    }

    public void testCompareDifferentMinuteDataInHourAndMinuteRecord() {
        Record record1 = new HourAndMinuteRecord("15:50");
        Record record2 = new HourAndMinuteRecord("15:51");
        assertTrue(record1.compareTo(record2) < 0);
    }
}
