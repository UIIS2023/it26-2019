package command;

import adapter.HexagonAdapter;
import mvc.DrawingModel;

public class AddHexagonCmd implements Command {

	private HexagonAdapter hex;
	private DrawingModel model;

	public AddHexagonCmd(HexagonAdapter hex, DrawingModel model) {
		this.hex = hex;
		this.model = model;
	}

	@Override
	public void execute() {
		model.add(hex);

	}

	@Override
	public void unexecute() {
		model.remove(hex);

	}
	
	@Override
	public String getName() {
		
		return "AddHexagon_"+hex.toString();
	}

}
