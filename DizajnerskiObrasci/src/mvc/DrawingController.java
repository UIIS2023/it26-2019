package mvc;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.ListIterator;
import java.util.Optional;

import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;

import Drawing.DlgCircle;
import Drawing.DlgDonut;
import Drawing.DlgHexagon;
import Drawing.DlgLine;
import Drawing.DlgPoint;
import Drawing.DlgRectangle;
import adapter.HexagonAdapter;
import command.AddCircleCmd;
import command.AddDonutCmd;
import command.AddHexagonCmd;
import command.AddLineCmd;
import command.AddPointCmd;
import command.AddRectangleCmd;
import command.BringToBackCmd;
import command.BringToFrontCmd;
import command.Command;
import command.SelectShapeCmd;
import command.ToBackCmd;
import command.ToFrontCmd;
import command.UnselectShapeCmd;
import command.UpdateCircleCmd;
import command.UpdateDonutCmd;
import command.UpdateHexagonCmd;
import command.UpdateLineCmd;
import command.UpdatePointCmd;
import command.UpdateRectangleCmd;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import strategy.SaveLog;
import strategy.SaveManager;
import strategy.SavePaint;
import command.QueueStack;
import command.RemoveArrayCmd;


public class DrawingController {
	private DrawingModel model;
	private DrawingFrame frame;
	private Point startPoint;
	private boolean flag=false;
	private PropertyChangeSupport propertyChangeSupport;
	private ArrayList<String> log = new ArrayList<String>();

	public DrawingController(DrawingModel model, DrawingFrame frame) {
		this.model = model;
		this.frame = frame;
		propertyChangeSupport=new PropertyChangeSupport(this);
        queueStackNormal = new QueueStack();
        queueStackReverse = new QueueStack();
	}

