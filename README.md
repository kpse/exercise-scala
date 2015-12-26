# Payment slip
## Usage:
open sbt shell, then
```
runMain com.myob.MainApp src/test/resources/input.csv src/main/resources/default_tax_table output.txt
```
### Arguments:
- The first argument is input file path (required), otherwise the usage tip will be printed.
- The second argument is tax table file path (optional), if it is omitted, default tax table is used. 
- The third argument is out put file path (optional), if it is omitted, the app will output the result to console directly.   
 
## Assumption:
- all numbers involve in will never exceed `Int.MaxValue` (2147483647), otherwise we should use `BigDecimal` instead of `Int`
- no negative tax rates or salary will ever happen. 

## Tasks
- report payment slip (done)
- detail: pay period (done)
- detail: gross income (done)
- detail: income tax (done)
  - base on Monthly Tax Rate (done)
- detail: net income (done)  
- detail: super (done)
- rounding rule (done)
- read Monthly Tax Rate from input (done)
- pass the second person test (done)
- error handling (done)
- better test coverage (done)
- list assumption (done)
 
## Background
 
 When I input the employee's details: first name, last name, annual salary(positive integer) and super rate(0% - 50% inclusive), payment start date, the program should generate payslip information with name, pay period,  gross income, income tax, net income and super.
 
### The calculation details will be the following:

- pay period = per calendar month

- gross income = annual salary / 12 months

- income tax = based on the tax table provide below

- net income = gross income - income tax

- super = gross income x super rate
 
***Notes: All calculation results should be rounded to the whole dollar. If >= 50 cents round up to the next dollar increment, otherwise round down.***
 
## Example
### input (first name, last name, annual salary, super rate (%), payment start date):
- David,Rudd,60050,9%,01 March – 31 March
- Ryan,Chen,120000,10%,01 March – 31 March
 
### Output (name, pay period, gross income, income tax, net income, super):
- David Rudd,01 March – 31 March,5004,922,4082,450
- Ryan Chen,01 March – 31 March,10000,2696,7304,1000