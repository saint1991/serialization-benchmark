
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
- JDK 1.8.0_171, Java HotSpot(TM) 64-Bit Server VM, 25.171-b11

### Average time to encode 100,000 records in milli seconds.

- 20 warming up iteration
- Average of 20 iteration

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) | MessagePack (jackson-module-msgpack) | MessagePack (msgpack4z) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 43.0  | 235.8 | 232.6 | 116.8 | 74.6  | 488.7 | 354.8 | 358.0  |

### Average time to decode 100,000 binary records in milli seconds.

- 20 warming up iteration
- Average of 20 iteration

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON (with jsoniter-scala) | JSON (with circe) | MessagePack (jackson-module-msgpack) | MessagePack (msgpack4z) |
| :---: | :---: | :---: | :---: | :---: | :---: | :---: | :---: |
| 139.5 | 162.9 | 586.0 | 160.8 | 151.3 | 503.5 | 414.9 | 609.5 |

### Data size of 100,000 encoded records in MB.

| Protocol Buffers (proto3) | Thrift (compact protocol) | Avro | CSV | JSON | MessagePack |
| :---: | :---: | :---: | :---: | :---: | :---: |
| 18.5  | 18.3  | 16.7  | 17.4  | 32.2  | 25.7  |
