package net.oliver.j2se.multithread.deadlock;

public class Deadlocker {

	int field_1;
	private Object lock_1 = new int[1];
	int field_2;
	private Object lock_2 = new int[1];

	public void method1(int value) {
		synchronized (lock_1) {
			Thread.currentThread().yield();
			synchronized (lock_2) {
				field_1 = 0;
				field_2 = 0;
			}
		}
	}

	public void method2(int value) {
		synchronized (lock_2) {
			Thread.currentThread().yield();
			synchronized (lock_1) {
				field_1 = 0;
				field_2 = 0;
			}
		}
	}
	
	public static void main(String[] args)
	{
		final Deadlocker deadlocker = new Deadlocker();
		
		
		for(int i=0;i<10;i++)
		{
			Thread t1 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					deadlocker.method1(1);
				}
			});
			t1.setName("线程A"+i);
			t1.start();
			
			
			Thread t2 = new Thread(new Runnable() {
				
				@Override
				public void run() {
					deadlocker.method2(1);
				}
			});
			t2.setName("线程B"+i);
			t2.start();
			
		}
	}

}
