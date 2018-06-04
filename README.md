
# Benchmarks against Serialization Systems
[![Build Status](https://travis-ci.org/saint1991/serialization-benchmark.svg?branch=master)](https://travis-ci.org/saint1991/serialization-benchmark)


## Characteristics

| | Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON | MessagePack |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
|        Schema-less?        | No | No | No | No | Yes | Yes |
| Require compiling schema in advance? | Yes | Yes | No | No | No | No |

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
The schemas used in this benchmark are under [schema](schema)

## Results

**NOTE**: This benchmark is taken place under the specific condition, results may be different under the other conditions.

### Benchmark setup

- OS: Ubuntu 16.04 TLS
- CPU: Intel(R) Xeon(R) CPU E5-2680 v3 2.50GHz, 4 cores
- Memory: 14GB
- Java(TM) SE Runtime Environment (build 1.8.0_171-b11), Java HotSpot(TM) 64-Bit Server VM (build 25.171-b11, mixed mode)

### Average time to encode 100,000 records in milli seconds.

- 20 warming up iteration
- Average of 20 iteration

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) | MessagePack (jackson-module-msgpack) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 43.1  | 231.6 | 230.0 | 112.3 | 74.5  | 485.6 | 359.8 |

### Average time to decode 100,000 binary records in milli seconds.

- 20 warming up iteration
- Average of 20 iteration

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) | MessagePack (jackson-module-msgpack) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 136.4 | 166.5 | 577.6 | 168.0 | 154.8 | 503.3 | 408.7 |

### Data size of 100,000 encoded records in MB.

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON | MessagePack |
| :---: | :---: | :---: | :---: | :---: | :---: |
| 18.0  | 17.8  | 16.3  | 17.0  | 30.9  | 24.8  |
