package command;

import geometry.Shape;
import mvc.DrawingModel;

public class ToBackCmd implements Command{
	Shape shape;
	private DrawingModel model;
	
	public ToBackCmd(Shape shape,DrawingModel model) {
		this.shape=model.get(model.getShapes().indexOf(shape));
		this.model=model;
	}
	
	@Override
	public void execute() {
		
		
		int index=model.getShapes().indexOf(shape);
        if(index==0)
        {
        	//JOptionPane.showMessageDialog(null, "Element is already at the end !");
        	return;
        }
		model.remove(shape);
		model.getShapes().add(index-1, shape);
	}

	@Override
	public void unexecute() {
		int max=model.getShapes().size()-1;
		int index=model.getShapes().indexOf(shape);
        if(index==max)
        {
        	//JOptionPane.showMessageDialog(null, "Element is already at the top !");
        	return;
        }
		model.remove(shape);
		model.getShapes().add(index+1, shape);
		
	}

	@Override
	public String getName() {
		return "toBack_"+shape.toString();
	}
}
