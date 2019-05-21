package examples;

import org.graalvm.polyglot.*;
import org.graalvm.polyglot.proxy.*;

public class Interop {
    private static final String BF = "bf";
    public static void main(String[] args) {
        System.out.print("Your name(in 4 letters): ");
        Context context = Context.newBuilder(BF)
            .allowAllAccess(true)
            .in(System.in).out(System.out)
            .build();
        Value value = context.eval(BF, ",>,>,>,");
        Value state = value.execute();
        Value data = state.getMember("data");
        System.out.print("Hello ");
        for (int i = 0; i < 4; i++) {
          System.out.print((char)data.getArrayElement(i).asLong());
        }
        System.out.println("!");
        System.out.print(state.asString());
    }
}
