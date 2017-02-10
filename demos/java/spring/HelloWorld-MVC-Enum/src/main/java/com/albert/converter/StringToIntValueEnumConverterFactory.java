package com.albert.converter;


import com.albert.domain.IntValueEnum;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;

/**
 * @author cyh
 * @Description: 字符串 转 IntVlaueEnum,  字符串可以是 IntVlaueEnum.value IntVlaueEnum.name
 * @date 2016/9/28
 */
public class StringToIntValueEnumConverterFactory implements ConverterFactory<String, IntValueEnum> {

    public <T extends IntValueEnum> Converter<String, T> getConverter(Class<T> targetType) {
        if(!targetType.isEnum()) {
            throw new UnsupportedOperationException("只支持转换到枚举类型");
        }
        return new StringToEnum(targetType);
    }

    private class StringToEnum<T extends IntValueEnum> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnum(Class<T> enumType) {
            this.enumType = enumType;
        }

        public T convert(String source) {
            if (source.length() == 0) {
                // It's an empty enum identifier: reset the enum value to null.
                return null;
            }
            try {
                Integer v = Integer.valueOf(source);
                T[] all = enumType.getEnumConstants();
                for(T t:all) {
                    if(t.getValue() == v) return t;
                }
                return null;
            } catch (NumberFormatException e) {
                T[] all = enumType.getEnumConstants();
                for(T t:all) {
                    if(source.equals(t.getName())) return t;
                }
                return null;
            }
        }
    }

}
