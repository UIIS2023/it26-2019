package Drawing;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import geometry.Line;
import geometry.Point;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class DlgLine extends JDialog {

	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldSx;
	private JTextField textFieldSy;
	private JTextField textFieldEx;
	private JTextField textFieldEy;
	private JButton btnBc;
	private Line l;
	private boolean isOk;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		try {
			DlgLine dialog = new DlgLine();
			dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
			dialog.setVisible(true);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the dialog.
	 */
	public DlgLine() {
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		JLabel lblSx = new JLabel("Coordinate X of the Start Point :");
		JLabel lblSy = new JLabel("Coordinate Y of the Start Point :");
		JLabel lblEx = new JLabel("Coordinate X of the End Point :");
		JLabel lblEy = new JLabel("Coordinate Y of the End Point :");
		textFieldSx = new JTextField();
		textFieldSx.setColumns(10);
		textFieldSy = new JTextField();
		textFieldSy.setColumns(10);
		textFieldEx = new JTextField();
		textFieldEx.setColumns(10);
		textFieldEy = new JTextField();
		textFieldEy.setColumns(10);
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
		GroupLayout gl_contentPanel = new GroupLayout(contentPanel);
		gl_contentPanel.setHorizontalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnBc)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSx)
								.addComponent(lblEy)
								.addComponent(lblEx)
								.addComponent(lblSy))
							.addGap(51)
							.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(textFieldEx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldSy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldSx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(textFieldEy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))))
					.addContainerGap(59, Short.MAX_VALUE))
		);
		gl_contentPanel.setVerticalGroup(
			gl_contentPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_contentPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSx)
						.addComponent(textFieldSx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addComponent(textFieldSy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textFieldEx, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addGroup(gl_contentPanel.createSequentialGroup()
							.addGap(5)
							.addComponent(lblSy)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblEx)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_contentPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(textFieldEy, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblEy))
					.addGap(18)
					.addComponent(btnBc, GroupLayout.DEFAULT_SIZE, 55, Short.MAX_VALUE)
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

	public JButton getBtnBc() {
		return btnBc;
	}
	
	public void setLine(Line l){
		textFieldSx.setText(String.valueOf(l.getStartPoint().getX()));
		textFieldSy.setText(String.valueOf(l.getStartPoint().getY()));
		textFieldEx.setText(String.valueOf(l.getEndPoint().getX()));
		textFieldEy.setText(String.valueOf(l.getEndPoint().getY()));
		getBtnBc().setBackground(l.getColor());

	}
	public Line getLine(){
		try{
			Line l=new Line();
			l.setStartPoint(new Point(Integer.parseInt(textFieldSx.getText()),Integer.parseInt(textFieldSy.getText())));
			l.setEndPoint(new Point(Integer.parseInt(textFieldEx.getText()),Integer.parseInt(textFieldEy.getText())));
			l.setColor(getBtnBc().getBackground());
			this.l=l;
			}catch(NumberFormatException exception){
				JOptionPane.showMessageDialog(null, "Input error!!!");
			}
			return this.l;
	}

	public boolean isCommited(){
		return this.isOk;
	}
}
