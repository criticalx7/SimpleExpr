package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public class PrefixConverter extends ExpressionConverter {
    private final Logger logger = Logger.getLogger(PrefixConverter.class.getName());

    PrefixConverter() {
        super();
        logger.setParent(Logger.getLogger(ExpressionConverter.class.getName()));
    }

    private List<String> reverseList(List<String> list) {
        List<String> result = new ArrayList<>();
        for (int i = list.size() - 1; i >= 0; i--) {
            String token = list.get(i);
            switch (token) {
                case "(":
                    result.add(")");
                    break;
                case ")":
                    result.add("(");
                    break;
                default:
                    result.add(token);
                    break;
            }
        }
        logger.info("Reverse: " + result.toString());
        return result;
    }


    /**
     * convert all to prefix by reverse the input list and reverse the output list
     *
     * @param expr Expression to convert
     * @return Result string of postfix conversion
     */
    @Override
    public String convert(String expr) {
        expr = expr.replaceAll(" ", "");    // remove all whitespace
        List<String> exprList = reverseList(toList(expr));    // convert expr to list and reverse it
        List<String> result = process(exprList);

        Collections.reverse(result);                          // reverse the result list again
        String result_expr = String.join(" ", result);
        logger.info(result_expr);
        return result_expr;
    }
}
