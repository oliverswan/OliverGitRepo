package net.oliver.j2se.anonation;


@MyAnnotation3(value = "Class GetMyAnnotation ", multiValues = {"1 ","2 "})
public class GetMyAnnotation {
	
    @MyAnnotation3(value = "call testField1 ", multiValues={"1 "}, number = 1)
    private String testField1;

    // ΪtestMethod1��������MyAnnotation3 ע��
    @MyAnnotation3(value = "call testMethod1 ", multiValues={"1 ", "2 "}, number = 1)
    public void testMethod1() {

    }

    @Deprecated
    @MyAnnotation3(value = "call testMethod2 ", multiValues={"3 ", "4 ", "5 "}) 
    public void testMethod2() {

    }
}

