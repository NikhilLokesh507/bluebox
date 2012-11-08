package package1;

import java.awt.*;

import javax.swing.border.*;

@SuppressWarnings("serial")

public class RoundedLineBorder extends LineBorder{
	public RoundedLineBorder(Color color, int size) {
		super(color, size, true);
	}

	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Color oldColor = g.getColor();

		try{
			g.setColor(lineColor);

			width -= 1;
			height -= 1;for (int i = 0; i < thickness; i++)
			{
				if (roundedCorners)
					g.fillRoundRect(x, y, width, height, thickness, thickness);
				else
					g.fillRect(x, y, width, height);

				x += 1;
				y += 1;
				width -= 2;
				height -= 2;
			}
		}
		
		finally
		{
			g.setColor(oldColor);
		}
	}
}

