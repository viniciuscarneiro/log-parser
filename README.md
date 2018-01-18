# log-parser

This application can parse a log file(pipe delimited) based on the following template:

{Date|IP|Request|Status|User Agent

2017-01-01 00:00:21.164|192.168.234.82|"GET / HTTP/1.1"|200|"swcd (unknown version) CFNetwork/808.2.16 Darwin/15.6.0"

Just configure the application.properties with your database credentials

To execute just run Parser.java or java -jar parser.jar --startDate=2017-02-01.13:00:00 --duration=hourly --threshold=1000

Input params:

Required:
* --startDate=2017-01-01.15:00:00
* --duration=hourly or daily
* --threshold=200

Optional:  

*  --accesslog=/path/to/access.log
