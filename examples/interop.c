#include <polyglot.h>

int main() {
  long long data[] = { 72, 101, 108, 108, 111, 32, 102, 114, 111, 109, 32, 84, 114, 117, 102, 102, 108, 101, 66, 70, 32, 105, 110, 32, 67, 46, 10, 0 };
  void (*program)(void**, int) = polyglot_eval("bf", "[.>]");
  program(polyglot_from_i64_array(data, 28), 0);
}
