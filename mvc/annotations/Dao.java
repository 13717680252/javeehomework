package annotations;
import java.lang.annotation.ElementType;
import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.lang.annotation.RetentionPolicy;
@Target(ElementType.TYPE)   
@Retention(RetentionPolicy.RUNTIME)    
@Documented    
@Inherited    
public @interface Dao {  
  
    public String value() default "";  
      
}  