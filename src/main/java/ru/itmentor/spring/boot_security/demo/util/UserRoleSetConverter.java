package ru.itmentor.spring.boot_security.demo.util;

import ru.itmentor.spring.boot_security.demo.model.Role;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Converter
public class UserRoleSetConverter implements AttributeConverter<Set<Role>, String> {

    @Override
    public String convertToDatabaseColumn(Set<Role> attribute) {
        if (attribute == null || attribute.isEmpty()) {
            return null;
        }
        return attribute.stream()
                .map(Role::name)
                .collect(Collectors.joining(","));
    }

    @Override
    public Set<Role> convertToEntityAttribute(String dbData) {
        if (dbData == null || dbData.isEmpty()) {
            return null;
        }
        String[] roleNames = dbData.split(",");
        return Arrays.stream(roleNames)
                .map(Role::valueOf)
                .collect(Collectors.toSet());
    }
}
