package net.oliver.j2se.anonation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// 其实标注只需要定义一个标注类就可以了，然后可在运行时通过反射获取实例的标注信息，然后进行逻辑处理
public class TestMyAnnotation3 {

	public static void main(String[] args) {
		
		// 判断类是否有标注
        System.out.println("--Class Annotations-- ");
        if (GetMyAnnotation.class.isAnnotationPresent(MyAnnotation3.class )) {
            System.out.println("[GetMyAnnotation].annotation: ");
            // 获取标注实例
            MyAnnotation3 classAnnotation = GetMyAnnotation.class.getAnnotation(MyAnnotation3.class );
            printMyAnnotation3(classAnnotation);
        }
        
        // 判断成员变量是否有标注
        System.out.println("--Fields Annotations-- ");
        Field [] fields = GetMyAnnotation.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAnnotation3.class )) {
                System.out.println("[GetMyAnnotation. " + field.getName() + "].annotation: ");
                // 获取标注实例
                MyAnnotation3 fieldAnnotation = field.getAnnotation(MyAnnotation3.class );
                printMyAnnotation3(fieldAnnotation);
            }
        }
        
        // 判断方法是否有标注
        System.out.println("--Methods Annotations-- ");
        Method[] methods = GetMyAnnotation.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("[GetMyAnnotation. " + method.getName() + "].annotation: ");
            if (method.isAnnotationPresent(MyAnnotation3.class )) {
            	// 获取标注实例
                MyAnnotation3 methodAnnotation = method.getAnnotation(MyAnnotation3.class );
                printMyAnnotation3(methodAnnotation);  
            }
        }
    }
    
    private static void printMyAnnotation3(MyAnnotation3 annotation3) {
        if (annotation3 == null ) {
            return;
        }
        
        System.out.println("{value= " + annotation3.value());
        
        String multiValues = "";
        for (String value: annotation3.multiValues()) {
            multiValues += ", " + value;
        }
        
        System.out.println("multiValues= " + multiValues);
        
        System.out.println("number= " + annotation3.number() + "} ");
    }

}
