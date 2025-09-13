package cn.eunoiay.cli.core.parameter;

import cn.eunoiay.cli.core.registry.TypeConverterRegistry;

import java.lang.reflect.Field;

/**
 * @author Eunoiay
 */
public class ParameterSpec {
    
    private final Field field;
    
    public ParameterSpec(Field field) {
        Parameter parameter = field.getAnnotation(Parameter.class);
        this.field = field;
    }
    
    public void setValue(Object instance, String value) throws Exception {
        if (field.getType().equals(String.class)) {
            field.set(instance, value);
            return;
        }
        // TODO: 处理基本类型
        Object val = TypeConverterRegistry.get(field.getType()).convert(value);
        field.set(instance, val);
    }
    
}
