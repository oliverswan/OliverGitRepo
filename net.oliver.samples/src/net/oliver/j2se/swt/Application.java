package net.oliver.j2se.swt;

import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class Application {

	public static void main( String[] args )
	  {
	    Display display = Display.getDefault();
	    Shell shell = new Shell( display );
	    init( shell );

	    shell.pack();
	    shell.open();
	    while ( !shell.isDisposed() )
	    {
	      if ( !display.readAndDispatch() )
	        display.sleep();
	    }
	  }


	  private static void init( Shell shell )
	  {
	    shell.setText( "Owner Draw Button Test" );
	    FillLayout layout = new FillLayout();
	    layout.marginWidth = layout.marginHeight = 8;
	    shell.setLayout( layout );

	    Button btn = new TestButton( shell );
	    btn.setText( "Owner Draw Button" );
	    btn.setToolTipText( "Hello, I'm a OwnerDraw Button!" );
	  }

}
