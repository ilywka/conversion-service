
#Conversion service
//todo description

#Commands
Start application\
`
mvn spring-boot:run 
    -Dspring-boot.run.profiles=production
    -Dspring-boot.run.jvmArguments="-Dconversion.provider.exchangerate-api-com.api-key={Insert exchangerate-api-com api key}
    -Dconversion.provider.exchangerates-api-io.api-key={Insert exchangerates-api-io api key}"
`

# Project Title

Currency conversion service

## Description

Service provides currency conversion functionality.
2 providers used to get conversion rates: https://www.exchangerate-api.com/ and https://www.exchangerate-api.com/  

## Getting Started

### Prerequisites

* Java 11
* Providers api keys

### Commands

####build
`mvn clean package`

####run
`
mvn spring-boot:run
-Dspring-boot.run.profiles=production
-Dspring-boot.run.jvmArguments="-Dconversion.provider.exchangerate-api-com.api-key={Insert exchangerate-api-com api key}
-Dconversion.provider.exchangerates-api-io.api-key={Insert exchangerates-api-io api key}"
`
