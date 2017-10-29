package server;

import common.StatusCode;
import server.model.ConverterFactory;
import server.model.ExpressionConverter;

class ConverterProtocol {


    String process(String input) {
        String result;
        boolean colonUsagePass = checkColonUsage(input);
        if (colonUsagePass) {
            String[] clientData = input.split(":");
            String code = clientData[0];
            String expr = clientData[1];
            result = String.format("%s:%s", StatusCode.ANSWER.getCode(), getConverter(code).convert(expr));
        } else {
            result = String.format("%s:%s", StatusCode.ERROR.getCode(), "Cannot use colon in expression");
        }

        return result;
    }

    private Boolean checkColonUsage(String input) {
        boolean parsable = true;
        int count = 0;
        for (char c : input.toCharArray()) {
            if (c == ':') {
                count++;
                if (count == 2) {
                    parsable = false;
                }
            }
        }
        return parsable;
    }

    private ExpressionConverter getConverter(String code) {
        ExpressionConverter converter = null;
        StatusCode phrase = StatusCode.evaluate(code);
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
