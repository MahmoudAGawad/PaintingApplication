
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.Stack;

public class Square extends Shape {
	
	
	public Square(){
		startPoint=new Point(0, 0);
		endPoint=new Point(0, 0);
		currentColors=new Stack<Color>();
		previousColors=new Stack<Color>();
	}
	
	public Square(int x1,int y1,int x2,int y2){
		startPoint.setX(x1);
		startPoint.setY(y1);
		endPoint.setX(x2);
		endPoint.setY(y2);
	}


	
	public void draw(Graphics shape) {
		// TODO Auto-generated method stub
		Graphics2D g2 = (Graphics2D)shape;
		g2.setStroke(new BasicStroke(3));
		int dx=Math.abs(startPoint.getX()-endPoint.getX());
		
		if(endPoint.getY()<startPoint.getY()){
			endPoint.setY(startPoint.getY()-dx);
		}else{
			endPoint.setY(dx+startPoint.getY());
		}
		
		int x=(startPoint.getX()+endPoint.getX())/2;
		int y=(startPoint.getY()+endPoint.getY())/2;
		g2.drawLine(x, y, x, y);
		g2.setColor(color);
		g2.drawLine(startPoint.getX(), startPoint.getY(), startPoint.getX(), endPoint.getY());
		g2.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), startPoint.getY());
		g2.drawLine(startPoint.getX(), endPoint.getY(), endPoint.getX(), endPoint.getY());
		g2.drawLine(endPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		////// Resizing ///////
		
		if(resizing){
			g2.setColor(Color.GRAY);
			g2.drawRect(startPoint.getX()-5, startPoint.getY()-5, 10, 10);
			g2.drawRect(endPoint.getX()-5, endPoint.getY()-5, 10, 10);
			g2.drawRect(startPoint.getX()-5, endPoint.getY()-5, 10, 10);
			g2.drawRect(endPoint.getX()-5, startPoint.getY()-5, 10, 10);
		}
		
		//////////////////////
		
	}

	
	public boolean isBelongTo(int x, int y) {
		// TODO Auto-generated method stub
		if((Math.min(startPoint.getX(), endPoint.getX())<=x
				&&Math.max(startPoint.getX(), endPoint.getX())>=x)
				&&(Math.min(startPoint.getY(), endPoint.getY())<=y
				&&Math.max(startPoint.getY(), endPoint.getY())>=y)) return true;
		return false;
	}


	
}
