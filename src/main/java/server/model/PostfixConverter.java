package server.model;

import java.util.List;
import java.util.logging.Logger;

/**
 * Author: Mr.Chatchapol Rasameluangon
 * ID: 5810404901
 */
public class PostfixConverter extends ExpressionConverter {
    private final Logger logger = Logger.getLogger(PostfixConverter.class.getName());

    PostfixConverter() {
        super();
        logger.setParent(Logger.getLogger(ExpressionConverter.class.getName()));
    }

    /**
     * convert all to post
     *
     * @param expr Expression to convert
     * @return Result string of postfix conversion
     */
    @Override
    public String convert(String expr) {
        expr = expr.replaceAll(" ", "");    // remove the whitespaces
        List<String> exprList = toList(expr);                 // convert expr to list
        List<String> result = process(exprList);

        String result_expr = String.join(" ", result);
        logger.info(result_expr);
        return result_expr;
    }
}

