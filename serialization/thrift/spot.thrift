
namespace java com.github.saint1991.samples

enum SpotType {
    A,
    S
}

struct Spot {
    1:required i32      id
    2:required SpotType type
}