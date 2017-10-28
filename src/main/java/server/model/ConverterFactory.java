package server.model;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
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
