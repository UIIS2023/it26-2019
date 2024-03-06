package strategy;

import java.io.FileWriter;
import java.io.IOException;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

public class SaveLog implements SaveToExt{

	private DefaultListModel<String> dlm;
	
	public SaveLog(DefaultListModel<String> dlm) {
		this.dlm=dlm;
	}
	@Override
	public void save() {
		int i=0;
		String fileName;
		try {
            //FileChooser
			JFileChooser j = new JFileChooser("/", FileSystemView.getFileSystemView());
			int r=j.showSaveDialog(null);
			 if (r == JFileChooser.APPROVE_OPTION)
				 
	            {
	                fileName=(j.getSelectedFile().getAbsolutePath());
	            }
	            else
	                return;
	        
			//
			FileWriter writer = new FileWriter(fileName+".txt");
			while(i<dlm.size()) {
				writer.write(dlm.get(i)+"\n");
				 i++;
			}
			writer.close();
			JOptionPane.showMessageDialog(null, "the log has been successfully saved !");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	
		 
		 
		}
		
	}

}
