package dataStore;

import java.util.Arrays;
import java.util.List;

/**
 * Created by sokam on 2/8/15.
 */
public class QueryParser implements QueryKeywords, QueryErrorMessage{


  private DataStore dataStore;
  private List<String> tokens;
  private String nextToken;
  private int nextIndex;

  private Expression expression;

  public QueryParser(DataStore dataStore) {
    this.dataStore = dataStore;
    this.expression = new Expression();
    this.tokens = null;
    this.nextToken = "";
    this.nextIndex = 0;
  }

  public String query(String queryString) {
    QueryScanner queryScanner = new QueryScanner(queryString);
    return query(queryScanner.getTokens());
  }

  public String query(List<String> tokens) {
    reset();
    this.tokens = tokens;
    String output = "";

    getToken();
    buildDataStoreQuery();

    if (!this.nextToken.equals(EOL)) {
      throw new IllegalArgumentException(USAGE);
    }
    return output;
  }

  private void reset() {
    this.expression = new Expression();
    this.nextToken = "";
    this.nextIndex = 0;
  }

  public Expression getQuery() {
    return this.expression;
  }

  private void buildDataStoreQuery() {
    while (isValidCommand(this.nextToken)) {
      getCommandArgument();
    }

    // TODO: Throw exception if GROUP is found but there is no Aggregates
  }

  private boolean isValidCommand(String command) {
    return hasListOfArgument(command) || hasSingleArgument(command) || hasLogicalArgument(command);
  }

  private boolean hasListOfArgument(String command) {
    return command.equals(DASH + SELECT) || command.equals(DASH + ORDER);
  }

  private boolean hasSingleArgument(String command) {
    return command.equals(DASH + GROUP);
  }

  private boolean hasLogicalArgument(String command) {
    return command.equals(DASH + FILTER);
  }

  private void getCommandArgument() {

    String token = this.nextToken;
    String command = token.substring(1,2);
    getToken();

    if (hasListOfArgument(token)) {
      this.expression.add(command, getListOfArguments(command));
    } else if (hasSingleArgument(token)) {
      this.expression.add(command, getSingleArgument(command));
    } else if (hasLogicalArgument(token)) {
      this.expression.add(command, getLogicalArguments(command));
    }
  }

  private CommandArgumentList getLogicalArguments(String command) {
    CommandArgumentList arguments = new CommandArgumentList();
    arguments.addAll(getMoreArguments(command));

    while (this.nextToken.equals(OR)) {
      arguments.addBinOp(getCriteria(command));
      arguments.addAll(getLogicalArguments(command));
    }

    return arguments;
  }

  private CommandArgumentList getMoreArguments(String command) {
    CommandArgumentList arguments = new CommandArgumentList();
    arguments.addAll(getParenArguments(command));

    while (this.nextToken.equals(AND)) {
      arguments.addBinOp(getCriteria(command));
      arguments.addAll(getMoreArguments(command));
    }

    return arguments;
  }

  private CommandArgumentList getParenArguments(String command) {
    CommandArgumentList arguments = new CommandArgumentList();

    if (this.nextToken.equals(OPEN)) {
      getToken();
      arguments.addAll(getLogicalArguments(command));
      if (this.nextToken.equals(CLOSE)) {
        getToken();
      } else {
        throw new IllegalArgumentException(EXPECT_CLOSE_PARENT);
      }
    } else {
      arguments.add(getCriteria(command));
    }

    return arguments;
  }


  private CommandArgumentList getSingleArgument(String command) {
    CommandArgumentList arguments = new CommandArgumentList();
    arguments.add(getCriteria(command));
    return arguments;
  }


  private CommandArgumentList getListOfArguments(String command) {
    CommandArgumentList arguments = getSingleArgument(command);
    while (this.nextToken.equals(COMMA)) {
      getToken();
      arguments.add(getCriteria(command));
    }
    return arguments;
  }

  private Criteria getCriteria(String command) {
    Criteria criteria;

    if (command.equals(SELECT)) {
      criteria = getSelectCriteria();
    } else if (command.equals(ORDER)) {
      criteria = getOrderCriteria();
    } else if (command.equals(FILTER)) {
      criteria = getFilterCriteria();
    } else if (command.equals(GROUP)) {
      criteria = getGroupCriteria();
    } else {
        throw new IllegalArgumentException(UNKNOWN_COMMAND_ERR);
    }
    return criteria;
  }
  private Criteria getGroupCriteria() {
    return new GroupCriteria(getColumn());
  }

  private Criteria getFilterCriteria() {

    if (isBinOp()) {
      String binOp = this.nextToken;
      getToken();
      return new FilterCriteria(binOp);

    } else {
      String match;
      String column = getColumn();

      if (this.nextToken.equals(EQUAL)) {
        getToken();
        match = this.nextToken;
        getToken();
      } else {
        throw new IllegalArgumentException(INCORRECT_FILTER_ERR);
      }
      return new FilterCriteria(column, match);
    }
  }

  private boolean isBinOp() {
    return this.nextToken.equals(AND) || this.nextToken.equals(OR);
  }

  private Criteria getOrderCriteria() {
    return new OrderCriteria(getColumn());
  }

  private Criteria getSelectCriteria() {
    final String[] AGGREGATES = {MAX, MIN, SUM, COUNT, COLLECT};
    String aggregate;
    String column = getColumn();

    if (this.nextToken.equals(COLON)) {
      getToken();
      if (Arrays.asList(AGGREGATES).contains(this.nextToken)) {
        aggregate = this.nextToken;
        getToken();
        return new SelectCriteria(column, aggregate);
      } else {
        throw new IllegalArgumentException(UNKNOWN_AGGREGATE_ERR);
      }
    } else {
      return new SelectCriteria(column);
    }
  }

  private String getColumn() {
    if (dataStore.getColumns().contains(this.nextToken)) {
      String column = this.nextToken;
      getToken();
      return column;
    } else {
      throw new IllegalArgumentException(COLUMN_ERR);
    }
  }

  private void getToken() {
    int len = tokens.size();
    if (this.nextIndex < len) {
      this.nextToken = this.tokens.get(nextIndex++);
    } else {
      this.nextToken = EOL;
    }
  }


}
