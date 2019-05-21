package io.tison.trufflebf;

import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.Value;

import java.io.File;
import java.io.IOException;

public class BFMain {
    private final static String BF = "bf";

    public static void main(String[] args) throws IOException {
        Source source = Source.newBuilder(BF, new File(args[0])).build();
        Context context = Context.newBuilder(BF).allowAllAccess(true).in(System.in).out(System.out).build();
        Value value = context.eval(source);
        value.execute(new long[1024], 0);
    }
}
