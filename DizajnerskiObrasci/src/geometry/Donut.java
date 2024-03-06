package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.geom.Area;
import java.awt.geom.Ellipse2D;

public class Donut extends Circle {

	private int innerRadius;
	private Area area;
	
	public Donut() {
		
	}
	
	public Donut(Point center, int radius, int innerRadius) {
		super(center, radius);
		this.innerRadius = innerRadius;
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected) {
		this(center, radius, innerRadius);
		setSelected(selected);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color) {
		this(center, radius, innerRadius,selected);
		this.setColor(color);
	}
	
	public Donut(Point center, int radius, int innerRadius, boolean selected, Color color, Color innerColor) {
		this(center, radius, innerRadius,selected,color);
		this.setInnerColor(innerColor);
	}
	
	@Override
	public int compareTo(Object o) {
		if(o instanceof Donut) {
			return (int) (this.area() - ((Donut) o).area());
		}
		return 0;
	}

	/*@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		super.fill(g);
		g.setColor(Color.LIGHT_GRAY);
		g.fillOval(this.getCenter().getX()-this.innerRadius, this.getCenter().getY()-this.innerRadius,
				this.innerRadius*2, this.innerRadius*2);
	}*/
	
	@Override
	public void draw(Graphics g) {
		Graphics2D gr = (Graphics2D)g;
		
		Shape donut = createDonutShape(this.getCenter().getX(), this.getCenter().getY(), this.getRadius(), (this.getRadius()-this.innerRadius)); 
        gr.setColor(getInnerColor());
        gr.fill(donut);
        gr.setColor(getColor());
        gr.draw(donut);
        
        if (isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-this.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()+this.getRadius()-3, this.getCenter().getY()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()-this.getRadius()-3, 6, 6);
			g.drawRect(this.getCenter().getX()-3, this.getCenter().getY()+this.getRadius()-3, 6, 6);
		}
		/*super.draw(g);
		g.setColor(getColor());
		g.drawOval(this.getCenter().getX()-this.innerRadius, this.getCenter().getY()-this.innerRadius,
				this.innerRadius*2, this.innerRadius*2);*/
	}
	
	private static Shape createDonutShape(double centerX, double centerY, double outerRadius, double thickness)
	    {
	        Ellipse2D outer = new Ellipse2D.Double(
	            centerX - outerRadius, 
	            centerY - outerRadius,
	            outerRadius + outerRadius, 
	            outerRadius + outerRadius);
	        Ellipse2D inner = new Ellipse2D.Double(
	            centerX - outerRadius + thickness, 
	            centerY - outerRadius + thickness,
	            outerRadius + outerRadius - thickness - thickness, 
	            outerRadius + outerRadius - thickness - thickness);
	        Area area = new Area(outer);
	        area.subtract(new Area(inner));
	        return area;
	    }


	
	public double area() {
		return super.area() - innerRadius*innerRadius*Math.PI;
	}
	
	public boolean equals(Object obj) {
		if (obj instanceof Donut) {
			Donut pomocni = (Donut) obj;
			if (this.innerRadius == pomocni.innerRadius &&
					getCenter().equals(pomocni.getCenter()) &&
					getRadius() == pomocni.getRadius()) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
	
	public boolean contains(int x, int y) {
		double dFromCenter = getCenter().distance(x, y);
		return super.contains(x, y) && dFromCenter > innerRadius;
	}
	
	public boolean contains(Point p) {
		double dFromCenter = getCenter().distance(p.getX(), p.getY());
		return super.contains(p.getX(), p.getY()) && dFromCenter > innerRadius;
	}
	
	public int getInnerRadius() {
		return innerRadius;
	}
	public void setInnerRadius(int innerRadius) throws Exception{
		if (innerRadius<this.getRadius()) {
			this.innerRadius = innerRadius;
		} else {
			throw new NumberFormatException("Radius has to be a value greater then 0!");
		}
		
	}
	
	public String toString() {
		//return super.toString() + ", inner radius=" + innerRadius;
		return "Donut(Center=" + getCenter() +",radius=" + getRadius()+",innerRadius=" + innerRadius+",color:"+getColor()+",innerColor:"+getInnerColor() +")";
	}
	
	public Donut clone(){
		Donut original=new Donut();
		original.getCenter().setX(this.getCenter().getX());
		original.getCenter().setY(this.getCenter().getY());
		try {
			original.setRadius(this.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			original.setInnerRadius(this.getInnerRadius());
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		original.setColor(this.getColor());
		original.setInnerColor(this.getInnerColor());
		return original;
	}
	
}
