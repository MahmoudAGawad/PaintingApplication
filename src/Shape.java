
//import javax.swing.*;
import java.awt.*;
import java.util.Stack;

public abstract class Shape   {
	
	protected Point startPoint;
	protected Point endPoint;
	protected Color color;
	protected boolean resizing=false;
	protected int radius;
	protected Stack<Color> currentColors,previousColors;
	
	abstract public void draw(Graphics shape);
	abstract public boolean isBelongTo(int x,int y);
	
	
	protected void setResizing(boolean op){
		resizing=op;
	}
	
	
	protected int area(){
		int dx=Math.abs(startPoint.getX()-endPoint.getX());
		int dy=Math.abs(startPoint.getY()-endPoint.getY());
		return dx*dy;
	}
	
	protected boolean undoColor() {
		if(currentColors.size()>1){
			previousColors.push(currentColors.pop()); // 1
			color=currentColors.peek(); // 2
			return false;	
		}
		return true;
	}
	protected boolean redoColor() {
		if(previousColors.size()>0){
			currentColors.push(previousColors.pop());
			color=currentColors.peek();
			return false;
		}
		return true;
	}
	
	protected void clearPrevious(){
		previousColors.clear();
	}
	
	protected void setStartPointO(Point p){
		startPoint=p;
	}
	
	protected void setEndPointO(Point p){
		endPoint=p;
	}
	
	protected void setRadius(int r){
		radius=r;
	}
	
	protected int getRadius(){
		double xDifference = Math.abs(startPoint.getX() - endPoint.getX());
		double yDifference = Math.abs(startPoint.getY() - endPoint.getY());
		int radius =(int)Math.sqrt(xDifference * xDifference + yDifference
				* yDifference);
		return this.radius=radius;
	}
	
	protected void setStartPoint(int x,int y){
		startPoint.setX(x);
		startPoint.setY(y);
	}
	protected void setEndPoint(int x,int y){
		endPoint.setX(x);
		endPoint.setY(y);
	}
	protected void setColor(Color clr){
		color=clr;
	}
	
	protected void addColor(Color clr){
		currentColors.push(clr);
		color=clr;
	}
	
	
	public Color getColor(){
		return color;
	}
	
	public Point getStartPoint(){
		return startPoint;
	}
	
	public Point getEndPoint(){
		return endPoint;
	}
	
	protected class Point{
		private int X,Y;
		public Point(int xx,int yy){
			X=xx;
			Y=yy;
		}
		public void setX(int x){X=x;}
		public void setY(int y){Y=y;}
		public int getX(){return X;}
		public int getY(){return Y;}
	}
	
	
}
