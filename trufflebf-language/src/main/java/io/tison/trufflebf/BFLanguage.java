package io.tison.trufflebf;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.instrumentation.ProvidedTags;
import com.oracle.truffle.api.instrumentation.StandardTags;

@TruffleLanguage.Registration(id = "bf", name = "BF", version = "0.1", mimeType = "application/x-bf")
@ProvidedTags({StandardTags.StatementTag.class, StandardTags.RootTag.class})
@SuppressWarnings("unused")
public class BFLanguage extends TruffleLanguage<Object> {

    @Override
    protected Object createContext(TruffleLanguage.Env env) {
        return null;
    }

    @Override
    protected boolean isObjectOfLanguage(Object object) {
        return false;
    }

    @Override
    protected CallTarget parse(ParsingRequest request) {
        BFRootNode rootNode = new BFRootNode(this, request.getSource());
        return Truffle.getRuntime().createCallTarget(rootNode);
    }
}
