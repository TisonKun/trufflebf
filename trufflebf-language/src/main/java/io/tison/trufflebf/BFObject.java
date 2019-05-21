package io.tison.trufflebf;

import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;

import java.util.Arrays;

@ExportLibrary(InteropLibrary.class)
@SuppressWarnings("unused")
public class BFObject implements TruffleObject {

    private final int dp;
    private final int ip;
    private final LongSeq data;
    private final CharSeq ins;

    public BFObject(int dp, int ip, CharSequence ins, long[] data) {
        this.dp = dp;
        this.ip = ip;
        this.data = new LongSeq(data);
        this.ins = new CharSeq(ins);
    }

    @ExportMessage
    Object readMember(String member) {
        switch (member) {
            case "dp": return dp;
            case "ip": return ip;
            case "data": return data;
            case "ins": return ins;
        }

        throw new RuntimeException("No such member " + member);
    }

    @ExportMessage
    boolean hasMembers() {
        return true;
    }

    @ExportMessage
    boolean isMemberReadable(String member) {
        return "dp".equals(member) || "ip".equals(member)
                || "ins".equals(member) || "data".equals(member);
    }

    @ExportMessage
    Object getMembers(boolean includeInternal) {
        return null;
    }

    @ExportLibrary(InteropLibrary.class)
    static class LongSeq implements TruffleObject {
        private final long[] data;
        LongSeq(long[] data) {
            this.data = data;
        }

        @ExportMessage
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        Object readArrayElement(long index) {
            return data[(int) index];
        }

        @ExportMessage
        long getArraySize() {
            return data.length;
        }

        @ExportMessage
        boolean isArrayElementReadable(long index) {
            return index < data.length;
        }
    }

    @ExportLibrary(InteropLibrary.class)
    static class CharSeq implements TruffleObject {
        private final CharSequence ins;
        private final long length;

        CharSeq(CharSequence ins) {
            this.ins = ins;
            this.length = ins.length();
        }

        @ExportMessage
        boolean hasArrayElements() {
            return true;
        }

        @ExportMessage
        Object readArrayElement(long index) {
            return ins.charAt((int) index);
        }

        @ExportMessage
        long getArraySize() {
            return length;
        }

        @ExportMessage
        boolean isArrayElementReadable(long index) {
            return index < length;
        }
    }

    @ExportMessage
    boolean isString() {
        return true;
    }

    @ExportMessage
    String asString() {
        return toString();
    }

    @Override
    public String toString() {
        return "BFObject={\n" +
                "\tip=" + ip + ";\n" +
                "\tins=" + ins.ins + ";\n" +
                "\tdp=" + dp + ";\n" +
                "\tdata=" + Arrays.toString(data.data) + ";\n" +
                "}\n";
    }
}
