package strategy;

public class SaveManager implements SaveToExt{
	
	private SaveToExt saving;
	
	public SaveManager(SaveToExt saving) {
		this.saving=saving;
	}

	@Override
	public void save() {
		saving.save();
	}

}
