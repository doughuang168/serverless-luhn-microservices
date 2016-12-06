package com.doughuang168.luhn.lambda;

import com.amazonaws.services.lambda.runtime.Context;

import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;


public class LuhnApiValidCardNumber implements RequestHandler<Map<String,String>,String> {

    private static final String STARTRANGE_KEY = "startRange";
    private static final String ENDRANGE_KEY = "endRange";
    private static final String template = "%s";
    private static final Logger LOGGER = Logger.getLogger(LuhnApiValidate.class.getName());

    public String handleRequest(Map<String,String> values, Context context) {

        LOGGER.info("Handling ValidCardNumber request");

        if(!values.containsKey(STARTRANGE_KEY)||!values.containsKey(ENDRANGE_KEY)) {
            return "You need both startRange and endRange";
        }

        Luhn luhnInstance = new Luhn();
        String result="";
        String message="";

        String startRange = values.get(STARTRANGE_KEY);
        String endRange = values.get(ENDRANGE_KEY);

        Integer numbers = luhnInstance.numberOfValidLuhnNumbersInRange(startRange,endRange);
        message="Number Of Valid LuhnNumbers In Range is "+numbers.toString();

        LOGGER.info(message);
        Response rsp = new Response(1,
                "OK",
                String.format(template, message),
                numbers.toString()
        );
        return message;
    }

}
