package server;

import common.ConverterPhrase;
import server.model.ConverterFactory;
import server.model.ExpressionConverter;

class ConverterProtocol {


    String processInput(String input) {
        String[] clientData = input.split(" ");
        String code = clientData[0];
        String expr = clientData[1];
        return getConverter(code).convert(expr);
    }

    private ExpressionConverter getConverter(String code) {
        ExpressionConverter converter = null;
        ConverterPhrase phrase = ConverterPhrase.evaluate(code);
        switch (phrase) {
            case POSTFIX:
                converter = ConverterFactory.getPostfixConverter();
                break;
            case PREFIX:
                converter = ConverterFactory.getPrefixConverter();
        }
        return converter;
    }
}
