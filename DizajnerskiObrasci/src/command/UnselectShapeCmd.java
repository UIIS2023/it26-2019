package command;

import geometry.Shape;
import mvc.DrawingModel;

public class UnselectShapeCmd implements Command {

	Shape shape;
	private DrawingModel model;
	
	public UnselectShapeCmd(Shape shape,DrawingModel model) {
		this.shape=shape;
		this.model=model;
	}

	@Override
	public void execute() {
		int i=model.getShapes().indexOf((Shape)shape);
		model.getShapes().get(i).setSelected(false);		
	}

	@Override
	public void unexecute() {
		int i=model.getShapes().indexOf((Shape)shape);
		model.getShapes().get(i).setSelected(true);
		
	}

	@Override
	public String getName() {
		return "Unselected_"+shape.toString();
	}

}
