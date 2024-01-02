package com.example.spge_sts;

public enum InvalidInputMessages {

    STUDENT_USERNAME("""
            Format: firstName_lastName_number@numberLetter
                                    
            \t* firstName - contains only small letters (any between a and z), not more than 10 characters
            \t* lastName - contains only small letters (any between a and z), not more than 10 characters
            \t* number (before @) - must be between 1 and 27 (not length)
            \t* number (after @) - must be between 8 and 12 (not length)
            \t* Letter - contains a single letter, any between a and z
                                    
            (* - must)"""),

    TEACHER_USERNAME("""
            Format: firstName_lastName@numberLetter
                                    
            \t* firstName - contains only small letters (any between a and z), not more than 10 characters
            \t* lastName - contains only small letters (any between a and z), not more than 10 characters
            \t* number (after @) - must be between 8 and 12 (not length)
            \t* Letter - contains a single letter, any between a and z
                                    
            (* - must)"""),

    PASSWORD("The password must contain minimum eight characters, at least one upper case English letter, one lower case English letter, one number and one special character"),

    FIRST_NAME("First name must start with a capital letter (A-Z), followed by at least one small letter (a-z)."),

    LAST_NAME("Last name must start with a capital letter (A-Z), followed by at least one small letter (a-z) and ends up on one of the following; -ov, -ova, -ski, -ska, -ich."),

    EMAIL("The email must be a valid Gmail!"),

    PHONE("The phone number must be in format: +359 XX XXX XXXX! (X = [0-9])"),

    CODE("""
            The code must consist of the following:
                        
            1.Small letter (a-z)
            2.Special symbol(!, @, #, $, %, &)
            3.Digit (0-9)
            4.Capital letter (A-Z)
            5.Special symbol(!, @, #, $, %, &)
            6.Small letter (a-z)
                        
            The order must be the same!""");

    private final String message;

    InvalidInputMessages(String message) {
        this.message = message;
    }

    public String getMessage() {
        return this.message;
    }
}
