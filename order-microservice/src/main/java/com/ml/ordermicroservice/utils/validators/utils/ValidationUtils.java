package com.ml.ordermicroservice.utils.validators.utils;

import com.ml.ordermicroservice.service.BaseService;
import org.apache.kafka.common.errors.ResourceNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class ValidationUtils {
    final static String EMAIL_PATTERN = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";

    private ValidationUtils() {
    }

    public static <T> T existById(Integer id, BaseService<T> service, String humanizedEntityName) {
        if (Objects.isNull((id))) throw new NullPointerException((humanizedEntityName + " id cannot be null"));
        Optional<T> existing = service.findById(id);
        if (existing.isEmpty()) {
            throw new ResourceNotFoundException(humanizedEntityName + " with id " + id + " not found.");
        }
        return existing.get();
    }

    public static <T> boolean existByStringValue(String value, BaseService<T> service, String humanizedEntityName) {
        if (Objects.isNull((value))) throw new NullPointerException((humanizedEntityName + " value cannot be null"));
        Optional<T> existing = service.findByStringValue(value);
        if (existing.isEmpty()) {
            throw new ResourceNotFoundException(humanizedEntityName + " with value " + value + " not found.");
        }
        return true;
    }


    public static boolean hasSizeGreaterThanOne(Integer size){
        if(size > 0) {
            return true;
        }
        return false;
    }

    public static boolean isStringPresent(String string){
        return isObjectPresent(string) && !string.trim().isEmpty();
    }

    public static boolean isWithinTheList(List<String> arrayList, String value){
        return arrayList.contains(value);
    }

    public static boolean isDoublePresent(Double value){
        return isObjectPresent(value) && !value.toString().trim().isEmpty();
    }

    public static boolean isObjectPresent(Object object) {
        return Objects.nonNull(object);
    }

    public static boolean isValidEmail(String string){
        Pattern pattern = Pattern.compile(EMAIL_PATTERN, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(string);
        return matcher.find();
    }


}
