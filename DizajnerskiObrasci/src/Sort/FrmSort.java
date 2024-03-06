package Sort;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Stack.DlgStack;
import geometry.Rectangle;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.GridLayout;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.util.Arrays;
import java.awt.event.ActionEvent;

public class FrmSort extends JFrame {

	private JPanel contentPane;
	private DefaultListModel<Rectangle> dlm=new DefaultListModel<Rectangle>();
	JList list = new JList();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FrmSort frame = new FrmSort();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public FrmSort() {
		setTitle("Vukasin Stanisic IT26");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.SOUTH);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JButton btnAdd = new JButton("Add");
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				DlgStack dialog=new DlgStack();
				dialog.setModal(true);
				dialog.setVisible(true);
				if(!dialog.isCommited())
					return;
				else{
					dlm.addElement(dialog.getRectangle());
				}
			}
		});
		panel.add(btnAdd);
		
		JButton btnSort = new JButton("Sort");
		btnSort.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(dlm.getSize()<=0)
				JOptionPane.showMessageDialog(null, "The list is empty !!!");
				else{
					int n=dlm.getSize();
					Rectangle[] row=new Rectangle[n];
					for(int i=0;i<n;i++)
					{
						row[i]=dlm.getElementAt(i);
					}
					Arrays.sort(row);
					dlm.clear();
					for(int i=0;i<n;i++)
					{
						dlm.addElement(row[i]);
					}
				}
				
			}
		});
		panel.add(btnSort);
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, BorderLayout.CENTER);
		

		scrollPane.setViewportView(list);
		list.setModel(dlm);
	}

}
