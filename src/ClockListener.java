// Class: ClockListener
// Written by: Mr. Swope
// Date: 1/27/2020
// Description: This class provides the implementation for a ClockListener. You probably won't want to modify this
//              class. The class will call the clock method in your GraphicsPanel class every 5 milliseconds.
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ClockListener implements ActionListener {

	GraphicsPanel f;

	ClockListener(GraphicsPanel c)
	{
		f = c;
	}

	public void actionPerformed(ActionEvent e) {
		f.clock();
	}

}
