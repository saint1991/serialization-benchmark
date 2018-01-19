
# Benchmarks of Serialization Systems

## Build
```
$ sbt jmh:compile
```

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

|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|
|0.039|0.148|0.169|0.099|0.558|

### Average time to decode 100,000 binary records in seconds.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|
|0.082|0.106|0.357|0.115|0.584|

### Data size of 10,000 encoded records in MB.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|
|1.87|1.83|1.69|1.75|3.23|


## Schemas
The data schemas used for this benchmark are located under [serialization](serialization)