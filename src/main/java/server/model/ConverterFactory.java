package server.model;

public class ConverterFactory {

    private ConverterFactory() {
    }

    public static ExpressionConverter getPostfixConverter() {
        return new PostfixConverter();
    }

    public static ExpressionConverter getPrefixConverter() {
        return new PrefixConverter();
    }
}
