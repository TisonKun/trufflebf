package io.tison.trufflebf;

import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;

public class BFRootNode extends RootNode {

    private final CharSequence instructions;

    public BFRootNode(TruffleLanguage<?> language, Source source) {
        super(language);
        instructions = source.getCharacters();
    }

    @Override
    public Object execute(VirtualFrame frame) {
        return new BFProgram(instructions);
    }
}
