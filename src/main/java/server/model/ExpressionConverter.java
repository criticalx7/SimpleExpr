package server.model;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public abstract class ExpressionConverter {
    private final Logger logger = Logger.getLogger(ExpressionConverter.class.getName());
    private final Map<String, Integer> operator;

    ExpressionConverter() {
        logger.setLevel(Level.OFF);
        operator = new HashMap<>();
        operator.put("+", 1);
        operator.put("-", 1);
        operator.put("*", 2);
        operator.put("/", 2);
        operator.put("^", 3);
        operator.put("(", 0);
        operator.put(")", 0);
    }

    List<String> toList(String expr) {
        List<String> result = new ArrayList<>();
        StringBuilder builder = new StringBuilder();
        for (char token : expr.toCharArray()) {
            if (!operator.containsKey(String.valueOf(token))) {
                builder.append(token);
            } else {
                if (builder.length() != 0) {
                    result.add(builder.toString());
                    builder.setLength(0);
                }
                result.add(String.valueOf(token));

            }
        }
        String remnant = builder.toString();
        if (remnant.length() != 0) result.add(remnant);
        logger.info(String.format("Sequence: %s", result.toString()));
        return result;
    }

    List<String> process(List<String> exprList) {
        Deque<String> stack = new ArrayDeque<>();
        List<String> result = new ArrayList<>();
        for (String token : exprList) {
            if (!operator.containsKey(token)) {
                result.add(token);
            } else if (token.equals("(")) {
                stack.push(token);
            } else if (token.equals(")")) {
                String top;
                while (!(top = stack.pop()).equals("("))
                    result.add(top);
            } else {
                while (!stack.isEmpty() && (operator.get(stack.peek()) >= operator.get(token)))
                    result.add(stack.pop());
                stack.push(token);
            }
        }

        while (!stack.isEmpty())
            result.add(stack.pop());
        return result;
    }

    public abstract String convert(String expr);
}
