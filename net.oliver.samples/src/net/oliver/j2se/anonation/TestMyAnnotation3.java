package net.oliver.j2se.anonation;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

// ��ʵ��עֻ��Ҫ����һ����ע��Ϳ����ˣ�Ȼ���������ʱͨ�������ȡʵ���ı�ע��Ϣ��Ȼ������߼�����
public class TestMyAnnotation3 {

	public static void main(String[] args) {
		
		// �ж����Ƿ��б�ע
        System.out.println("--Class Annotations-- ");
        if (GetMyAnnotation.class.isAnnotationPresent(MyAnnotation3.class )) {
            System.out.println("[GetMyAnnotation].annotation: ");
            // ��ȡ��עʵ��
            MyAnnotation3 classAnnotation = GetMyAnnotation.class.getAnnotation(MyAnnotation3.class );
            printMyAnnotation3(classAnnotation);
        }
        
        // �жϳ�Ա�����Ƿ��б�ע
        System.out.println("--Fields Annotations-- ");
        Field [] fields = GetMyAnnotation.class.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(MyAnnotation3.class )) {
                System.out.println("[GetMyAnnotation. " + field.getName() + "].annotation: ");
                // ��ȡ��עʵ��
                MyAnnotation3 fieldAnnotation = field.getAnnotation(MyAnnotation3.class );
                printMyAnnotation3(fieldAnnotation);
            }
        }
        
        // �жϷ����Ƿ��б�ע
        System.out.println("--Methods Annotations-- ");
        Method[] methods = GetMyAnnotation.class.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println("[GetMyAnnotation. " + method.getName() + "].annotation: ");
            if (method.isAnnotationPresent(MyAnnotation3.class )) {
            	// ��ȡ��עʵ��
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
