package command;

import geometry.Point;
import mvc.DrawingModel;

public class AddPointCmd implements Command {
	private Point point;
	private DrawingModel model;

	public AddPointCmd(Point point, DrawingModel model) {
		this.point = point;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(point);
	}

	@Override
	public void unexecute() {
		model.remove(point);

	}
	
	@Override
	public String getName() {
		
		return "AddPoint_Point("+point.toString()+",outerColor:"+point.getColor()+")";
	}

}
