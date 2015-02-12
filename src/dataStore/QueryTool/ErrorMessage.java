package dataStore.QueryTool;

/**
 * Created by sokam on 2/11/15.
 */
public interface ErrorMessage {
  public static final String USAGE = "USAGE: ./query " +
      "-s column[:aggregate],... -o column,... -g column,... -f column=data (AND|OR) ... ";

  public static final String EXPECTED_QUOTE = "Expect a matching quote.";

  public static final String COLUMN_ERR = "Column Name is not in the Database.";
  public static final String UNKNOWN_COMMAND_ERR = "Unknown command.";
  public static final String INCORRECT_FILTER_ERR = "Must specified Filter criteria for the column";
  public static final String UNKNOWN_AGGREGATE_ERR = "Unrecognized aggregate function.";
  public static final String EXPECT_CLOSE_PARENT = "Expecting a close parenthesis.";
  public static final String GROUP_AND_AGGREGATE_ERR = "Group must be accommadate with aggregate";
}
