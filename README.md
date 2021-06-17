# Currency conversion service

## Description

Service provides currency conversion functionality.\
2 providers used to get conversion rates:
* https://www.exchangerate-api.com
* https://exchangeratesapi.io

## Getting Started

### Prerequisites

* Java 11
* Providers api keys

### Commands
___
Build project

`./mvnw clean package`
___

Run

```
./mvnw spring-boot:run \
-Dspring-boot.run.profiles=production \
-Dspring-boot.run.jvmArguments="-Dconversion.provider.exchangerate-api-com.api-key={Insert exchangerate-api-com api key} -Dconversion.provider.exchangerates-api-io.api-key={Insert exchangerates-api-io api key}"
```

By default service runs on 8080 port
___