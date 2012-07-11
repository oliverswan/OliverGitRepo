package net.oliver.j2se.memory;

/**
 * 创建线程导致OOM异常
 * 
 * VM 参数：-Xss2M （这时候不妨设大些）
 *  
 * @author oliver
 * 
 */
public class JavaVMStackOOM {

	private void dontStop() {
		while (true) {
		}
	}

	public void stackLeakByThread() {
		while (true) {
			Thread thread = new Thread(new Runnable() {
				public void run() {
					dontStop();
				}
			});
			thread.start();
		}
	}

	public static void main(String[] args) throws Throwable {
		JavaVMStackOOM oom = new JavaVMStackOOM();
		oom.stackLeakByThread();
	}

}
