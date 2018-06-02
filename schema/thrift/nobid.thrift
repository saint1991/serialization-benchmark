include "spot.thrift"
namespace java com.github.saint1991.serialization.benchmark.thrift

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
    11:required list<string>        history,
    12:required map<string,string>  tags
}
