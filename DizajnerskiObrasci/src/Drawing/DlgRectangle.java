package Drawing;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Point;
import geometry.Rectangle;

import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgRectangle extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldX;
	private JTextField textFieldY;
	private JTextField textFieldHeight;
	private JTextField textFieldWidth;
	private Rectangle r;
private boolean isOk;
private JButton btnBc;
private JButton btnIc;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgRectangle dialog = new DlgRectangle();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgRectangle() {
		setModal(true);
		setTitle("Vukasin Stanisic IT26");
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblX = new JLabel("Coordinate X of the upperLeft point :");
		textFieldX = new JTextField();
		textFieldX.setColumns(10);
		JLabel lblY = new JLabel("Coordinate Y of the upperLeft point :");
		textFieldY = new JTextField();
		textFieldY.setColumns(10);
		JLabel lblHeightOfThe = new JLabel("Height of the Rectangle :");
		textFieldHeight = new JTextField();
		textFieldHeight.setColumns(10);
		JLabel lblWidthOfThe = new JLabel("Width of the Rectangle :");
		textFieldWidth = new JTextField();
		textFieldWidth.setColumns(10);
		btnBc = new JButton("Border Color");
		btnBc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JColorChooser obj=new JColorChooser();
				Object a=obj;
				int b=JOptionPane.showConfirmDialog(null, a,"Vukasin Stanisic IT26",JOptionPane.OK_CANCEL_OPTION);
				if(b==0)
					btnBc.setBackground(obj.getColor());
			}
		});
		btnIc = new JButton("Inner Color");
		btnIc.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
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
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(lblX)
							.addGap(18)
							.addComponent(textFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
								.addComponent(btnBc)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(btnIc))
							.addGroup(Alignment.LEADING, gl_contentPanel.createSequentialGroup()
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(lblY)
									.addComponent(lblHeightOfThe)
									.addComponent(lblWidthOfThe))
								.addGap(18)
								.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
									.addComponent(textFieldWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(textFieldHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
									.addComponent(textFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))))
					.addContainerGap(66, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblX)
						.addComponent(textFieldX, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblY)
						.addComponent(textFieldY, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHeightOfThe)
						.addComponent(textFieldHeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWidthOfThe)
						.addComponent(textFieldWidth, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addGap(33)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnIc, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE)
						.addComponent(btnBc, GroupLayout.DEFAULT_SIZE, 50, Short.MAX_VALUE))
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
					public void actionPerformed(ActionEvent e) {
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
					public void actionPerformed(ActionEvent e) {
						setVisible(false);
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
			
			
			
		}
	}


	public JButton getBtnBc() {
		return btnBc;
	}
	public JButton getBtnIc() {
		return btnIc;
	}
	public void setRectangle(Rectangle r)
	{
		textFieldX.setText(String.valueOf(r.getUpperLeftPoint().getX()));
		textFieldY.setText(String.valueOf(r.getUpperLeftPoint().getY()));
		if(r.getWidth()!=-1)
		{
			textFieldWidth.setText(String.valueOf(r.getWidth()));
		}
		if(r.getHeight()!=-1)
		{
			textFieldHeight.setText(String.valueOf(r.getHeight()));
		}
		getBtnBc().setBackground(r.getColor());
		getBtnIc().setBackground(r.getInnerColor());
	}
	public Rectangle getRectangle(){
		try{
		Rectangle rec=new Rectangle();
		rec.setUpperLeftPoint(new Point(Integer.parseInt(textFieldX.getText()),Integer.parseInt(textFieldY.getText())));
		rec.setWidth(Integer.parseInt(textFieldWidth.getText()));
		rec.setHeight(Integer.parseInt(textFieldHeight.getText()));
		rec.setColor(getBtnBc().getBackground());
		rec.setInnerColor(getBtnIc().getBackground());
		this.r=rec;
		}catch(NumberFormatException exception){
			JOptionPane.showMessageDialog(null, "Input error!!!");
		}
		return this.r;
	}
	public boolean isCommited(){
		return this.isOk;
	}

}
