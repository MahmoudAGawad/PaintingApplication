
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;

public class OutlineRectangle extends Shape {

	public OutlineRectangle() {
		startPoint = new Point(0, 0);
		endPoint = new Point(0, 0);
		currentColors = new Stack<Color>();
		previousColors = new Stack<Color>();
	}

	final private float dash1[] = { 10.0f };
	final private BasicStroke dashed = new BasicStroke(1.0f,
			BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash1, 0.0f);


	public void draw(Graphics shape) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D) shape;
		g2.setStroke(dashed);

		g2.setColor(color);
		g2.drawLine(startPoint.getX(), startPoint.getY(), startPoint.getX(),
				endPoint.getY());
		g2.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(),
				startPoint.getY());
		g2.drawLine(startPoint.getX(), endPoint.getY(), endPoint.getX(),
				endPoint.getY());
		g2.drawLine(endPoint.getX(), startPoint.getY(), endPoint.getX(),
				endPoint.getY());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
	}

	public boolean isBelongTo(int x, int y) {
		// TODO Auto-generated method stub

		if ((Math.min(startPoint.getX(), endPoint.getX()) <= x && Math.max(
				startPoint.getX(), endPoint.getX()) >= x)
				&& (Math.min(startPoint.getY(), endPoint.getY()) <= y && Math
						.max(startPoint.getY(), endPoint.getY()) >= y))
			return true;
		return false;
	}

}
