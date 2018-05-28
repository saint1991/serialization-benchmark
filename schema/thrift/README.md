# Apache Thrift

### Installation (Mac)
```
brew install thrift
```

### Schema

- Similar to proto2, all fields are uniquely indexed so that receivers can decode data based on it.

```nobid.thrift
include "spot.thrift"
namespace java com.githu.saint1991.samples

struct Nobid {
    1: required i32                 adnw_id,
    2: required string              app_name,
    3: required string              auction_id,
    4: required string              host,
    5: required string              logged_at,
    6: required i32                 m_id,
    7: required i32                 nbr,
    8: optional string              page,
    9: required i32                 res_time,
    10:required spot.Spot           spot,
    11:optional list<string>        history,
    12:optional map<string,string>  tags
}
```

```spot.thrift
namespace java com.github.saint1991.samples

enum SpotType {
    A,
    S
}

struct Spot {
    1:required i32      id
    2:required SpotType type
}
```

### Code generation
```
thrift --gen java nobid.thrift
```