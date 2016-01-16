# Payment slip
## Usage:
### from sbt
open sbt shell, then
```
runMain com.myob.MainApp src/test/resources/input.csv src/main/resources/default_tax_table output.txt
```

### from jar

```
sbt clean test package &&  
scala -classpath target/scala-2.11/payment-slip_2.11-1.0.jar com.myob.MainApp src/test/resources/input.csv src/main/resources/default_tax_table output.txt 
```

### Arguments:
- The first argument is input file path (required), otherwise the usage tip will be printed.
- The second argument is tax table file path (optional), if it is omitted, default tax table is used. 
- The third argument is out put file path (optional), if it is omitted, the app will output the result to console directly.   
 

## For Development:

Run the following commands before push to remote:  

```
git pull --rebase && sbt test it:test && git push
```

### unit test

```
sbt test
```

### integration test

```
sbt it:test
```
 
## Assumption:
- all numbers involve in will never exceed `Int.MaxValue` (2147483647), otherwise we should use `BigDecimal` instead of `Int`
- no negative tax rates or salary will ever happen. 
- 'payment start date' becomes 'pay period' directly

## Tasks:
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

- partial month (done)
- cross months (done) 
  - list months (done)
- list weeks
- cross years

