
# MessagePack

MessagePack is a kind of serialization format.
It iss schema-less line JSON.

## Type system

MessagePack has 7 built-in types and extension type.

### Built-in types

- Integer: represents integer that has Long precision in most languages.
- Nil: represents nil.
- Boolean: represents true or false.
- Float: represents a floating point number that has double precision in most languages.
- Raw
    - String: represents UTF-8 string.
    - Binary: represents byte array.
- Array: represents a sequence of objects
- Map: represents a key-value pairs of objects

### Extension types

Extension type is the functionality to define application specific data type.
It consists of magic bytes, another magic byte representing its type and data as the array of bytes.

## Efficiency

MessagePack is efficient and safe format. 
It generally consists of magic bytes and the data as the array of bytes.
Data is serialized into variable-length binary so its size becomes smaller in many cases.
