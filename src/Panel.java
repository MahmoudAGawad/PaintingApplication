import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Panel extends JPanel implements MouseListener, MouseMotionListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Stack<Shape> shapes; // Shapes to be Painted
	private Stack<Shape> Redo;
	private boolean isNewShape; // to know whether the user is to be paint a new
								// Shape
	static int mode; // to know the user current mode (0 for painting mode : 1
						// for edit mode : 2 for move/delete shape : 3 for
						// resize mode)
	private Color nextColor;
	private Color editColor;
	private Shape newShape; 

	public Panel() {
		shapes = new Stack<Shape>();
		Redo = new Stack<Shape>();
		setBackground(Color.WHITE);
		addMouseListener(this);
		addMouseMotionListener(this);
		isNewShape = false;
		mode = 2;
		nextColor = Color.GREEN;
		editColor = Color.GREEN;
	}

	public void paintComponent(Graphics shape) {

		super.paintComponent(shape);
		for (Shape shp : shapes) {
			if (mode == 1)
				shape.setColor(editColor);
			else
				shape.setColor(nextColor);
			shp.draw(shape);

		}

	}

	public Stack<Shape> getShapes() {
		return shapes;
	}

	public void setShapes(Stack<Shape> shp) {
		// System.out.println("Entered");
		shapes = shp;
		mode = 2;
		this.repaint();
	}

	public void addShape(Shape shp) {
		if (isNewShape && shapes.size() > 0) {
			if (newShape.equals(shapes.peek())) {
				shapes.pop(); // to handle if the user click the button more than one time
			}
		}

		if (dashed != null) {
			movingShape.setResizing(false); // disappear the the resizing squares
			shapes.remove(dashed); 
			dashed = null;
		}
		shapes.push(shp);
		newShape = shp;
		shp.addColor(nextColor);
		isNewShape = true;
		mode = 0;
		setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
	}

	public void deleteShape() {
		if (movingShape != null) {
			movingShape.setResizing(false);
			Redo.push(shapes.remove(shapes.indexOf(movingShape))); // delete selected Shape
			
			if (dashed != null) {
				shapes.remove(shapes.indexOf(dashed));
				dashed = null;
			}

			movingShape = null;
			this.repaint();
		}
	}

	public void undo() {
		if (shapes.size() > 0)
			if (shapes.peek().undoColor())
				Redo.push(shapes.pop());

	}

	public void redo() {
		// System.out.println(Redo.size());
		if (shapes.size() > 0) {
			if (shapes.peek().redoColor() && Redo.size() > 0)
				shapes.push(Redo.pop());
		} else if (Redo.size() > 0)
			shapes.push(Redo.pop());

	}

	public void clearRedo() {
		Redo.clear();
		for (Shape shp : shapes)
			shp.clearPrevious();
	}

	public void setNextColor(Color clr) {
		if (mode == 1) {
			editColor = clr;
		} else
			nextColor = clr;
	}

	public Color getNextColor() {
		return nextColor;
	}

	public void setMode(int m) {
		mode = m;
	}

	// //////////// MOUSE LISTNERS //////////////////
	private Shape movingShape = null, dashed = null;

	@Override
	public void mousePressed(MouseEvent event) {
		if (mode == 2 && shapes.size() > 0) // default mode (resize/move/delete)
		{
			int x = event.getX();
			int y = event.getY();

			int area = Integer.MAX_VALUE; // smallest area

			if (dashed != null) { // dashed is already chosen and there is movingShape
				Shape.Point st = dashed.getStartPoint(), end = dashed
						.getEndPoint();

				if (movingShape.getClass().getName()
						.equals(Circle.class.getName())) { // if the movingShape is Circle
					if ((x >= movingShape.getStartPoint().getX() - 10 && x <= movingShape
							.getStartPoint().getX() + 10)
							&& (y >= movingShape.getEndPoint().getY() - 10 && y <= movingShape
									.getEndPoint().getY() + 10)) {
						this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));

						mode = 3; // start resizing the circle
						return;
					}
					
					// else another Shape
				} else if (((x >= st.getX() - 10 && x <= st.getX() + 10) || ((x >= end
						.getX() - 10 && x <= end.getX() + 10)))
						&& ((y >= st.getY() - 10 && y <= st.getY() + 10) || ((y >= end
								.getY() - 10 && y <= end.getY() + 10)))) {
					this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
					int xdiff = Math.abs(end.getX() - st.getX()) ;
					int ydiff = Math.abs(end.getY() - st.getY()) ;

					

					// /// opposite point
					if (movingShape.isBelongTo(x + 22, y + 22)) {
						end.setX(x + xdiff);
						end.setY(y + ydiff);
					} else if (movingShape.isBelongTo(x + 22, y - 22)) {
						end.setX(x + xdiff);
						end.setY(y - ydiff);
					} else if (movingShape.isBelongTo(x - 22, y + 22)) {
						end.setX(x - xdiff);
						end.setY(y + ydiff);
					} else if (movingShape.isBelongTo(x - 22, y - 22)) {
						end.setX(x - xdiff);
						end.setY(y - ydiff);
					}
					
					// x,y is the center of the chosen shape
					
					st.setX(x - xdiff); // resize the shape 
					st.setY(y - ydiff);
					
					mode = 3; // start resizing
					return;
				} else {
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}
				
				// the press was on no shape so the dashed and resizing squares are disappeared
				
				shapes.remove(dashed);
				movingShape.setResizing(false);

			}

			this.repaint();
			
			
			dashed = new OutlineRectangle(); // new dashed for the future selected shape
			movingShape = null; // there's no selected shape yet
			
			
			for (Shape shp : shapes) {

				if (shp.isBelongTo(x, y) && area >= shp.area()) { // choose the smallest area

					movingShape = shp;

					if (shp.getClass().getName().equals(Circle.class.getName())) {
						area = shp.getRadius() * shp.getRadius() * 4;
					} else {
						area = shp.area();
					}
				}
			}
			if (movingShape != null) { // if the press was on an shape's area

				if (movingShape.getClass().getName()
						.equals(Circle.class.getName())) {

					int r = movingShape.getRadius();
					int xx = movingShape.getStartPoint().getX() - r;
					int yy = movingShape.getStartPoint().getY() - r;
					dashed.setStartPoint(xx, yy);
					dashed.setEndPoint(xx + 2 * r, yy + 2 * r);

				} else {
					dashed.setStartPointO(movingShape.getStartPoint());
					dashed.setEndPointO(movingShape.getEndPoint());
				}
				dashed.setColor(Color.BLACK);
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
				
				// draw the dashed and resizing squares around the movingShape
				shapes.push(dashed);
				movingShape.setResizing(true);
				this.repaint();
			} else {
				dashed = null;
			}
		} else if (shapes.size() > 0 || Redo.size() > 0) {
			if (mode == 1) { // if edit mode is active
				int x = event.getX();
				int y = event.getY();
				for (Shape shp : shapes) {
					// System.out.println(shp.getColor().toString());
					if (shp.isBelongTo(x, y)) {
						shp.addColor(editColor);
					}
				}

				this.repaint();

			} else if(mode==0){ // if painting mode is actived

				this.setBackground(Color.LIGHT_GRAY);
				Shape shp = newShape;

				if (!isNewShape) { // if draw similar shape of the last painted
					if (Redo.size() > 0) {
						shp = Redo.firstElement();
					}
					Shape objectOfLastPaintedShape;
					try {
						objectOfLastPaintedShape = shp.getClass().newInstance();
						objectOfLastPaintedShape.setColor(nextColor);
						shapes.push(objectOfLastPaintedShape);
						objectOfLastPaintedShape.setStartPoint(event.getX(),
								event.getY());
						objectOfLastPaintedShape.setEndPoint(event.getX(),
								event.getY());

					} catch (InstantiationException | IllegalAccessException e1) {
						// TODO Auto-generated catch block
						System.out.println("ERROR FOUND");
					}
				} else { // draw new shape

					if (Redo.size() > 0 && newShape.equals(Redo.firstElement()))
						shapes.push(newShape);

					shp.setEndPoint(event.getX(), event.getY());
					shp.setStartPoint(event.getX(), event.getY());
					// System.out.println(nextColor);
					shp.setColor(nextColor);
				}
				clearRedo();
			}

		}
	}

	@Override
	public void mouseDragged(MouseEvent event) {

		PaintingApplication.coordinates.setText("       x = " + event.getX()
				+ "    |    y = " + event.getY());

		PaintingApplication.statueBar.setBackground(Color.YELLOW);

		if (shapes.size() > 0) {
			if (mode == 3 && movingShape != null) { // if resizing mode

				Shape.Point st = dashed.getStartPoint(), end = dashed
						.getEndPoint();

				int x = event.getX(), y = event.getY();
				if (movingShape.getClass().getName()
						.equals(Circle.class.getName())) {
					
					movingShape.setEndPoint(x, y);
					int radius = movingShape.getRadius();
					int xx = movingShape.getStartPoint().getX();
					int yy = movingShape.getStartPoint().getY();
					st.setX(xx - radius);
					st.setY(yy - radius);
					end.setX(xx + radius);
					end.setY(yy + radius);
					// }
				} else {
					st.setX(x); // set start point of the shape
					st.setY(y);
				}
				this.repaint();
			} else if (mode == 2 && movingShape != null) { // if moving mode

				Shape.Point st = movingShape.getStartPoint();
				Shape.Point end = movingShape.getEndPoint();

				int xdiff = (end.getX() - st.getX()) / 2;
				int ydiff = (end.getY() - st.getY()) / 2;
				st.setX(event.getX() - xdiff);
				st.setY(event.getY() - ydiff);
				end.setX(event.getX() + xdiff);
				end.setY(event.getY() + ydiff);

				if (movingShape.getClass().getName()
						.equals(Circle.class.getName())) {
					int r = movingShape.getRadius();
					int xx = movingShape.getStartPoint().getX() - r;
					int yy = movingShape.getStartPoint().getY() - r;
					dashed.setStartPoint(xx, yy);
					dashed.setEndPoint(xx + 2 * r, yy + 2 * r);

				}
				this.repaint();

			} else if (mode == 0) { // if paint mode

				// this.setBackground(Color.PINK);
				Shape shp = shapes.peek();
				PaintingApplication.fromStartPoint.setText("       \u0394x = "
						+ Math.abs(event.getX() - shp.getStartPoint().getX())
						+ "    |    \u0394y = "
						+ Math.abs(event.getY() - shp.getStartPoint().getY()));
				shp.setEndPoint(event.getX(), event.getY());
				this.repaint();
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent event) { 
		PaintingApplication.coordinates.setText("       x = " + event.getX()
				+ "    |    y = " + event.getY());

		
		
		// Mouse Look
		int x = event.getX();
		int y = event.getY();
		boolean f = true;
		for (Shape shp : shapes) {
			
			if (shp.isBelongTo(x, y)) {
				f = false;
				this.setCursor(new Cursor(Cursor.MOVE_CURSOR));
			}
		}
		if (f) {
			this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
		}
		if (mode < 2) {
			setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		} else if (dashed != null) {

			Shape.Point st = dashed.getStartPoint(), end = dashed.getEndPoint();
			if (mode == 2)
				if (((x >= st.getX() - 10 && x <= st.getX() + 10) || ((x >= end
						.getX() - 10 && x <= end.getX() + 10)))
						&& ((y >= st.getY() - 10 && y <= st.getY() + 10) || ((y >= end
								.getY() - 10 && y <= end.getY() + 10)))) {
					this.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
				} else {
					this.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
				}

		}

	}

	@Override
	public void mouseClicked(MouseEvent arg0) {

		this.setBackground(Color.WHITE);
		PaintingApplication.fromStartPoint.setText("");

	}

	@Override
	public void mouseEntered(MouseEvent event) {

	}

	@Override
	public void mouseExited(MouseEvent event) {
		this.setBackground(Color.WHITE);
		PaintingApplication.coordinates.setText("");
		PaintingApplication.fromStartPoint.setText("");

	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
		this.setBackground(Color.WHITE);
		PaintingApplication.statueBar.setBackground(Color.WHITE);
		PaintingApplication.fromStartPoint.setText("");
		isNewShape = false;
		if (mode == 3)
			mode--;
		this.repaint();

	}

}
