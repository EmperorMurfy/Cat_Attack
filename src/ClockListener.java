// Class: ClockListener 
// Written by: Cat Attack Developer
// Date: Apr 5, 2024
// Description: called every 5 milliseconds for clock
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
