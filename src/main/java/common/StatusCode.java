package common;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
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
