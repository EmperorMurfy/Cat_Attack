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
