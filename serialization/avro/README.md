
# Apache Avro

## languages
officially supported
- C
- C++
- C#
- Java
- Perl
- Python
- Ruby
- PHP

## schema
- No field index is included in data
    - decoding is always based on writer's (and reader's for schema evolution) schema.
- Dynamic schema resolution
    - no need to generate codes in advance
    -  encode/decode is conduct according only to schema

   
``` nobid.avsc
{
    "namespace": "com.github.saint1991.samples",
    "name":"Nobid",
    "type":"record",
    "fields":[
        {"name":"adnwId","type": "int"},
        {"name":"auctionId","type":"string"},
        {"name":"host","type":"string"},
        {"name":"loggedAt","type":"string"},
        {"name":"mId","type":"int"},
        {"name":"nbr","type":"int"},
        {"name":"page","type":["null", "string"], "default": null},
        {"name":"resTime","type":"int"},
        {"name":"spot","type": {
            "name": "spotRecord",
            "type": "record",
            "fields": [
                {"name": "id", "type": "int"},
                {"name": "type", "type": {
                    "name": "spotType",
                    "type": "enum",
                    "symbols": ["A", "S"]
                }}
            ]
        }},
        {"name": "history", "type": {
            "name": "historyItems",
            "type": "array",
            "items": {
                "name": "historyItem",
                "type": "string"
            }
        }},
        {"name": "tags", "type": {
            "name": "tag",
            "type": "map",
            "values": "string"
        }}
    ]
}

```