	public void mouseClicked(MouseEvent e) {
		Color color=frame.btnColor.getBackground();
		Color innerColor=frame.btnInnerColor.getBackground();
		if(frame.getTglbtnSelection().isSelected()){			
			ListIterator<Shape> it=model.getShapes().listIterator(model.getShapes().size());
			while(it.hasPrevious())
			{
				Shape shape=it.previous();
				System.out.println(shape.toString());
				if(shape.contains(e.getX(), e.getY()))
				{
					if(shape.isSelected())
					{
						UnselectShapeCmd unselectShapeCmd=new UnselectShapeCmd(shape,model);
						execute(unselectShapeCmd);
						setDeleteBtnEnabled();
						setModifyBtnEnabled();
						flag=true;
						frame.repaint();
						break;
					}else {
					SelectShapeCmd selectShapeCmd=new SelectShapeCmd(shape,model);
					execute(selectShapeCmd);
					setDeleteBtnEnabled();
					setModifyBtnEnabled();
					flag=true;
					frame.repaint();
					break;
					}
				}
			}
			if(flag==false) {
				emptyTheList();
			}
			flag=false;
			}else if(frame.getTglbtnPoint().isSelected())
			{
				emptyTheList();
				Point p=new Point(e.getX(),e.getY(),false,color);
				AddPointCmd addPointCmd = new AddPointCmd(p, model);
				execute(addPointCmd);
				clearReverse();
			}else if(frame.getTglbtnLine().isSelected()){
				emptyTheList();
				if(startPoint==null)
					startPoint=new Point(e.getX(),e.getY());
				else{
					Line l=new Line(startPoint,new Point(e.getX(),e.getY()),false,color);
					AddLineCmd addLineCmd=new AddLineCmd(l,model);
					execute(addLineCmd);
					clearReverse();
					startPoint=null;
				}
			}else if(frame.getTglbtnRectangle().isSelected()){
				emptyTheList();
				DlgRectangle dlg=new DlgRectangle();
				dlg.setModal(true);
				dlg.setRectangle(new Rectangle(new Point(e.getX(),e.getY()),-1,-1,false,color,innerColor));
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				Rectangle r=null;
				try{
					r=dlg.getRectangle();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "Wrong data type","Vukasin Stanisic IT26",JOptionPane.ERROR_MESSAGE);
				}
				if(r!=null){
					AddRectangleCmd addRectangleCmd=new AddRectangleCmd(r,model);
					execute(addRectangleCmd);
					clearReverse();
				}
			}else if(frame.getTglbtnHexagon().isSelected()){
				emptyTheList();
				DlgHexagon dlg=new DlgHexagon();
				dlg.setModal(true);
				dlg.setHexagon(new HexagonAdapter(new Point(e.getX(),e.getY()),-1,color,innerColor));
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				HexagonAdapter h=null;
				try{
					h=dlg.getHexagon();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "Wrong data type","Vukasin Stanisic IT26",JOptionPane.ERROR_MESSAGE);
				}
				if(h!=null){
					AddHexagonCmd addHexagonCmd=new AddHexagonCmd(h,model);
					execute(addHexagonCmd);
					clearReverse();
				}
			}else if(frame.getTglbtnCircle().isSelected()){
				emptyTheList();
				DlgCircle dlg=new DlgCircle();
				dlg.setModal(true);
				dlg.setCircle(new Circle(new Point(e.getX(),e.getY()),-1,false,color,innerColor));
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				Circle c=null;
				try{
					c=dlg.getCircle();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "Wrong data type","Vukasin Stanisic IT26",JOptionPane.ERROR_MESSAGE);
				}
				if(c!=null){
				AddCircleCmd addCircleCmd=new AddCircleCmd(c,model);
				execute(addCircleCmd);
				clearReverse();
				}
			}else if(frame.getTglbtnDonut().isSelected()){
				emptyTheList();
				DlgDonut dlg=new DlgDonut();
				dlg.setModal(true);
				dlg.setDonut(new Donut(new Point(e.getX(),e.getY()),-1,-1,false,color,innerColor));
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				Donut d=null;
				try{
					d=dlg.getDonut();
				}catch(Exception ex){
					JOptionPane.showMessageDialog(frame, "Wrong data type","Vukasin Stanisic IT26",JOptionPane.ERROR_MESSAGE);
				}
				if(d!=null){
					AddDonutCmd addDonutCmd=new AddDonutCmd(d,model);
					execute(addDonutCmd);
					clearReverse();
				}
			}
			
				frame.repaint();
	}
	public ArrayList<Shape> getSelected(){
		ArrayList<Shape> selected = new ArrayList<Shape>();
		Iterator<Shape> it=model.getShapes().iterator();
		while(it.hasNext())
		{
			Shape shape=it.next();
			if(shape.isSelected())
				selected.add(shape);
			
		}
		return selected;
}
	/*public void deleteSelected(){
		this.selected=null;
	}*/
	
	public void modify() throws Exception{
		ArrayList<Shape> sel=getSelected();
		Shape selected=sel.get(0);
		if(selected!=null){
			if(selected instanceof Point){
				Point point=(Point)selected;
				DlgPoint dlg=new DlgPoint();
				dlg.setPoint(point);
				dlg.setModal(true);
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
        		UpdatePointCmd updatePointCmd = new UpdatePointCmd(point, dlg.getPoint());
        		execute(updatePointCmd);
        		clearReverse();
				
			}else if(selected instanceof Line){
				Line line=(Line)selected;
				DlgLine dlg=new DlgLine();
				dlg.setLine(line);
				dlg.setModal(true);
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				UpdateLineCmd updateLineCmd = new UpdateLineCmd(line, dlg.getLine());
				execute(updateLineCmd);
				clearReverse();
			}else if(selected instanceof Rectangle){
				Rectangle rec=(Rectangle)selected;
				DlgRectangle dlg=new DlgRectangle();
				dlg.setRectangle(rec);
				dlg.setModal(true);
				dlg.setVisible(true);
				if(!dlg.isCommited())
					return;
				UpdateRectangleCmd updateRectangleCmd = new UpdateRectangleCmd(rec, dlg.getRectangle());
				execute(updateRectangleCmd);
				clearReverse();
		}else if(selected instanceof HexagonAdapter){
			HexagonAdapter hex=(HexagonAdapter)selected;
			DlgHexagon dlg=new DlgHexagon();
			dlg.setHexagon(hex);
			dlg.setModal(true);
			dlg.setVisible(true);
			if(!dlg.isCommited())
				return;
			UpdateHexagonCmd updateHexagonCmd = new UpdateHexagonCmd(hex, dlg.getHexagon());
			execute(updateHexagonCmd);
			clearReverse();
	}else if(selected instanceof Donut){
			Donut d=(Donut)selected;
			DlgDonut dlg=new DlgDonut();
			dlg.setDonut(d);
			dlg.setModal(true);
			dlg.setVisible(true);
			if(!dlg.isCommited())
				return;
			UpdateDonutCmd updateDonutCmd = new UpdateDonutCmd(d, dlg.getDonut());
			execute(updateDonutCmd);
			clearReverse();
	}else if(selected instanceof Circle){
			Circle c=(Circle)selected;
			DlgCircle dlg=new DlgCircle();
			dlg.setCircle(c);
			dlg.setModal(true);
			dlg.setVisible(true);
			if(!dlg.isCommited())
				return;
			UpdateCircleCmd updateCircleCmd = new UpdateCircleCmd(c, dlg.getCircle());
			execute(updateCircleCmd);
			clearReverse();
	}
}frame.repaint();
}
	
	public void delete(){
		int reply=JOptionPane.showConfirmDialog(null,"Are you sure you want to delete this?","Delete?",JOptionPane.YES_NO_OPTION);
		if(reply==JOptionPane.YES_OPTION)
		{
		RemoveArrayCmd removeArrayCmd=new RemoveArrayCmd(getSelected(),model);
		execute(removeArrayCmd);
		clearReverse();
		setDeleteBtnEnabled();
		setModifyBtnEnabled();
		frame.repaint();
		}
		
			
		
	}
	
	private void emptyTheList() {
		if(getSelected().isEmpty())
			return;
		Iterator<Shape> it=model.getShapes().iterator();
		while(it.hasNext())
		{
			Shape shape=it.next();
			shape.setSelected(false);
		}
		propertyChangeSupport.firePropertyChange("delete", false, null);
		propertyChangeSupport.firePropertyChange("modify", false, null);
		frame.repaint();
		print("Unselected_All");
	}
	
	public void addPropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.addPropertyChangeListener(propertyChangeListener);
	}

	public void removePropertyChangeListener(PropertyChangeListener propertyChangeListener) {
		propertyChangeSupport.removePropertyChangeListener(propertyChangeListener);
	}
	
	private void setDeleteBtnEnabled() {
		boolean signal=!getSelected().isEmpty();
		propertyChangeSupport.firePropertyChange("delete", signal, null);
	}
	private void setModifyBtnEnabled() {
		boolean signal=getSelected().size()==1;
		propertyChangeSupport.firePropertyChange("modify", signal, null);
	}
	
	
	
	//ToFront,ToBack...
	
	public void toFront() {
		Shape shape=getSelected().get(0);
		ToFrontCmd toFrontCmd=new ToFrontCmd(shape,model);
    	execute(toFrontCmd);
		frame.repaint();
	}
	
	public void toBack() {
		Shape shape=getSelected().get(0);
		ToBackCmd toBackCmd=new ToBackCmd(shape,model);
    	execute(toBackCmd);
		frame.repaint();
	}
	
    public void bringToBack() {
    	Shape shape=getSelected().get(0);
		BringToBackCmd bringToBackCmd=new BringToBackCmd(shape,model);
		execute(bringToBackCmd);
		frame.repaint();
	}

    public void bringToFront() {
    	Shape shape=getSelected().get(0);
    	BringToFrontCmd bringToFrontCmd=new BringToFrontCmd(shape,model);
    	execute(bringToFrontCmd);
    	frame.repaint();
}
    
    // Colors
    
    public void borderColorClicked() {
			JColorChooser obj=new JColorChooser();
			Object a=obj;
			int b=JOptionPane.showConfirmDialog(null, a,"Vukasin Stanisic IT26",JOptionPane.OK_CANCEL_OPTION);
			if(b==0)
			{
				frame.btnColor.setBackground(obj.getColor());
				print("borderColor_setTo_"+obj.getColor());
			}
				
    }

