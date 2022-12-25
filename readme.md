Rest api Web crawler

This rest api is created using Java, Spring Framework(Boot, Data, Test), PostgreSQL, REST api, JUnit and Mockito

The web crawler uses input data such as URL and terms set, discovers a web resource with this URL and collects data on how many times these terms occur in the web resource. Also, the web crawler find other URLs in the web resource and gains the same data.   

To run this rest api you need:
- pull this project to your repository;

- create a schema seeds_base in PostgreSQL, if you use another name of schema you can change the 'spring.datasource.url' property in the 'src/main/resources/application.properties' file;

- change the 'spring.datasource.password' property in the 'src/main/resources/application.properties' file to meaning that suits your database;

- open terminal, go to the package with this api and run the command ./gradlew bootRun;

- by default, this api runs on the port 8088, if you need another port you can change the 'server.port' property in the 'src/main/resources/application.properties' file to meaning that suits you;

The api handles following endpoints(for localhost):

- POST:

http://localhost:8088/api/v1/seeds?seed=anyURL&terms=termOne, termTwo, ... - crawl web resource with anyURL and collect data about included URLs and terms.

Output data includes list of objects those include data such as 'url firsTermsNumber secondTermsNumber ... totalRepetitionsNumber'. 
All results are written in database and the file csv-file, that you can find in the root directory after post request will be completed.
If meanings of the seed or terms parameters are absent, empty or not correct the empty result will be received.

- GET:

http://localhost:8088/api/v1/seeds - get all result from database; this request can be complemented by parameters:
    - limit - the number of first results those will be received;
    - search - search by  part of term or url.

Output data has Json format as:

{[{
'seedData':'anyURL 1 5 7 13'
},
{
'seedData':'anyURL 2 6 8 16'
}
]
}