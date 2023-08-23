package ru.itmentor.spring.boot_security.demo.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class UserRoleSetConverter implements AttributeConverter<Set<UserRole>, String> {

    @Override
    public String convertToDatabaseColumn(Set<UserRole> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(UserRole::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<UserRole> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] roleNames = dbData.split(",");
        return Arrays.stream(roleNames)
                .map(UserRole::valueOf)
                .collect(Collectors.toSet());
    }
}
