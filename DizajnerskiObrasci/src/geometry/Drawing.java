package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Drawing extends JPanel {

	public static void main(String[] args) {

		JFrame frame = new JFrame("Drawing");
		frame.setSize(800, 600);
		Drawing drawing = new Drawing();
		frame.getContentPane().add(drawing);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	
	public void paint(Graphics g) {
		
		Point p = new Point(50, 50, true, Color.BLUE);
		// p.draw(g);
		
		Line l = new Line(new Point(80, 50), new Point(80, 230), true, Color.RED);
		// l.draw(g);
		
		Circle c = new Circle(new Point(150, 60), 40, true, Color.BLUE, Color.GREEN);
		// c.draw(g);
		
		Rectangle r = new Rectangle(new Point(200, 130), 50, 80, true, Color.BLACK, Color.RED);
		// r.draw(g);
		
		Donut d = new Donut(new Point(330, 320), 60, 25, true, Color.GRAY, Color.MAGENTA);
		// d.draw(g);
		
		ArrayList<Shape> shapes = new ArrayList<Shape>();
		shapes.add(p);
		shapes.add(l);
		shapes.add(c);
		shapes.add(r);
		shapes.add(d);
		
		Iterator<Shape> it = shapes.iterator();
		while(it.hasNext()) {
			System.out.println("Selected: " + it.next().isSelected());
		}
		
		// iscrtati treci element iz liste shapes
		shapes.get(2).draw(g);
		
		// iscrtati poslednji element iz liste shapes
		shapes.get(shapes.size() - 1).draw(g);
		
		// kreirati, dodati u listu shapes i iscrtati liniju l1 tako da ona bude na 4. mestu u listi shapes
//		Line l1 = new Line(new Point(450, 200), new Point(650, 200), false, Color.BLACK);
//		shapes.add(3, l1);
//		
//		shapes.get(3).draw(g);
		
		// ukloniti drugi element liste shapes
//		shapes.remove(1);
		
		// iscrtati sve elemente iz liste shapes
		for(Shape s : shapes) {
			s.draw(g);
		}
		
		// ispisati samo povrsinske oblike liste shapes
//		for(Shape s : shapes) {
//			if (s instanceof SurfaceShape) {
//				System.out.println(s);
//			}
//		}
		
	}

}
