# transaction-service

## Some assumptions/notes done:

1) It is assumed transaction happens on transactions level. This means that no consideration is done of the users the accounts belong to.
2) It is assumed that account service and transaction service reside in the same code base but note that this can be different stand-alone services communicating via APIs.
3) It is assumed that for any currencies the conversation rate is 1 i.e. x amount in currency A after conversion is still x amount in currency B. This is done for simplicity. But in reality there will be a service that knows the conversion rates in real time and does conversions itself. 
4) log4j was configured in most basic way, but one is aware of log4j.properties
6) Adding and subtracting some amount of accounts could have returned the new amount, but since it is not used at the end, the current type is void
7) In Account in add/subtract operations no checks have been done since it is assumed that Account can't be changed outside AccountService, therefore AccountService deals with the bulk of validating operations done on an account.
8) Other assumptions/notes are put as comments in the code where appropriate. 

## To run and access the transaction service

1) In terminal execute the following command:
```
java -jar jar/transaction-service-1.0-SNAPSHOT-jar-with-dependencies.jar
```

2) Access the service by executing localhost:4567/transaction/sender/receiver/100/GBP in the browser, substituting the values
Some dummy accounts have been added with the following data:
Account (dummy1, 200, GBP)
Account (dummy2, 10, USD)