
# Benchmarks against Serialization Systems
[![Build Status](https://travis-ci.org/saint1991/serialization-benchmark.svg?branch=master)](https://travis-ci.org/saint1991/serialization-benchmark)

## Run benchmarks
```
$ sbt $project/jmh:run
```
where $project is one of the name of sbt sub project (e.g. avroBench)


## Sample data file generation
```
$ sbt $project/run
```

## Schemas
The schemas used in this benchmark are under [serialization](schema)

## Results

**NOTE**: 
This benchmark is taken place under the specific condition: 
Under the other conditions, results may be different.

### Average time to encode 100,000 records in seconds.

|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|:--:|
|0.032|0.156|0.140|0.066|0.052|0.302|

### Average time to decode 100,000 binary records in seconds.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) |
|:--:|:--:|:--:|:--:|:--:|:--:|
|0.073|0.095|0.347|0.106|0.104|0.355|

### Data size of 10,000 encoded records in MB.
|Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON | MsgPack |
|:--:|:--:|:--:|:--:|:--:|:--:|:--:|
|1.82|1.80|1.64|1.72|3.20|3.35|
