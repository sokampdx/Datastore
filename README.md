# Datastore
Datastore application

* original.txt is the file being imported.
* original.datastore is the Database file.

* type ./query with no argument for usage
* filter
  * -f COLUMN=data (AND|OR) ...
* group 
  * -g COLUMN, ...
* order
  * -o COLUMN, ...
* select
  * -s COLUMN[:AGGREGATE], ...
  
* COLUMN is column name of the table.
* data is for matching a field in the table.
* AGGREGATE must be one of these five {max, min, sum, count, collect}
  * max = maximum of a column for a group
  * min = minimum of a column for a group
  * sum = sum of all values within a group
  * count = number of distinct values of the column within a group
  * collect = a list of distinct values of the column within a group


