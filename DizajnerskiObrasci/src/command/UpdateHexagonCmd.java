package command;

import adapter.HexagonAdapter;

public class UpdateHexagonCmd implements Command{

	private HexagonAdapter oldState;
	private HexagonAdapter newState;
	private HexagonAdapter original=new HexagonAdapter();
	
	public UpdateHexagonCmd(HexagonAdapter oldState, HexagonAdapter newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {

		original=oldState.clone();
		
		oldState.setStartPoint(newState.getStartPoint());
		oldState.setR(newState.getR());
		oldState.setColor(newState.getColor());
		oldState.setInnerColor(newState.getInnerColor());
		
	}

	@Override
	public void unexecute() {
		
		oldState.setStartPoint(original.getStartPoint());
		oldState.setR(original.getR());
		oldState.setColor(original.getColor());
		oldState.setInnerColor(original.getInnerColor());
		
	}
	
	@Override
	public String getName() {
		return "UpdateHexagon_Old"+original.toString()
				+"_New"+oldState.toString();
	}

}
