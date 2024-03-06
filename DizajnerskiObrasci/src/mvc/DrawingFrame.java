package mvc;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.Enumeration;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Component;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class DrawingFrame extends JFrame implements PropertyChangeListener  {
	private DrawingView view = new DrawingView();
	private DrawingController controller;
	
	private JPanel contentPane;
	private final static ButtonGroup btnGroup = new ButtonGroup();
	private JToggleButton tglbtnPoint;
	private JToggleButton tglbtnLine;
	private JToggleButton tglbtnRectangle;
	private JToggleButton tglbtnCircle;
	private JToggleButton tglbtnDonut;
	private JToggleButton tglbtnSelection;
	private JButton btnModify;
	private JButton btnDelete;
	private JButton btnUndo;
	private JButton btnRedo;
	private JToggleButton tglbtnHexagon;
	private JPanel selektovaniO;
	private JLabel lblOdabirOblika;
	private JButton btnBringToFront;
	private JButton btnBringToBack;
	private JButton btnToFront;
	private JButton btnToBack;
	public JButton btnInnerColor;
	public JButton btnColor;
	private JScrollPane scrollPane;
	private JList list;
	public DefaultListModel<String> dlm=new DefaultListModel<String>();
	private JButton btnSaveLog;
	private JButton btnSavePaint;
	private JButton btnLoadTheDrawing;
	private JButton btnLoadLog;
	public JButton btnLoadNext;
	private JButton btnExitFromLog;
	private MouseAdapter mouseA;

	public DrawingFrame() {
		
		setTitle("Vukasin Stanisic IT26");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 800, 600);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		mouseA=new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.mouseClicked(e);
			}
		};
		
		view.setBackground(Color.WHITE);

		view.addMouseListener(mouseA);
		getContentPane().add(view, BorderLayout.CENTER);
	panel.setLayout(new GridLayout(0, 8, 0, 0));
	
	btnRedo = new JButton("Redo");
	btnRedo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {

			controller.redo();
			repaint();
		}
	});
	btnRedo.setEnabled(false);
	panel.add(btnRedo);
	
	btnUndo = new JButton("Undo");
	btnUndo.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {

			controller.undo();
			repaint();
		}
	});
	btnUndo.setEnabled(false);
	panel.add(btnUndo);
	
	tglbtnSelection = new JToggleButton("Selection");
	panel.add(tglbtnSelection);
	btnGroup.add(tglbtnSelection);
	
	
	btnModify = new JButton("Modify");
	btnModify.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			try {
				controller.modify();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	});
	btnModify.setEnabled(false);
	panel.add(btnModify);
	
	btnDelete = new JButton("Delete");
	btnDelete.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			controller.delete();
		}
	});
	btnDelete.setEnabled(false);
	panel.add(btnDelete);
	
	selektovaniO = new JPanel();
	selektovaniO.setAlignmentY(2.0f);
	contentPane.add(selektovaniO, BorderLayout.WEST);
	
	lblOdabirOblika = new JLabel("Odabir oblika:");
	
	
	tglbtnPoint = new JToggleButton("Point");
	
	btnGroup.add(tglbtnPoint);
	
	tglbtnLine = new JToggleButton("Line");
	btnGroup.add(tglbtnLine);
	
	tglbtnRectangle = new JToggleButton("Rectangle");
	btnGroup.add(tglbtnRectangle);
	
	tglbtnCircle = new JToggleButton("Circle");
	btnGroup.add(tglbtnCircle);
	
	tglbtnDonut = new JToggleButton("Donut");
	tglbtnDonut.setBounds(new Rectangle(0, 20, 0, 0));
	btnGroup.add(tglbtnDonut);
	selektovaniO.setLayout(new GridLayout(10, 1, 0, 0));
	selektovaniO.add(lblOdabirOblika);
	selektovaniO.add(tglbtnPoint);
	selektovaniO.add(tglbtnLine);
	selektovaniO.add(tglbtnRectangle);
	
	tglbtnHexagon = new JToggleButton("Hexagon");
	btnGroup.add(tglbtnHexagon);
	selektovaniO.add(tglbtnHexagon);
	selektovaniO.add(tglbtnCircle);
	selektovaniO.add(tglbtnDonut);
	
	btnColor = new JButton("Border Color");
	btnColor.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.borderColorClicked();
		}
	});
	btnColor.setBackground(Color.BLACK);
	selektovaniO.add(btnColor);
	
	btnInnerColor = new JButton("InnerColor");
	btnInnerColor.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.innerColorClicked();
		}
	});
	btnInnerColor.setBackground(Color.WHITE);
	selektovaniO.add(btnInnerColor);
	
	JPanel panelLeft = new JPanel();
	contentPane.add(panelLeft, BorderLayout.EAST);
	
	btnToBack = new JButton("To Back");
	btnToBack.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.toBack();
		}
	});
	btnToBack.setEnabled(false);
	
	btnToFront = new JButton("To Front");
	btnToFront.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.toFront();
		}
	});
	btnToFront.setEnabled(false);
	
	panelLeft.setLayout(new GridLayout(10, 1, 0, 0));
	panelLeft.add(btnToBack);
	panelLeft.add(btnToFront);
	
	btnBringToFront = new JButton("Bring To Front");
	btnBringToFront.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.bringToFront();
		}
	});
	btnBringToFront.setEnabled(false);
	
	panelLeft.add(btnBringToFront);
	
	btnBringToBack = new JButton("Bring To Back");
	btnBringToBack.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.bringToBack();
		}
	});
	btnBringToBack.setEnabled(false);
	
	panelLeft.add(btnBringToBack);
	
	btnSaveLog = new JButton("Save Log");
	btnSaveLog.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.saveLogClicked();
		}
	});
	panelLeft.add(btnSaveLog);
	
	btnSavePaint = new JButton("Save Drawing");
	btnSavePaint.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.savePaintClicked();
		}
	});
	panelLeft.add(btnSavePaint);
	
	btnLoadTheDrawing = new JButton("Load the drawing");
	btnLoadTheDrawing.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				controller.loadTheDrawing();
			} catch (ClassNotFoundException e1) {

				JOptionPane.showMessageDialog(null, "Greška !");
			}
		}
	});
	panelLeft.add(btnLoadTheDrawing);
	
	btnLoadLog = new JButton("Load From Log ");
	btnLoadLog.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			try {
				controller.loadDrawFromLog();
			} catch (IOException e1) {
				JOptionPane.showMessageDialog(null, "Greška !");
			}
		}
	});
	panelLeft.add(btnLoadLog);
	
	btnLoadNext = new JButton("Load Next");
	btnLoadNext.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.extractCommands();
		}
	});
	btnLoadNext.setEnabled(false);
	panelLeft.add(btnLoadNext);
	
	btnExitFromLog = new JButton("Exit from log");
	btnExitFromLog.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			controller.unabledButtons(true);
		}
	});
	btnExitFromLog.setEnabled(false);
	btnExitFromLog.setVisible(false);
	panelLeft.add(btnExitFromLog);
	
	scrollPane = new JScrollPane();
	contentPane.add(scrollPane, BorderLayout.SOUTH);
	
	list = new JList();
	scrollPane.setViewportView(list);
	list.setModel(dlm);
}

