package cn.eunoiay.cli.core.option;

import cn.eunoiay.cli.core.registry.TypeConverterRegistry;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

/**
 * @author Eunoiay
 */
@Accessors(fluent = true)
public class OptionSpec {
    
    @Getter
    private final List<String> names;
    
    private final Field field;
    
    @Getter
    private final String defaultValue;
    
    private final int arity;
    
    public OptionSpec(Field field) {
        this.field = field;
        Option option = field.getAnnotation(Option.class);
        if (option == null) {
            throw new IllegalArgumentException("Field " + field + " is not annotated with @Option");
        }
        field.setAccessible(true);
        this.names = Arrays.asList(option.names());
        this.defaultValue = option.defaultValue();
        this.arity = option.arity();
    }
    
    public void setValue(Object instance, String value) throws Exception {
        if (isFlag()) {
            field.set(instance, true);
            return;
        }
        if (value == null) {
            field.set(instance, null);
            return;
        }
        // TODO: 处理基本类型
        if (field.getType().equals(String.class)) {
            field.set(instance, value);
            return;
        }
        Object val = TypeConverterRegistry.get(field.getType()).convert(value);
        field.set(instance, val);
    }
    
    public boolean isFlag() {
        return arity == 0;
    }
    
    public boolean isMultiValue() {
        return arity == -1;
    }

    public void validate(Object instance) throws Exception {
        Object o = field.get(instance);
        if (o == null) {
            if (isFlag()) {
                field.set(instance, false);
                return;
            }
            if (defaultValue != null) {
                setValue(instance, defaultValue);
            }
        }
    }
}
