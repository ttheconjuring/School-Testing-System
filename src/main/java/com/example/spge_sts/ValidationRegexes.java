package com.example.spge_sts;

public enum ValidationRegexes {

    STUDENT_USERNAME("^[a-z]{1,10}_[a-z]{1,10}_(?:[1-9]|1[0-9]|2[0-7])@(?:[8-9]|1[0-2])[a-z]$"),
    TEACHER_USERNAME("^[a-z]{1,10}_[a-z]{1,10}@(?:[8-9]|1[0-2])[a-z]$"),
    PASSWORD("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$ %^&*-]).{8,}$"),
    FIRST_NAME("^[A-Z][a-z]+$"),
    LAST_NAME("^[A-Z][a-z]+(ov|ova|ev|eva|ski|ska|ich)$"),
    EMAIL("^[a-zA-Z0-9._%+-]+@gmail\\.com$"),
    PHONE("^\\+359 \\d{2} \\d{3} \\d{4}$"),
    CODE("^[a-z][!@#$%&][\\d][A-Z][!@#$%&][a-z]$");

    private final String regex;

    ValidationRegexes(String regex) {
        this.regex = regex;
    }

    public String getRegex() {
        return this.regex;
    }

}
