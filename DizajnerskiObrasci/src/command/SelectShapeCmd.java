package command;

import geometry.Shape;
import mvc.DrawingModel;

public class SelectShapeCmd implements Command {

	Shape shape;
	private DrawingModel model;
	
	public SelectShapeCmd(Shape shape,DrawingModel model) {
		this.shape=shape;
		this.model=model;
	}

	@Override
	public void execute() {
		int i=model.getShapes().indexOf((Shape)shape);
		model.getShapes().get(i).setSelected(true);		
	}

	@Override
	public void unexecute() {
		int i=model.getShapes().indexOf((Shape)shape);
		model.getShapes().get(i).setSelected(false);
		
	}

	@Override
	public String getName() {
		return "Selected_"+shape.toString();
	}

}
