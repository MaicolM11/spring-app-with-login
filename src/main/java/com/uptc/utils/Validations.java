package com.uptc.utils;

import java.util.function.Predicate;

public class Validations {

    public static Predicate<String> isEmail = (email) -> email.contains("@");
    
}
