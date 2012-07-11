package net.oliver.j2se.swt;


import org.eclipse.swt.SWT;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.RowLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;

public class TransitPaintTest {

	public static void main(String[] args) {
		Display display = new Display();
		final Shell shell = new Shell(display);
		shell.setLayout(new FillLayout());
		shell.setSize(300, 300);

		shell.addPaintListener(new PaintListener() {

			@Override
			public void paintControl(PaintEvent arg0) {
				System.out.println("shell paint");

			}
		});

//		Composite comp1 = new Composite(shell, SWT.None);
//		comp1.addPaintListener(new PaintListener() {
//
//			@Override
//			public void paintControl(PaintEvent arg0) {
//				System.out.println("comp1 paint");
//			}
//		});
//		comp1.setLayout(new FillLayout());
//		
//		Composite comp2 = new Composite(comp1, SWT.None);
//		comp2.addPaintListener(new PaintListener() {
//
//			@Override
//			public void paintControl(PaintEvent arg0) {
//				System.out.println("comp2 paint");
//			}
//		});
//		comp2.setLayout(new RowLayout());
//		
//		Button btn = new Button(comp2, SWT.None);
//		btn.setText("test");
//		
//		btn.addSelectionListener(new SelectionListener() {
//			
//			@Override
//			public void widgetSelected(SelectionEvent arg0) {
//				System.out.println("xx");
//				shell.redraw();
//			}
//			
//			@Override
//			public void widgetDefaultSelected(SelectionEvent arg0) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		shell.open();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}
}