public void innerColorClicked() {
		JColorChooser obj=new JColorChooser();
		Object a=obj;
		int b=JOptionPane.showConfirmDialog(null, a,"Vukasin Stanisic IT26",JOptionPane.OK_CANCEL_OPTION);
		if(b==0)
		{
			frame.btnInnerColor.setBackground(obj.getColor());
			print("innerColor_setTo_"+obj.getColor());
		}
			
}

//Undo - Redo
private QueueStack queueStackNormal;
private QueueStack queueStackReverse;

private void execute(Command command){
    command.execute();
     queueStackNormal.push(command);
     setUndoBtnEnabled();
     setRedoBtnEnabled();
     setDeleteBtnEnabled();
	 setModifyBtnEnabled();
     print(command.getName());
 }

public void undo() {
     Optional<Command> optionalCommand = queueStackNormal.pop();
     optionalCommand.ifPresent(c -> {
         c.unexecute();
         queueStackReverse.push(c);
         setUndoBtnEnabled();
         setRedoBtnEnabled();
         setDeleteBtnEnabled();
		setModifyBtnEnabled();
         print("undo_"+c.getName());
        // aList.forEach(a -> actionHistory.add(a.getName() + " - undo"));
     });
 }

public void redo() {
     Optional<Command> optionalCommand = queueStackReverse.pop();
     optionalCommand.ifPresent(c -> {
        c.execute();
         queueStackNormal.push(c);
         setUndoBtnEnabled();
         setRedoBtnEnabled();
         setDeleteBtnEnabled();
		 setModifyBtnEnabled();
         print("redo_"+c.getName());
        // aList.forEach(a -> actionHistory.add(a.getName() + " - redo"));
     });
 }

private void clearNormal() {
	    propertyChangeSupport.firePropertyChange("undo", false, null);
     queueStackNormal.clear();
 }

private void clearReverse() {
	   propertyChangeSupport.firePropertyChange("redo", false, null);
	   queueStackReverse.clear();
 }

private void setUndoBtnEnabled() {
	boolean signal=!queueStackNormal.isEmpty();
	propertyChangeSupport.firePropertyChange("undo", signal, null);
}
private void setRedoBtnEnabled() {
	boolean signal=!queueStackReverse.isEmpty();
	propertyChangeSupport.firePropertyChange("redo", signal, null);
}

// print
public void print(String s)
{
	int max=frame.dlm.size();
	frame.dlm.add(max, s);
}

// Saving external

public void saveLogClicked() {
	SaveLog saveLog=new SaveLog(frame.dlm);
	SaveManager manager=new SaveManager(saveLog);
	manager.save();
}

public void savePaintClicked() {
	SavePaint savePaint=new SavePaint(model.getShapes());
	SaveManager manager=new SaveManager(savePaint);
	manager.save();
}
    
//Reading paint
public void loadTheDrawing() throws ClassNotFoundException {
	String fileName;
	try {
		JFileChooser j = new JFileChooser("/", FileSystemView.getFileSystemView());
		FileNameExtensionFilter filter=new FileNameExtensionFilter(".ser","ser");
		j.setFileFilter(filter);
		int r=j.showSaveDialog(null);
		 if (r == JFileChooser.APPROVE_OPTION)
			 
            {
			 File choice=j.getSelectedFile();
			 FileInputStream fileIS=new FileInputStream(choice);
			 ObjectInputStream objectIS=new ObjectInputStream(fileIS);
			 model.getShapes().clear();
			 clearNormal();
			 clearReverse();
			 emptyTheList();
			 frame.dlm.clear();
			 ArrayList<Shape> paint=(ArrayList<Shape>)objectIS.readObject();
			 for(Shape shape:paint)
			 {
				 model.add(shape);
			 }
			 frame.repaint();
			 objectIS.close();
			 fileIS.close();
            }
            else
                return;

	} catch (IOException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
		JOptionPane.showMessageDialog(null, "Greška !");

	 
	 
	}

	
}

