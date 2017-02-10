### Spring MVC 自定义枚举映射示例

本示例基于 [Spring MVC 注解](https://github.com/albertchendao/demos/java/spring/HelloWorld-MVC) 示例

>注意： 4.0.6.RELEASE 版本的 spring 经试验不支持通过 interface 进行转换, 只能对应的每个枚举编写对应的转换器, 而 4.1.8.RELEASE 可以编写通用的转换器对 interface 进行转换。

#### 转换器

编写通用的转换器, 多个枚举可以实现同一个接口，转换器通过接口进行转换, 转换器实现了 spring 的 `ConverterFactory` 接口。

```
<package com.albert.converter;


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
```

### 注入转换器

在 spring 的 mvc 配置文件中配置:

```
    <bean id="conversionService"
          class="org.springframework.format.support.FormattingConversionServiceFactoryBean">
        <property name="converters">
            <set>
                <bean class="com.albert.converter.StringToIntValueEnumConverterFactory"></bean>
            </set>
        </property>
    </bean>

    <mvc:annotation-driven conversion-service="conversionService" />
```

### 定义枚举接口与枚举

枚举接口

```
package com.albert.domain;

public interface IntValueEnum extends NameEnum {
    int getValue(); /** int 类型的值 **/
}
```

枚举
```
package com.albert.domain;

import com.fasterxml.jackson.annotation.JsonFormat;

@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum SexType implements IntValueEnum {
    男(1, "男"),
    女(0, "女"),
    未知(-1, "未知");
    private int value;
    private String desc;
    SexType(int value, String desc){
        this.value = value;
        this.desc = desc;
    }
    public int getValue() {
        return value;
    }
    public String getDesc() {
        return desc;
    }
    public String getName() {
        return this.name();
    }


    public static SexType of(int value) {
        switch (value) {
            case 0: return 男;
            case 1: return 女;
            case -1: return 未知;
            default: return 未知;
        }
    }
}
```

所有枚举都继承 `IntValueEnum` 接口，这样通用的转换器就可以对所有实现此接口的枚举进行转换。

### 使用

在 controller 里就可以将普通的枚举类型像 string 一样使用

```
	@RequestMapping(value = "print", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> print(UserSex user){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("user", user);
		System.out.println(user);
		return result;
	}
	@RequestMapping(value = "print2", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> print2(SexType sex){
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("sex", sex);
		System.out.println(sex);
		return result;
	}
```

### 枚举 json 输出

spring 默认使用 jackson 输入 json, 会对枚举直接输出其名称字符串，可以通过 `JsonFormat` 注解将枚举当做普通的 object 一样输出其属性。

需要引入 jackson 的包:

```
		<dependency>
			<groupId>com.fasterxml.jackson.core</groupId>
			<artifactId>jackson-databind</artifactId>
			<version>2.7.8</version>
		</dependency>
```

#### 参考链接

1. [官方文档 Validation, Data Binding, and Type Conversion](http://docs.spring.io/spring/docs/current/spring-framework-reference/html/validation.html)
2. [Coverter 与 ConvertorFactory 接口示例](http://blog.csdn.net/qwe6112071/article/details/51056781)


