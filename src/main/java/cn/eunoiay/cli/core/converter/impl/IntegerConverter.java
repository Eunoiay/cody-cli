package cn.eunoiay.cli.core.converter.impl;

import cn.eunoiay.cli.core.converter.TypeConverter;

/**
 * @author Eunoiay
 */
public class IntegerConverter implements TypeConverter<Integer> {

    @Override
    public Integer convert(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new RuntimeException("Cannot convert '" + value + "' to integer", e);
        }
    }
    
}
