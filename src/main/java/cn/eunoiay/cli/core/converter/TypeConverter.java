package cn.eunoiay.cli.core.converter;

/**
 * @author Eunoiay
 */
public interface TypeConverter<T> {
    
    T convert(String value) throws Exception;
    
}
