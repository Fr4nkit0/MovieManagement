package management.service.validator;


import management.exceptions.InvalidPasswordException;

import org.springframework.util.StringUtils;

public class PasswordValidator {
    public static void validatePassword (String password , String passwordRepeated){
        if (!StringUtils.hasText(password)|| !StringUtils.hasText(passwordRepeated)){
            throw new IllegalArgumentException("Password must contain data");
        }
        if(!password.equals(passwordRepeated)){
            throw new InvalidPasswordException(password,passwordRepeated,"password do not match");
        }
        if (!containsNumber(password)){
            throw  new InvalidPasswordException(password,"Password must contain at least one number");
        }
        if (!containsUpperCause(password)){
            throw new InvalidPasswordException(password,"Password must contain at least one uppercase letter");
        }
        if (!containsLowerCause(password)){
            throw new InvalidPasswordException(password,"Password must contain at least one lowercase letter");
        }
        if (!containsSpecialCharacter(password)){
            throw new InvalidPasswordException(password,"Password must contain at least one special character");
        }
    }



    private static boolean containsSpecialCharacter(String password) {
        return password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\",.<>/?].*");
    }
    private static boolean containsLowerCause(String password) {
        return password.matches(".*[a-z].*");
    }
    private static boolean containsUpperCause(String password) {
        return password.matches(".*[A-Z].*");
    }

    private static boolean containsNumber(String password) {
        return password.matches(".*\\d.*");
    }
}
