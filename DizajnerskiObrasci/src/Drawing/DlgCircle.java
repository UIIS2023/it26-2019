package Drawing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Circle;
import geometry.Point;
import geometry.Rectangle;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgCircle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldRadius;
	private JButton btnBc;
	private JButton btnIc;
	private boolean isOk;
	private Circle ci;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgCircle dialog = new DlgCircle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgCircle() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		
		JLabel lblX = new JLabel("Coordinate X of the center :");
		
		JLabel lblY = new JLabel("Coordinate Y of the center :");
		
		JLabel lblRadius = new JLabel("Radius :");
		
		textFieldX = new JTextField();
		textFieldX.setColumns(10);
		
		textFieldY = new JTextField();
		textFieldY.setColumns(10);
		
		textFieldRadius = new JTextField();
		textFieldRadius.setColumns(10);
		
		btnBc = new JButton("Border Color");
		btnBc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JColorChooser obj=new JColorChooser();
				Object a=obj;
				int b=JOptionPane.showConfirmDialog(null, a,"Vukasin Stanisic IT26",JOptionPane.OK_CANCEL_OPTION);
				if(b==0)
					btnBc.setBackground(obj.getColor());
			}
		});
		
		btnIc = new JButton("Inner Color");
		btnIc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				JColorChooser obj=new JColorChooser();
				Object a=obj;
				int b=JOptionPane.showConfirmDialog(null, a,"Vukasin Stanisic IT26",JOptionPane.OK_CANCEL_OPTION);
				if(b==0)
					btnIc.setBackground(obj.getColor());
			}
		});
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(btnBc)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnIc))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblX)
								.addComponent(lblY)
								.addComponent(lblRadius))
							.addGap(67)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(67, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblX)
						.addComponent(textFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblY)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblRadius))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldRadius, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 58, Short.MAX_VALUE)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(btnIc, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(btnBc, GroupLayout.DEFAULT_SIZE, 54, Short.MAX_VALUE))
					.addContainerGap())
		);
		contentPanel.setLayout(gl_contentPanel);
		{
			JPanel buttonPane = new JPanel();
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			buttonPane.setLayout(new GridLayout(0, 2, 0, 0));
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						isOk=true;
						setVisible(false);
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent arg0) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			
		}
	}
	
	public void setCircle(Circle c){
		textFieldX.setText(String.valueOf(c.getCenter().getX()));
		textFieldY.setText(String.valueOf(c.getCenter().getY()));
		if(c.getRadius()!=-1)
		{
			textFieldRadius.setText(String.valueOf(c.getRadius()));
		}		
		getBtnBc().setBackground(c.getColor());
		getBtnIc().setBackground(c.getInnerColor());
	}
	
	public JButton getBtnBc() {
		return btnBc;
	}
	public JButton getBtnIc() {
		return btnIc;
	}
	public boolean isCommited(){
		return this.isOk;
	}
	public Circle getCircle() throws Exception{
		try{
			Circle ci=new Circle();
			ci.setCenter(new Point(Integer.parseInt(textFieldX.getText()),Integer.parseInt(textFieldY.getText())));
			ci.setRadius(Integer.parseInt(textFieldRadius.getText()));
			ci.setColor(getBtnBc().getBackground());
			ci.setInnerColor(getBtnIc().getBackground());
			this.ci=ci;
			}catch(NumberFormatException exception){
				JOptionPane.showMessageDialog(null, "Input error!!!");
			}
			return this.ci;
	}
}
