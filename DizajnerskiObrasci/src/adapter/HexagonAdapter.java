package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class HexagonAdapter extends SurfaceShape {

	Hexagon hex=new Hexagon(10,10,12);
	
	public HexagonAdapter(){
		hex.setX(0);
		hex.setY(0);
		hex.setR(0);

	}
	
	public HexagonAdapter(Point start,int r){
		hex.setX(start.getX());
		hex.setY(start.getY());
		hex.setR(r);

	}
	public HexagonAdapter(Point start,int r,Color borderColor,Color areaColor){
		this(start,r);
		hex.setBorderColor(borderColor);
		hex.setAreaColor(areaColor);

	}


	@Override
	public void moveBy(int byX, int byY) {
		hex.setX(hex.getX()+byX);
		hex.setY(hex.getY()+byY);
		
	}

	@Override
	public int compareTo(Object o) {
		if(o instanceof HexagonAdapter) {
			return this.area() - ((HexagonAdapter) o).area();
		}
		return 0;
	}
	public int area(){
		int p=6*((hex.getR()*hex.getR()*(int)Math.sqrt(3))/4);
		return p;
	}

	@Override
	public boolean contains(Point p) {
		return hex.doesContain(p.getX(), p.getY());
	}

	@Override
	public void fill(Graphics g) {
		
	}

	@Override
	public boolean contains(int x, int y) {
		return hex.doesContain(x, y);		
	}

	@Override
	public void draw(Graphics g) {
		hex.setSelected(isSelected());
		hex.paint(g);
			
	}
	
	public Point getStartPoint() {
		return new Point(hex.getX(),hex.getY());
	}
	public void setStartPoint(Point startPoint) {
		hex.setX(startPoint.getX());
		hex.setY(startPoint.getY());
	}
	public int getR(){
		return hex.getR();
	}
public void setR(int r){
	hex.setR(r);
}

public Color getColor(){
	return hex.getBorderColor();
}
	public Color getInnerColor(){
		return hex.getAreaColor();
	}
	
	public void setColor(Color color) {
		hex.setBorderColor(color);
	}
	public void setInnerColor(Color color) {
		hex.setAreaColor(color);
	}
	
	public String toString() {
		return "Hexagon(start:"+getStartPoint()+",r:"+hex.getR()+",outerColor:"+getColor()+",innerColor:"+getInnerColor()+")";
	}
	
	public boolean equals(Object obj) {
			if (obj instanceof HexagonAdapter) {
				HexagonAdapter pomocni = (HexagonAdapter) obj;
				if (this.getStartPoint().equals(pomocni.getStartPoint())  && this.getR()==pomocni.getR()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}	
	}
	
	@Override
    public HexagonAdapter clone(){

        HexagonAdapter hexagon = new HexagonAdapter(this.getStartPoint(), this.getR(),this.getColor(),this.getInnerColor());

        return hexagon;

    }
	
	
}
