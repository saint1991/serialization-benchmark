
# Protocol Buffers (by Google)

## Installation (Mac)
```
$ brew install protobuf
```

## languages
officially supported

- C++
- Java
- Python
- Objective-C
- C#
- JavaScript
- Ruby
- Go
- PHP
- Dart


## schema
- easier to keep compatibility when schema evolution thanks to self annotation
    - all fields are uniquely indexed so that receivers can decode data based on it.
- possible to reserve indices for future use as follows, `reserved 1 to 11; `
- any types are mapped to corresponding type for each language [(reference)](https://developers.google.com/protocol-buffers/docs/proto3#scalar)

``` nobid.proto
syntax = "proto3";
package samples;

// SPEED (default) | CODE_SIZE | LITE_RUNTIME
option optimize_for = SPEED;

// possible to import definition from other files
import "./spot.proto";

// message should be CamelCase
message Nobid {

  // fields should be snake_case
  // each field should have an unique index
  // required directive indicate a field must exis
  int32  adnw_id = 1;
  string app_name = 2;
  string auction_id = 3;
  string host = 4;
  string logged_at = 5;
  int32  m_id = 6;
  int32  nbr = 7;
  string page = 8;
  int32  res_time = 9;
  Spot spot = 10;


  repeated string history = 11;
  map<string, string> tags = 12;

  // it's possible to reserve some indices for future use
  reserved 13 to 15;
}

// rpc interface is defined by service
// it corresponds to interface or trait in Java and Scala respectively
service NobidBenchmark {
  // define a method that take a Nobid type parameter and returns int32
  rpc benchmark (Nobid) returns (int32);
}
```

``` spot.proto
syntax = "proto3";
package samples;

// enum should be CamelCase and each member should be UNDERSCORE_SEPARATED_CAPITALS
enum SpotType {
    A = 0; // begin with 0 in enum
    S = 1;
}

message Spot {
    int32 id = 1;
    SpotType type = 2;
}
```

## code generation
```
protoc --proto_path=IMPORT_PATH  \
    [--cpp_out=DST_DIR] \
    [ --java_out=DST_DIR] \ 
    [--python_out=DST_DIR] \
    [ --go_out=DST_DIR]  \
    [--ruby_out=DST_DIR] \
    [--javanano_out=DST_DIR] \ 
    [--objc_out=DST_DIR] \ 
    [--csharp_out=DST_DIR] \
        path/to/file.proto
```

For this sample,
```
protoc --proto_path=. --java_out=. nobid.proto
```
It generates java classes to the directory specified at `--java_out`

## Binary format


messages are represented just as key-value pairs  where each key is consist of index and [wire type](https://developers.google.com/protocol-buffers/docs/encoding#structure)

### example


The following bytes represents the value of index 1 is 150.
```
0000 1000 1001 0110  0000 0001
```

a key is always a varint. thus remove the first bit (it is most signifiant bit that represents whether further bytes are following)
```
000 1000 1001 0110 0000 0001
```

The last 3 bit in the first byte `00001000`  represents wire type,
Here, it is `000` so that wire type is `Variant` according to [reference](https://developers.google.com/protocol-buffers/docs/encoding#structure)
Then remaining bit is `0001` so we find its index is 1.

Following bytes `1001 0110 0000 0001` represents a varint value for index 1.
The first bit is most significant bit, inhere it is 1. That means another byte is following.
By ordering from lower bits, it becomes `00000001 0010110`
Interpret it as 2'complement, we can find it is 150.

### Varint
It is variable length codec of integers.
For each byte, the first bit is flag  whether further bytes are following. ( 1 means further bytes are following)
Except those bits, it is a 2's complement in order of  lower-significant-bytes-first.

### Strings
It is consists of variant and UTF8 binary.
The variant part gives the length of UTF8 binary part.