package com.accountsservice.utils;

public class ValidationUtil {

    public static boolean isObjectNullOrEmptyOrZero(Object object) {
        if (object == null)
            return true;
        if (object.getClass().equals(String.class)) {
            String s = (String) object;
            if (s.trim().equals(""))
                return true;
        }
        if (object instanceof StringBuilder) {
            String s = ((StringBuilder) object).toString();
            if (s.trim().equals(""))
                return true;
        } else {
            return object.toString().trim().equals("0") || object.toString().trim().equals("0.0");
        }
        return false;
    }

    public static boolean isObjectNotNullOrEmptyOrZero(Object object) {
        return !isObjectNullOrEmptyOrZero(object);
    }

    public static boolean isValidFirstName(String value) {

        if (isObjectNotNullOrEmptyOrZero(value) && value.length() > 3)
            return true;

        return false;
    }

    public static boolean isValidLastName(String value) {

        return isValidFirstName(value);
    }

    public static boolean isValidEmailAddress(String theEmailAddress) {
        if (theEmailAddress == null || !theEmailAddress
                .matches("^([_a-zA-Z0-9-\\.]+)@([a-zA-Z0-9-]+)(\\.[_a-zA-Z0-9-]+)*(\\.[a-zA-Z]{2,63})$"))
            return false;
        else if (!isValidEmailLocalPart(theEmailAddress))
            return false;
        else
            return true;
    }

    private static boolean isValidEmailLocalPart(String emailAddress) {
        String emailLocalPart = emailAddress.substring(0, emailAddress.indexOf('@'));

        if (emailLocalPart.startsWith(".") || emailLocalPart.endsWith(".") || emailLocalPart.matches(".*(\\.){2,}.*"))
            return false;

        else
            return true;
    }

    public static boolean isValidPhoneNumber(String theNumber) {
        theNumber = theNumber.replaceAll(" ", "");
        if (theNumber.startsWith("+"))
            theNumber = theNumber.substring(1);
        if (theNumber.contains("x"))
            theNumber = theNumber.substring(0, theNumber.indexOf("x"));
        if (isValidInternationalPhoneNumber(theNumber, true))
            return true;
        else
            return false;
    }

    public static boolean isValidInternationalPhoneNumber(String theNumber, boolean required) {
        if (theNumber == null || (theNumber.trim().length() == 0))
            return (!required);
        else {
            theNumber = theNumber.replaceAll(" ", "");
            if ((theNumber.length() == 10 || theNumber.length() == 11 || theNumber.length() == 12
                    || theNumber.length() == 13 || theNumber.length() == 14) && isAllNumeric(theNumber, "+"))
                return true;
            else
                return false;
        }
    }

    public static boolean isAllNumeric(String input, String ignoreCharacters) {
        if (input == null || input.length() == 0)
            return false;

        char all[] = input.trim().toCharArray();
        boolean result = true;
        char tmp = ' ';
        for (int i = 0; i < all.length; i++) {
            tmp = all[i];
            if (!Character.isDigit(tmp) && !(ignoreCharacters.indexOf(tmp) > -1)) {
                result = false;
                break;
            }
        }
        return result;
    }

}