// Loading draw from log
public void loadDrawFromLog() throws IOException {
	log = new ArrayList<String>();
	String fileName;
	JFileChooser j = new JFileChooser("/", FileSystemView.getFileSystemView());
	FileNameExtensionFilter filter=new FileNameExtensionFilter(".txt","txt");
	j.setFileFilter(filter);
	int r=j.showSaveDialog(null);
	 if (r == JFileChooser.APPROVE_OPTION)
	 {
		 File choice=j.getSelectedFile();
		 if(choice.getName().toLowerCase().endsWith(".txt"))
		 {
			 fileName=(j.getSelectedFile().getAbsolutePath());
			 
				BufferedReader bufReader = new BufferedReader(new FileReader(fileName)); 
				String line = bufReader.readLine(); 
				while (line != null) 
				{
					log.add(line); 
					line = bufReader.readLine(); 
				} 
				
				bufReader.close();
				 model.getShapes().clear();
				 clearNormal();
				 clearReverse();
				 emptyTheList();
				 frame.dlm.clear();
				 unabledButtons(false);		
				 frame.repaint();
		 } else {
			 JOptionPane.showMessageDialog(null, "Greška !");
		 }
         
     }


}

public void extractCommands()
{
	int len=log.size();
	if(len==0)
	{
		frame.btnLoadNext.setEnabled(false);
	}else {
		String l=log.remove(0);
		String[] niz=l.split("_");
		try {
		
		//LOG:ADD
		if(niz[0].equals("AddLine"))
		{
			System.out.println("u ifu sam");
			Line line=extractLine(niz[1]);
			AddLineCmd addLineCmd=new AddLineCmd(line,model);
			execute(addLineCmd);
			frame.repaint();
			
		}
		else if(niz[0].equals("AddPoint")) {
			
			Point point=extractPoint(niz[1]);
			AddPointCmd addPointCmd=new AddPointCmd(point,model);
			execute(addPointCmd);
			frame.repaint();
			
		}else if(niz[0].equals("AddCircle")) {
			
			System.out.println("u circle ifu sam");
			Circle circle=extractCircle(niz[1]);
			AddCircleCmd addCircleCmd=new AddCircleCmd(circle,model);
			execute(addCircleCmd);
			frame.repaint();
		}else if(niz[0].equals("AddDonut")) {
			
			System.out.println("u donut ifu sam");
			Donut donut=extractDonut(niz[1]);
			AddDonutCmd addDonutCmd=new AddDonutCmd(donut,model);
			execute(addDonutCmd);
			frame.repaint();
		}else if(niz[0].equals("AddHexagon")) {
			
			System.out.println("u hexagon ifu sam");
			HexagonAdapter hex=extractHexagon(niz[1]);
			AddHexagonCmd addHexagonCmd=new AddHexagonCmd(hex,model);
			execute(addHexagonCmd);
			frame.repaint();
		}else if(niz[0].equals("AddRectangle")) {
			
			System.out.println("u rectangle ifu sam");
			Rectangle rec=extractRectangle(niz[1]);
			AddRectangleCmd addRectangleCmd=new AddRectangleCmd(rec,model);
			execute(addRectangleCmd);
			frame.repaint();
		}else if(niz[0].equals("borderColor")) {
			
			System.out.println("u borderColor ifu sam");
			Color color=extractBorder(niz[2]);
            frame.btnColor.setBackground(color);
			
			
		}else if(niz[0].equals("innerColor")) {
			
			System.out.println("u innerColor ifu sam");
			Color color=extractBorder(niz[2]);
            frame.btnInnerColor.setBackground(color);
			
			
		}//LOG:SELECTED
		else if(niz[0].equals("Selected")) {
			System.out.println(niz[1]);
			String[] niz2=niz[1].split("\\(");
			System.out.println(niz2[0]);
			if(niz2[0].equals("Line"))
		{
			System.out.println("u selectLine ifu sam");
			Line line=extractLine(niz[1]);
			SelectShapeCmd selectLineCmd=new SelectShapeCmd(line,model);
			execute(selectLineCmd);
			frame.repaint();
			
			
		}
		else if(niz2[0].equals("Point")) {
			
			System.out.println("u selectPoint ifu sam");
			Point point=extractPoint(niz[1]);
			SelectShapeCmd selectPointCmd=new SelectShapeCmd(point,model);
			execute(selectPointCmd);
			frame.repaint();
			
		}else if(niz2[0].equals("Circle")) {
			
			System.out.println("u selectCircle ifu sam");
			Circle circle=extractCircle(niz[1]);
			SelectShapeCmd selectCircleCmd=new SelectShapeCmd(circle,model);
			execute(selectCircleCmd);
			frame.repaint();
		}else if(niz2[0].equals("Donut")) {
			
			System.out.println("u selectDonut ifu sam");
			Donut donut=extractDonut(niz[1]);
			SelectShapeCmd selectDonutCmd=new SelectShapeCmd(donut,model);
			execute(selectDonutCmd);
			frame.repaint();
		}else if(niz2[0].equals("Hexagon")) {
			
			System.out.println("u selecthexagon ifu sam");
			HexagonAdapter hex=extractHexagon(niz[1]);
			SelectShapeCmd selectHexagonCmd=new SelectShapeCmd(hex,model);
			execute(selectHexagonCmd);
			frame.repaint();
		}else if(niz2[0].equals("Rectangle")) {
			
			System.out.println("u selectrectangle ifu sam");
			Rectangle rec=extractRectangle(niz[1]);
			SelectShapeCmd selectRectangleCmd=new SelectShapeCmd(rec,model);
			execute(selectRectangleCmd);
			frame.repaint();
		}
		//LOG:UNSELECTED
	}else if(niz[0].equals("Unselected")) {
		String[] niz2=niz[1].split("\\(");
		if(niz2[0].equals("Line"))
	{
		System.out.println("u unselectLine ifu sam");
		Line line=extractLine(niz[1]);
		UnselectShapeCmd unselectLineCmd=new UnselectShapeCmd(line,model);
		execute(unselectLineCmd);
		frame.repaint();
		
	}
	else if(niz2[0].equals("Point")) {
		
		System.out.println("u unselectPoint ifu sam");
		Point point=extractPoint(niz[1]);
		UnselectShapeCmd unselectPointCmd=new UnselectShapeCmd(point,model);
		execute(unselectPointCmd);
		frame.repaint();
		
	}else if(niz2[0].equals("Circle")) {
		
		System.out.println("u unselectCircle ifu sam");
		Circle circle=extractCircle(niz[1]);
		UnselectShapeCmd unselectCircleCmd=new UnselectShapeCmd(circle,model);
		execute(unselectCircleCmd);
		frame.repaint();
	}else if(niz2[0].equals("Donut")) {
		
		System.out.println("u unselectDonut ifu sam");
		Donut donut=extractDonut(niz[1]);
		UnselectShapeCmd unselectDonutCmd=new UnselectShapeCmd(donut,model);
		execute(unselectDonutCmd);
		frame.repaint();
	}else if(niz2[0].equals("Hexagon")) {
		
		System.out.println("u unselecthexagon ifu sam");
		HexagonAdapter hex=extractHexagon(niz[1]);
		UnselectShapeCmd unselectHexagonCmd=new UnselectShapeCmd(hex,model);
		execute(unselectHexagonCmd);
		frame.repaint();
	}else if(niz2[0].equals("Rectangle")) {
		
		System.out.println("u unselectrectangle ifu sam");
		Rectangle rec=extractRectangle(niz[1]);
		UnselectShapeCmd unselectRectangleCmd=new UnselectShapeCmd(rec,model);
		execute(unselectRectangleCmd);
		frame.repaint();
	}else if(niz2[0].equals("All"))
	{
		emptyTheList();
		frame.repaint();
	}
	
}
		//LOG:UPDATE
	else if(niz[0].equals("UpdateLine"))
		{
			Line oldLine=extractLine(niz[1]);
			int index=model.getShapes().indexOf((Shape)oldLine);
			oldLine=(Line)model.get(index);
			Line newLine=extractLine(niz[2]);
			UpdateLineCmd updateLineCmd=new UpdateLineCmd(oldLine,newLine);
			execute(updateLineCmd);
			frame.repaint();
			
		}
		else if(niz[0].equals("UpdatePoint")) {
			
			Point oldPoint=extractPoint(niz[1]);
			int index=model.getShapes().indexOf((Shape)oldPoint);
			oldPoint=(Point)model.get(index);
			Point newPoint=extractPoint(niz[2]);
			UpdatePointCmd updatePointCmd=new UpdatePointCmd(oldPoint,newPoint);
			execute(updatePointCmd);
			frame.repaint();
			
		}else if(niz[0].equals("UpdateCircle")) {
			
			Circle oldCircle=extractCircle(niz[1]);
			int index=model.getShapes().indexOf((Shape)oldCircle);
			oldCircle=(Circle)model.get(index);
			Circle newCircle=extractCircle(niz[2]);
			UpdateCircleCmd updateCircleCmd=new UpdateCircleCmd(oldCircle,newCircle);
			execute(updateCircleCmd);
			frame.repaint();
		}else if(niz[0].equals("UpdateDonut")) {
			
			Donut oldDonut=extractDonut(niz[1]);
			int index=model.getShapes().indexOf((Shape)oldDonut);
			oldDonut=(Donut)model.get(index);
			Donut newDonut=extractDonut(niz[2]);
			UpdateDonutCmd updateDonutCmd=new UpdateDonutCmd(oldDonut,newDonut);
			execute(updateDonutCmd);
			frame.repaint();
		}else if(niz[0].equals("UpdateHexagon")) {			
			HexagonAdapter oldHex=extractHexagon(niz[1]);
			System.out.println(oldHex.toString());
			int index=model.getShapes().indexOf((Shape)oldHex);
			System.out.println(index+oldHex.toString());
			oldHex=(HexagonAdapter)model.get(index);
			HexagonAdapter newHex=extractHexagon(niz[2]);
			UpdateHexagonCmd updateHexagonCmd=new UpdateHexagonCmd(oldHex,newHex);
			execute(updateHexagonCmd);
			frame.repaint();
		}else if(niz[0].equals("UpdateRectangle")) {
			
			Rectangle oldRec=extractRectangle(niz[1]);
			int index=model.getShapes().indexOf((Shape)oldRec);
			oldRec=(Rectangle)model.get(index);
			Rectangle newRec=extractRectangle(niz[2]);
			UpdateRectangleCmd updateRectangleCmd=new UpdateRectangleCmd(oldRec,newRec);
			execute(updateRectangleCmd);
			frame.repaint();
		}//LOG:toFront
		else if(niz[0].equals("toFront")) {
			System.out.println(niz[1]);
			String[] niz2=niz[1].split("\\(");
			System.out.println(niz2[0]);
			if(niz2[0].equals("Line"))
		{
			Line line=extractLine(niz[1]);
			ToFrontCmd selectLineCmd=new ToFrontCmd(line,model);
			execute(selectLineCmd);
			frame.repaint();
			
		}
		else if(niz2[0].equals("Point")) {
			
			System.out.println("u selectPoint ifu sam");
			Point point=extractPoint(niz[1]);
			ToFrontCmd selectPointCmd=new ToFrontCmd(point,model);
			execute(selectPointCmd);
			frame.repaint();
			
		}else if(niz2[0].equals("Circle")) {
			
			System.out.println("u selectCircle ifu sam");
			Circle circle=extractCircle(niz[1]);
			ToFrontCmd selectCircleCmd=new ToFrontCmd(circle,model);
			execute(selectCircleCmd);
			frame.repaint();
		}else if(niz2[0].equals("Donut")) {
			
			System.out.println("u selectDonut ifu sam");
			Donut donut=extractDonut(niz[1]);
			ToFrontCmd selectDonutCmd=new ToFrontCmd(donut,model);
			execute(selectDonutCmd);
			frame.repaint();
		}else if(niz2[0].equals("Hexagon")) {
			
			System.out.println("u selecthexagon ifu sam");
			HexagonAdapter hex=extractHexagon(niz[1]);
			ToFrontCmd selectHexagonCmd=new ToFrontCmd(hex,model);
			execute(selectHexagonCmd);
			frame.repaint();
		}else if(niz2[0].equals("Rectangle")) {
			
			System.out.println("u selectrectangle ifu sam");
			Rectangle rec=extractRectangle(niz[1]);
			ToFrontCmd selectRectangleCmd=new ToFrontCmd(rec,model);
			execute(selectRectangleCmd);
			frame.repaint();
		}
	}//LOG:toBack
		else if(niz[0].equals("toBack")) {
		System.out.println(niz[1]);
		String[] niz2=niz[1].split("\\(");
		System.out.println(niz2[0]);
		if(niz2[0].equals("Line"))
	{
		Line line=extractLine(niz[1]);
		ToBackCmd selectLineCmd=new ToBackCmd(line,model);
		execute(selectLineCmd);
		frame.repaint();
		
	}
	else if(niz2[0].equals("Point")) {
		
		System.out.println("u toBackPoint ifu sam");
		Point point=extractPoint(niz[1]);
		ToBackCmd selectPointCmd=new ToBackCmd(point,model);
		execute(selectPointCmd);
		frame.repaint();
		
	}else if(niz2[0].equals("Circle")) {
		
		System.out.println("u toBackCircle ifu sam");
		Circle circle=extractCircle(niz[1]);
		ToBackCmd selectCircleCmd=new ToBackCmd(circle,model);
		execute(selectCircleCmd);
		frame.repaint();
	}else if(niz2[0].equals("Donut")) {
		
		System.out.println("u toBackDonut ifu sam");
		Donut donut=extractDonut(niz[1]);
		ToBackCmd selectDonutCmd=new ToBackCmd(donut,model);
		execute(selectDonutCmd);
		frame.repaint();
	}else if(niz2[0].equals("Hexagon")) {
		
		System.out.println("u toBackhexagon ifu sam");
		HexagonAdapter hex=extractHexagon(niz[1]);
		ToBackCmd selectHexagonCmd=new ToBackCmd(hex,model);
		execute(selectHexagonCmd);
		frame.repaint();
	}else if(niz2[0].equals("Rectangle")) {
		
		System.out.println("u toBackrectangle ifu sam");
		Rectangle rec=extractRectangle(niz[1]);
		ToBackCmd selectRectangleCmd=new ToBackCmd(rec,model);
		execute(selectRectangleCmd);
		frame.repaint();
	}
}//LOG:BringtoBack
		else if(niz[0].equals("bringToBack")) {
		System.out.println(niz[1]);
		String[] niz2=niz[1].split("\\(");
		System.out.println(niz2[0]);
		if(niz2[0].equals("Line"))
	{
		Line line=extractLine(niz[1]);
		BringToBackCmd selectLineCmd=new BringToBackCmd(line,model);
		execute(selectLineCmd);
		frame.repaint();
		
	}
	else if(niz2[0].equals("Point")) {
		
		System.out.println("u BringToBackCmdPoint ifu sam");
		Point point=extractPoint(niz[1]);
		BringToBackCmd selectPointCmd=new BringToBackCmd(point,model);
		execute(selectPointCmd);
		frame.repaint();
		
	}else if(niz2[0].equals("Circle")) {
		
		System.out.println("u BringToBackCmdCircle ifu sam");
		Circle circle=extractCircle(niz[1]);
		BringToBackCmd selectCircleCmd=new BringToBackCmd(circle,model);
		execute(selectCircleCmd);
		frame.repaint();
	}else if(niz2[0].equals("Donut")) {
		
		System.out.println("u BringToBackCmdDonut ifu sam");
		Donut donut=extractDonut(niz[1]);
		BringToBackCmd selectDonutCmd=new BringToBackCmd(donut,model);
		execute(selectDonutCmd);
		frame.repaint();
	}else if(niz2[0].equals("Hexagon")) {
		
		System.out.println("u BringToBackCmdhexagon ifu sam");
		HexagonAdapter hex=extractHexagon(niz[1]);
		BringToBackCmd selectHexagonCmd=new BringToBackCmd(hex,model);
		execute(selectHexagonCmd);
		frame.repaint();
	}else if(niz2[0].equals("Rectangle")) {
		
		System.out.println("u BringToBackCmdrectangle ifu sam");
		Rectangle rec=extractRectangle(niz[1]);
		BringToBackCmd selectRectangleCmd=new BringToBackCmd(rec,model);
		execute(selectRectangleCmd);
		frame.repaint();
	}
}//LOG:BringtoFrontCmd
		else if(niz[0].equals("bringToFront")) {
		System.out.println(niz[1]);
		String[] niz2=niz[1].split("\\(");
		System.out.println(niz2[0]);
		if(niz2[0].equals("Line"))
	{
		Line line=extractLine(niz[1]);
		BringToFrontCmd selectLineCmd=new BringToFrontCmd(line,model);
		execute(selectLineCmd);
		frame.repaint();
		
	}
	else if(niz2[0].equals("Point")) {
		
		System.out.println("u BringToFrontCmdPoint ifu sam");
		Point point=extractPoint(niz[1]);
		BringToFrontCmd selectPointCmd=new BringToFrontCmd(point,model);
		execute(selectPointCmd);
		frame.repaint();
		
	}else if(niz2[0].equals("Circle")) {
		
		System.out.println("u BringToFrontCmdCmdCircle ifu sam");
		Circle circle=extractCircle(niz[1]);
		BringToFrontCmd selectCircleCmd=new BringToFrontCmd(circle,model);
		execute(selectCircleCmd);
		frame.repaint();
	}else if(niz2[0].equals("Donut")) {
		
		System.out.println("u BringToFrontCmdDonut ifu sam");
		Donut donut=extractDonut(niz[1]);
		BringToFrontCmd selectDonutCmd=new BringToFrontCmd(donut,model);
		execute(selectDonutCmd);
		frame.repaint();
	}else if(niz2[0].equals("Hexagon")) {
		
		System.out.println("u BringToFrontCmdhexagon ifu sam");
		HexagonAdapter hex=extractHexagon(niz[1]);
		BringToFrontCmd selectHexagonCmd=new BringToFrontCmd(hex,model);
		execute(selectHexagonCmd);
		frame.repaint();
	}else if(niz2[0].equals("Rectangle")) {
		
		System.out.println("u BringToFrontCmdrectangle ifu sam");
		Rectangle rec=extractRectangle(niz[1]);
		BringToFrontCmd selectRectangleCmd=new BringToFrontCmd(rec,model);
		execute(selectRectangleCmd);
		frame.repaint();
	}
}//LOG:RemoveArray
		else if(niz[0].equals("RemoveArray"))
		{
			ArrayList<Shape> shapes=new ArrayList<Shape>();
			int i=1;
			while(i<niz.length)
			{
				String[] niz2=niz[i].split("\\(");
				
				if(niz2[0].equals("Line"))
				{
				System.out.println("u removeLine ifu sam");
				Line line=extractLine(niz[i]);
				shapes.add(line);
				}
				else if(niz2[0].equals("Point")) {
				
				Point point=extractPoint(niz[i]);
				shapes.add(point);
				
			}else if(niz2[0].equals("Circle")) {
				
				System.out.println("u RemoveCircleCmd ifu sam");
				Circle circle=extractCircle(niz[i]);
				shapes.add(circle);
				
			}else if(niz2[0].equals("Donut")) {
				
				System.out.println("u Removedonut ifu sam");
				Donut donut=extractDonut(niz[i]);
				shapes.add(donut);
				
			}else if(niz2[0].equals("Hexagon")) {
				
				System.out.println("u hexagon ifu sam");
				HexagonAdapter hex=extractHexagon(niz[i]);
				shapes.add(hex);
				
			}else if(niz2[0].equals("Rectangle")) {
				
				System.out.println("u Removerectangle ifu sam");
				Rectangle rec=extractRectangle(niz[i]);
				shapes.add(rec);
				
			}
				i++;
			}
			RemoveArrayCmd removeArrayCmd=new RemoveArrayCmd(shapes,model);
			execute(removeArrayCmd);
			frame.repaint();
			
	}
		//LOG:REDO
		else if(niz[0].equals("redo"))
		{
			redo();
			frame.repaint();

		}
		//LOG:UNDO
		else if(niz[0].equals("undo"))
		{
			undo();
			frame.repaint();
		}
		}catch(Exception e) {
			JOptionPane.showMessageDialog(null, "Greška !");
			unabledButtons(true);
			
		}

	}
}

