package strategy;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileSystemView;

import geometry.Shape;

public class SavePaint implements SaveToExt{
	private ArrayList<Shape> shapes;
	
	public SavePaint(ArrayList<Shape> shapes) {
		this.shapes=shapes;
	}

	@Override
	public void save() {
		String fileName;
		try {
			JFileChooser j = new JFileChooser("/", FileSystemView.getFileSystemView());
			int r=j.showSaveDialog(null);
			 if (r == JFileChooser.APPROVE_OPTION)
				 
	            {
	                fileName=(j.getSelectedFile().getAbsolutePath()+".ser");
	            }
	            else
	                return;
			 File newFile=new File(fileName);
			 FileOutputStream fileOS=new FileOutputStream(newFile);
			 ObjectOutputStream objectOS=new ObjectOutputStream(fileOS);
			 
			 objectOS.writeObject(shapes);
			 objectOS.close();
			 fileOS.close();
			 JOptionPane.showMessageDialog(null, "the paint has been successfully saved !");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
	
		 
		 
		}

		
	}

}
