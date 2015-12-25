# Payment slip

## Tasks
- report payment slip
- detail: pay period
- detail: gross income
- detail: income tax
  - base on Monthly Tax Rate
- detail: net income  
- detail: super
- rounding rule
- read Monthly Tax Rate from input
- pass the second person test

 
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