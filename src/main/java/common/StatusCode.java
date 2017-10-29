package common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 * <p>
 * A representation of status code and reason phrase
 */
public enum StatusCode {
    PREFIX("01"),
    POSTFIX("02"),
    ERROR("11"),
    ANSWER("12");

    private static final Map<String, StatusCode> map = Collections.unmodifiableMap(initialize());
    private final String code;

    StatusCode(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    /**
     * Evaluate the phrase from code
     *
     * @param code A code that map to phrase
     * @return A phrase that map to the code
     */
    public static StatusCode evaluate(String code) {
        return map.get(code);
    }

    private static HashMap<String, StatusCode> initialize() {
        HashMap<String, StatusCode> result = new HashMap<>();
        for (StatusCode phrase : StatusCode.values()) {
            result.put(phrase.code, phrase);
        }
        return result;
    }
}
