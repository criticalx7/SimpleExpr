package client;

import common.StatusCode;

class InputProcessor {

    String process(String input) {
        String result = "";
        String[] data = input.split(":");
        String code = data[0];
        if (code.equals(StatusCode.ANSWER.getCode())) {
            result = data[1];
        } else if (code.equals(StatusCode.ERROR.getCode())) {
            result = String.format("%s: %s", StatusCode.ERROR.toString(), data[1]);
        }
        return result;
    }
}
