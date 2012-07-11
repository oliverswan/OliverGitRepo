package net.oliver.j2se.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class TestSwtObservables {
	
	public static void main(String[] args) {
		Display disp = new Display();
		Shell shell = new Shell(disp);
		
		
		shell.setSize(400,400);
		shell.setLayout(new FillLayout());
		
		
		ScrolledComposite scroll1 = new ScrolledComposite(shell,SWT.NONE);
		Composite a = new Composite(scroll1,SWT.NONE);
		a.setLayout(new FillLayout());
//		
//		ScrolledComposite scroll2= new ScrolledComposite(a,SWT.NONE);
//		Composite b = new Composite(scroll2,SWT.NONE);
//		scroll2.setContent(b);
//		b.setLayout(new FillLayout());
		
		Text t1 = new Text(a,SWT.BORDER);
		t1.setText("xxxxxx");
		
		Text t2 = new Text(a,SWT.BORDER);
		t2.setText("xxxxxx");
		
		scroll1.setContent(a);
		
		
		shell.open();
		while(!shell.isDisposed())
		{
			if(!disp.readAndDispatch())
			{
				disp.sleep();
			}
		}
		
	}
}