public JToggleButton getTglbtnPoint() {
	return tglbtnPoint;
}
public JToggleButton getTglbtnLine() {
	return tglbtnLine;
}
public JToggleButton getTglbtnRectangle() {
	return tglbtnRectangle;
}
public JToggleButton getTglbtnHexagon() {
	return tglbtnHexagon;
}
public JToggleButton getTglbtnCircle() {
	return tglbtnCircle;
}
public JToggleButton getTglbtnDonut() {
	return tglbtnDonut;
}
public JToggleButton getTglbtnSelection() {
	return tglbtnSelection;
}
public JButton getBtnModify() {
	return btnModify;
}
public JButton getBtnDelete() {
	return btnDelete;
}	
public JButton getBtnUndo() {
	return btnUndo;
}
public JButton getBtnRedo() {
	return btnRedo;
}

	public DrawingView getView() {
		return view;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}
	
	
	public void propertyChange(PropertyChangeEvent evt) {
		if (evt.getPropertyName().equals("undo")) {
			btnUndo.setEnabled((boolean)evt.getOldValue());
		} else if (evt.getPropertyName().equals("redo")) {
			btnRedo.setEnabled((boolean)evt.getOldValue());
		}  else if (evt.getPropertyName().equals("delete")) {
			btnDelete.setEnabled((boolean)evt.getOldValue());
		}  else if (evt.getPropertyName().equals("modify")) {
			btnModify.setEnabled((boolean)evt.getOldValue());
			btnToFront.setEnabled((boolean)evt.getOldValue());
			btnToBack.setEnabled((boolean)evt.getOldValue());
			btnBringToFront.setEnabled((boolean)evt.getOldValue());
			btnBringToBack.setEnabled((boolean)evt.getOldValue());
		}

	}

	public static void enableButtonGroup(boolean enable)
	{
	  Enumeration<AbstractButton> buttons = btnGroup.getElements();
	  while (buttons.hasMoreElements())
	  {
		  AbstractButton button=buttons.nextElement();
		 
	   button.setEnabled(enable);
	  }
	}

	public JButton getBtnBringToFront() {
		return btnBringToFront;
	}

	public JButton getBtnBringToBack() {
		return btnBringToBack;
	}

	public JButton getBtnToFront() {
		return btnToFront;
	}

	public JButton getBtnToBack() {
		return btnToBack;
	}
	
	public JButton getBtnExitFromLog() {
		return btnExitFromLog;
	}
	
	public MouseAdapter getMouseListener() {
		return mouseA;
	}
}
