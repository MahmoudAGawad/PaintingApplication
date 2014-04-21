
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;

public class Circle extends Shape {
	 
	
	
	public Circle(){
		startPoint=new Point(0, 0);
		endPoint=new Point(0, 0);
		currentColors=new Stack<Color>();
		previousColors=new Stack<Color>();
	}
	
	public Circle(int x1,int y1,double radius){
		startPoint.setX(x1);
		startPoint.setY(y1);
		endPoint.setX(x1+(int)radius);
		endPoint.setY(y1+(int)radius);
	}
	
	
	public void draw(Graphics shape) {
		// TODO Auto-generated method stub
		
		int radius =getRadius();
		endPoint.setX(startPoint.getX());
		endPoint.setY(startPoint.getY()-radius);
		Graphics2D g2 = (Graphics2D)shape;
		g2.setColor(color);
		g2.setStroke(new BasicStroke(3));
//		shape.drawArc(startPoint.getX(), startPoint.getY(), 2 * (int) radius,
//				2 * (int) radius, 0, 360);
		g2.drawOval(startPoint.getX()-radius, startPoint.getY()-radius, 2*radius,2*radius);
		g2.drawLine(startPoint.getX(), startPoint.getY(),startPoint.getX(), startPoint.getY());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		 if(resizing){
				g2.setColor(Color.GRAY);
				
				g2.drawRect(endPoint.getX()-5, endPoint.getY()-5, 10, 10);
			}
		 
	}
	
	
	
	public boolean isBelongTo(int x, int y) {
		// TODO Auto-generated method stub
		x=Math.abs(x-startPoint.getX());
		y=Math.abs(y-startPoint.getY());
		int leftHandSide=x*x+y*y;
		double xDifference = Math.abs(startPoint.getX() - endPoint.getX());
		double yDifference = Math.abs(startPoint.getY() - endPoint.getY());
		int radius =(int)Math.sqrt(xDifference * xDifference + yDifference
				* yDifference);
		return (leftHandSide<=(radius*radius));
	}


}
