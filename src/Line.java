
import java.awt.*;
//import java.awt.geom.*;
import java.util.Stack;

//import javax.swing.*;
//
//import java.awt.*;
//import java.awt.event.*;

public class Line extends Shape{
	
	public Line(){
		startPoint=new Point(0, 0);
		endPoint=new Point(0, 0);
		currentColors=new Stack<Color>();
		previousColors=new Stack<Color>();
	}
	
	public Line(int x1,int y1,int x2,int y2){
		startPoint.setX(x1);
		startPoint.setY(y1);
		endPoint.setX(x2);
		endPoint.setY(y2);
	}
	
	public void draw(Graphics shape) {
		// TODO Auto-generated method stub
		//shape.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		 Graphics2D g2 = (Graphics2D)shape;
		 g2.setStroke(new BasicStroke(3));
		 g2.setColor(color);
		 
		 g2.drawLine(startPoint.getX(), startPoint.getY(), endPoint.getX(), endPoint.getY());
		 g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

			////// Resizing ///////
			
			if(resizing){
				g2.setColor(Color.GRAY);
				g2.drawRect(startPoint.getX()-5, startPoint.getY()-5, 10, 10);
				g2.drawRect(endPoint.getX()-5, endPoint.getY()-5, 10, 10);
//				g2.drawRect(startPoint.getX()-5, endPoint.getY()-5, 10, 10);
//				g2.drawRect(endPoint.getX()-5, startPoint.getY()-5, 10, 10);
			}
			
			//////////////////////
			
	}

	
	public boolean isBelongTo(int x, int y) {
		// TODO Auto-generated method stub
//		double m1=(1.0*(startPoint.getY()-y))/(startPoint.getX()-x);
//		double m2=(1.0*(endPoint.getY()-y))/(endPoint.getX()-x);
//		return Double.compare(m1, m2)==0;
		
		///////// 1 ///////////////
//		int contain=(int) Line2D. ptLineDist(1.0*startPoint.getX(),1.0* startPoint.getY(), 1.0*endPoint.getX(), 1.0*endPoint.getY(), 1.0*x, 1.0*y);  	
//		 if(contain==0)
//		 {
//			 return true;
//		 }
//		 
//		return false;
		//////////////////////////
		
		/////////// 2 ///////////
		if((Math.min(startPoint.getX(), endPoint.getX())<=x
				&&Math.max(startPoint.getX(), endPoint.getX())>=x)
				&&(Math.min(startPoint.getY(), endPoint.getY())<=y
				&&Math.max(startPoint.getY(), endPoint.getY())>=y)) return true;
		return false;
		////////////////////////
	}
	
	
	
	
	
	
}