private Point extractPoint(String l) {
	Point point=new Point();
	String[] niz=l.split(",");
	//vrijednost x centra u BROJU
	int firstPointX=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost y centra u BROJU
	int firstPointY=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//BORDER COLOR
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[3].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[4].replaceAll("[^0-9]", ""));

	point.setX(firstPointX);
	point.setY(firstPointY);
	point.setColor(new Color(R,G,B));
	
	return point;
}

private Line extractLine(String l) {
	System.out.println("u extract line sam");
	Line line=new Line();
	String[] niz=l.split("-->");
	String[] niz2=niz[0].split(",");
	//vrijednost x prve tacke u BROJU
	int firstPointX=Integer.parseInt(niz2[0].replaceAll("[^0-9]", ""));
	//vrijednost y prve tacke u BROJU
	int firstPointY=Integer.parseInt(niz2[1].replaceAll("[^0-9]", ""));
	
	String[] niz3=niz[1].split(",");
	//vrijednost x druge tacke u BROJU
	int secondPointX=Integer.parseInt(niz3[0].replaceAll("[^0-9]", ""));
	//vrijednost y druge tacke u BROJU
	int secondPointY=Integer.parseInt(niz3[1].replaceAll("[^0-9]", ""));
	
	//vrijednost R u rgb
	int R=Integer.parseInt(niz3[2].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz3[3].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz3[4].replaceAll("[^0-9]", ""));
	
	line.setStartPoint(new Point(firstPointX,firstPointY));
	line.setEndPoint(new Point(secondPointX,secondPointY));
	line.setColor(new Color(R,G,B));
	
	
	System.out.println(niz2[0]);
	return line;
}
private Circle extractCircle(String l) {
	Circle circle=new Circle();
	String[] niz=l.split(",");
	//vrijednost x centra u BROJU
	int firstPointX=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost y centra u BROJU
	int firstPointY=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//vrijednost radiusa r
	int radius=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	
	//BORDER COLOR
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[3].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[4].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[5].replaceAll("[^0-9]", ""));
	
	//INNER COLOR
	int R2=Integer.parseInt(niz[6].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G2=Integer.parseInt(niz[7].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B2=Integer.parseInt(niz[8].replaceAll("[^0-9]", ""));
	
	circle.setCenter(new Point(firstPointX,firstPointY));
	try {
		circle.setRadius(radius);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	circle.setColor(new Color(R,G,B));
	circle.setInnerColor(new Color(R2,G2,B2));
	
	return circle;
}
private Donut extractDonut(String l) {
	Donut donut=new Donut();
	String[] niz=l.split(",");
	//vrijednost x centra u BROJU
	int firstPointX=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost y centra u BROJU
	int firstPointY=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//vrijednost radiusa r
	int radius=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	//vrijednost innerRadiusa 
	int innerRadius=Integer.parseInt(niz[3].replaceAll("[^0-9]", ""));
	//BORDER COLOR
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[4].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[5].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[6].replaceAll("[^0-9]", ""));
	
	//INNER COLOR
	int R2=Integer.parseInt(niz[7].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G2=Integer.parseInt(niz[8].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B2=Integer.parseInt(niz[9].replaceAll("[^0-9]", ""));
	
	donut.setCenter(new Point(firstPointX,firstPointY));
	try {
		donut.setRadius(radius);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	try {
		donut.setInnerRadius(innerRadius);
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	donut.setColor(new Color(R,G,B));
	donut.setInnerColor(new Color(R2,G2,B2));
	
	return donut;
}
private Rectangle extractRectangle(String l) {
	Rectangle rec=new Rectangle();
	String[] niz=l.split(",");
	//vrijednost x centra u BROJU
	int firstPointX=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost y centra u BROJU
	int firstPointY=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//vrijednost width
	int width=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	//vrijednost height
	int height=Integer.parseInt(niz[3].replaceAll("[^0-9]", ""));
	
	//BORDER COLOR
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[4].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[5].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[6].replaceAll("[^0-9]", ""));
	
	//INNER COLOR
	int R2=Integer.parseInt(niz[7].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G2=Integer.parseInt(niz[8].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B2=Integer.parseInt(niz[9].replaceAll("[^0-9]", ""));
	
	rec.setUpperLeftPoint(new Point(firstPointX,firstPointY));
	rec.setWidth(width);
	rec.setHeight(height);
	rec.setColor(new Color(R,G,B));
	rec.setInnerColor(new Color(R2,G2,B2));
	
	return rec;
}
private HexagonAdapter extractHexagon(String l) {
	HexagonAdapter hex=new HexagonAdapter();
	String[] niz=l.split(",");
	//vrijednost x centra u BROJU
	int firstPointX=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost y centra u BROJU
	int firstPointY=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//vrijednost radiusa r
	int radius=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	
	//BORDER COLOR
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[3].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[4].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[5].replaceAll("[^0-9]", ""));
	
	//INNER COLOR
	int R2=Integer.parseInt(niz[6].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G2=Integer.parseInt(niz[7].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B2=Integer.parseInt(niz[8].replaceAll("[^0-9]", ""));
	
	hex.setStartPoint(new Point(firstPointX,firstPointY));
	hex.setR(radius);
	hex.setColor(new Color(R,G,B));
	hex.setInnerColor(new Color(R2,G2,B2));
	
	return hex;
}
private Color extractBorder(String l) {
	Color color;
	String[] niz=l.split(",");
	//vrijednost R u rgb 
	int R=Integer.parseInt(niz[0].replaceAll("[^0-9]", ""));
	//vrijednost G u rgb
	int G=Integer.parseInt(niz[1].replaceAll("[^0-9]", ""));
	//vrijednost B u rgb
	int B=Integer.parseInt(niz[2].replaceAll("[^0-9]", ""));
	
	color=new Color(R,G,B);
	return color;
}

public void unabledButtons(boolean signal) {
	frame.btnLoadNext.setEnabled(!signal);
	frame.btnColor.setEnabled(signal);
	frame.btnInnerColor.setEnabled(signal);
	frame.getBtnDelete().setVisible(signal);
	frame.getBtnModify().setVisible(signal);
	frame.getBtnUndo().setVisible(signal);
	frame.getBtnRedo().setVisible(signal);
	frame.enableButtonGroup(signal);
	frame.getBtnToBack().setVisible(signal);
	frame.getBtnToFront().setVisible(signal);
	frame.getBtnBringToBack().setVisible(signal);
	frame.getBtnBringToFront().setVisible(signal);
	frame.getBtnExitFromLog().setEnabled(!signal);
	frame.getBtnExitFromLog().setVisible(!signal);
	if(signal==false) {
		frame.getView().removeMouseListener(frame.getMouseListener());
	}else
		frame.getView().addMouseListener(frame.getMouseListener());
}
    

}
