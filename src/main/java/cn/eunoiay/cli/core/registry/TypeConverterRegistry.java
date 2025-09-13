package cn.eunoiay.cli.core.registry;

import cn.eunoiay.cli.core.converter.TypeConverter;
import cn.eunoiay.cli.core.converter.impl.IntegerConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Eunoiay
 */
public class TypeConverterRegistry {
    
    private static final Map<Class<?>, TypeConverter<?>> converters = new HashMap<>();
    
    private TypeConverterRegistry() {
    }
    
    static {
        init();
    }
    
    public static <T> void register(Class<T> clazz, TypeConverter<T> converter) {
        converters.put(clazz, converter);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> TypeConverter<T> get(Class<T> clazz) {
        return (TypeConverter<T>)converters.get(clazz);
    }
    
    private static void init() {
        register(Integer.class, new IntegerConverter());
    }
    
}
