package command;

import java.util.ArrayList;
import java.util.Iterator;

import geometry.Shape;
import mvc.DrawingModel;

public class RemoveArrayCmd implements Command {
	
	ArrayList<Shape> shapes=new ArrayList<Shape>();;
	private DrawingModel model;
	private StringBuilder sb=new StringBuilder();;
	
	public RemoveArrayCmd(ArrayList<Shape> shapes,DrawingModel model) {
		Iterator<Shape>it=shapes.iterator();
		while(it.hasNext())
		{
			this.shapes.add(it.next());
		}
		this.model=model;
		sb.append("RemoveArray");
	}

	@Override
	public void execute() {
		Iterator<Shape> it=shapes.iterator();
		while(it.hasNext())
		{
			Shape shape=it.next();
			model.remove(shape);
			sb.append("_"+shape.toString());
		}
		
	}

	@Override
	public void unexecute() {

		Iterator<Shape> it=shapes.iterator();
		while(it.hasNext())
		{
			Shape shape=it.next();
			model.add(shape);
			sb.append("_"+shape.toString());
		}
		
	}

	@Override
	public String getName() {
		
       return sb.toString();
	}

}
