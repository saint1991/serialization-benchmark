
# Benchmarks of Serialization Systems

## Run benchmarks
```
$ sbt $project/jmh:run
```
where $project is one of the name of sbt sub project (e.g. avroBench)


## Sample data file generation
```
$ sbt $project/run
```


## Results
### Average time to encode 100,000 records in seconds.

|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|:--:|
|0.032|0.156|0.140|0.066|0.052|0.302|

### Average time to decode 100,000 binary records in seconds.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|:--:|
|0.073|0.095|0.347|0.106|0.104|0.355|

### Data size of 10,000 encoded records in MB.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|:--:|
|1.87|1.83|1.69|1.75|3.15|3.23|


## Schemas
The data schemas used for this benchmark are located under [serialization](serialization)