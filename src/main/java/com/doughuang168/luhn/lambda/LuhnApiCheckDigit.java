package com.doughuang168.luhn.lambda;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;

import java.math.BigDecimal;
import java.util.Map;
import java.util.logging.Logger;

public class LuhnApiCheckDigit implements RequestHandler<Map<String,String>,String> {

    private static final String CARDNUMBER_KEY = "cardnmber";
    private static final String template = "%s";
    private static final Logger LOGGER = Logger.getLogger(LuhnApiValidate.class.getName());

    public String handleRequest(Map <String,String> values, Context context) {

        LOGGER.info("Handling CheckDigit request");

        if(!values.containsKey(CARDNUMBER_KEY)) {
            return "You need both numberator and denominator";
        }

        Luhn luhnInstance = new Luhn();
        String result="";
        String message="";
        String cardnumber = values.get(CARDNUMBER_KEY);

        String chkDigit = luhnInstance.generateCheckDigit(cardnumber);
        message="Check digit is "+chkDigit;
        LOGGER.info(message);
        Response rsp = new Response(1,
                "OK",
                String.format(template, message),
                chkDigit
        );
        return message;



    }

}
