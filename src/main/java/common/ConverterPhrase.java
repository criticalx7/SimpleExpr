package common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public enum ConverterPhrase {
    POSTFIX("01"),
    PREFIX("02");

    private static final Map<String, ConverterPhrase> map = Collections.unmodifiableMap(initialize());
    private final String code;

    ConverterPhrase(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static ConverterPhrase evaluate(String code) {
        return map.get(code);
    }

    private static HashMap<String, ConverterPhrase> initialize() {
        HashMap<String, ConverterPhrase> result = new HashMap<>();
        for (ConverterPhrase phrase : ConverterPhrase.values()) {
            result.put(phrase.code, phrase);
        }
        return result;
    }
}
