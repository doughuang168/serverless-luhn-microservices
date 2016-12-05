package com.doughuang168.luhn.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;


public class LuhnApiValidate implements RequestHandler<Map<String,String>,String> {

    private static final String NUMERATOR_KEY = "numerator";
    private static final String DENOMINATOR_KEY = "denominator";
    private static final String CARDNUMBER_KEY = "cardnmber";
    private static final String template = "%s";
    private static final Logger LOGGER = Logger.getLogger(LuhnApiValidate.class.getName());


    public String handleRequest(Map <String,String> values, Context context) {

        LOGGER.info("Handling Validate request");

        if(!values.containsKey(CARDNUMBER_KEY)) {
            return "You need cardnmber";
        }

////////////////////////////////////////////////
        Luhn luhnInstance = new Luhn();
        String result="";
        String message="";
        String cardnumber = values.get(CARDNUMBER_KEY);

        if (luhnInstance.isValidLuhn(cardnumber)) {
            LOGGER.info("valid card");
            message= cardnumber+" is valid Luhn number";
            result="true";
        } else {
            LOGGER.info("invalid card");
            message=cardnumber+" is invalid Luhn number";
            result="false";
        }
        LOGGER.info(message);
        Response rsp = new Response(1,
                                    "OK",
                                    String.format(template, message),
                                    result
        );
        return message;
//////////////////////////////////////////////

//        new BigDecimal(23.3);
//
//        try {
//            BigDecimal numerator = new BigDecimal(values.get(NUMERATOR_KEY));
//            BigDecimal denominator= new BigDecimal(values.get(DENOMINATOR_KEY));
//            return  numerator.divide(denominator).toString();
//        } catch (Exception e) {
//            return "Please provide valid values";
//        }
    }

}
