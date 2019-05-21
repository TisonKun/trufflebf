package io.tison.trufflebf;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("unused")
public class BFProgram implements TruffleObject {
    private final CharSequence instructions;

    private final Map<Integer, Integer> jump = new HashMap<>();
    private final Deque<Integer> stack = new ArrayDeque<>();

    private long[] data;

    private final int end;

    private int ip;
    private int dp;

    public BFProgram(CharSequence instructions) {
        this.instructions = instructions;
        this.end = instructions.length();
    }

    @ExportMessage
    boolean isExecutable() {
        return true;
    }

    @ExportMessage
    Object execute(Object[] arguments) throws UnsupportedMessageException {
        if (arguments.length == 2) {
            ip = 0;

            if (arguments[0] instanceof TruffleObject) {
                TruffleObject object = (TruffleObject) arguments[0];
                InteropLibrary library = InteropLibrary.getFactory().getUncached();
                int size = (int) library.getArraySize(object);
                data = new long[size];
                for (int i = 0; i < size; i++) {
                    try {
                        data[i] = ((Number) library.readArrayElement(object, i)).longValue();
                    } catch (Exception e) {
                        throw new RuntimeException("Arg0 should be instance of a long sequence");
                    }
                }
            } else {
                throw new RuntimeException("Arg0 should be instance of a long sequence");
            }

            if (arguments[1] instanceof Number) {
                dp = ((Number) arguments[1]).intValue();
            } else {
                throw new RuntimeException("Arg1 should be instance of Integer");
            }
        } else if (arguments.length == 0) {
            ip = 0;
            dp = 0;
            data = new long[1024];
        } else {
            throw new RuntimeException("Either execute with no arg or (data, data pointer)");
        }

        return eval();
    }

    private Object eval() {
        while (ip < end) {
            switch (instructions.charAt(ip)) {
                case '>': dp++; break;
                case '<': dp--; break;
                case '+': data[dp]++; break;
                case '-': data[dp]--; break;
                case '.': System.out.print((char)data[dp]); break;
                case ',':
                    try {
                        data[dp] = System.in.read();
                    } catch (IOException e) {
                        throw new UncheckedIOException(e);
                    }
                    break;
                case '[':
                    if (!jump.containsKey(ip)) {
                        stack.addLast(ip);
                    }

                    if (data[dp] == 0) {
                        if (jump.containsKey(ip)) {
                            ip = jump.get(ip) + 1;
                            continue;
                        } else {
                            while (instructions.charAt(ip) != ']') {
                                ip++;
                            }

                            int m = stack.removeLast();
                            jump.put(m, ip);
                            jump.put(ip, m);
                        }
                    }
                    break;
                case ']':
                    if (!jump.containsKey(ip)) {
                        int m = stack.removeLast();
                        jump.put(m, ip);
                        jump.put(ip, m);
                    }

                    if (data[dp] != 0) {
                        ip = jump.get(ip) + 1;
                        continue;
                    }
                    break;
            }
            ip++;
        }

        return new BFObject(dp, ip, instructions, data);
    }

}
