package command;

import geometry.Rectangle;

public class UpdateRectangleCmd implements Command{

	private Rectangle oldState;
	private Rectangle newState;
	private Rectangle original=new Rectangle();
	
	public UpdateRectangleCmd(Rectangle oldState, Rectangle newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		original=oldState.clone();
		
		oldState.getUpperLeftPoint().setX(newState.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(newState.getUpperLeftPoint().getY());
		oldState.setWidth(newState.getWidth());
		oldState.setHeight(newState.getHeight());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		
	}

	@Override
	public void unexecute() {
		
		oldState.getUpperLeftPoint().setX(original.getUpperLeftPoint().getX());
		oldState.getUpperLeftPoint().setY(original.getUpperLeftPoint().getY());
		oldState.setWidth(original.getWidth());
		oldState.setHeight(original.getHeight());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
		
	}
	
	@Override
	public String getName() {
		return "UpdateRectangle_Old"+original.toString()
	     +"_New"+oldState.toString();
	}

}
