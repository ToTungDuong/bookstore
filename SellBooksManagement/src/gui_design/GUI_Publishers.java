package gui_design;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.event.CaretEvent;
import javax.swing.event.CaretListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import process.Process_Publisher;
import process.Publisher;

public class GUI_Publishers extends JFrame {

	private JPanel contentPane;
	private ImageIconHelper imgHelp = new ImageIconHelper();
	private JTextField txtName;
	private JTable tbPub;
	private DefaultTableModel dtm = new DefaultTableModel();
	private Vector tbContent = new Vector();
	private Vector tbHeader = new Vector();
	private Process_Publisher pp = new Process_Publisher();
	private Publisher pu = new Publisher();
	private ArrayList<String> errors = new ArrayList<>();
	private ArrayList<Publisher> lp = new ArrayList<>();
	private JTextField txtFind;
	private JLabel lbCheck;
	
	private static GUI_Publishers frame = new GUI_Publishers();

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI_Login l = new GUI_Login();
					l.setLocationRelativeTo(null); 
					l.setVisible(true);
//					frame.setLocationRelativeTo(null); 
//					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public void showErrorMessage(String message, String err) {
	    JOptionPane.showMessageDialog(this, message, err, JOptionPane.ERROR_MESSAGE);
	}
	
	public void showSuccessMessage(String message, String suc) {
	    JOptionPane.showMessageDialog(this, message, suc, JOptionPane.INFORMATION_MESSAGE);
	}
	
	public void getListPublishers() {
		tbContent = new Vector();
		lp = pp.getListPublishers();
		for (int i=0; i<lp.size(); i++) {
			Publisher p = lp.get(i);
			Vector tbRow = new Vector();
			tbRow.add(p.getId());
			tbRow.add(p.getName());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbPub.setModel(dtm);
	}
	
	public void getListPublishersByName(String name) {
		tbContent = new Vector();
		lp = pp.getPublishersByName(name);
		
		if (lp.size() == 0) 
			showErrorMessage("Can't find any publiser with name "+name, "Error");
		
		for (int i=0; i<lp.size(); i++) {
			Publisher a = lp.get(i);
			Vector tbRow = new Vector();
			tbRow.add(a.getId());
			tbRow.add(a.getName());
			tbContent.add(tbRow);
		}
		dtm.setDataVector(tbContent, tbHeader);
		tbPub.setModel(dtm);
	}
	
	public void clearInforInput() {
		txtName.setText(null);
		pu = new Publisher();
		errors = new ArrayList<>();
		getListPublishers();
	}
	
	public GUI_Publishers() {
		setTitle("Publishers Management");
		
		ImageIcon searchIcon = imgHelp.getIcon(GUI_Publishers.class.getResource("/icons/search.png"), 18, 18);
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 648, 421);
		contentPane = new JPanel();
		contentPane.setBorder(new SoftBevelBorder(BevelBorder.RAISED, new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255), new Color(255, 255, 255)));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 41, 612, 202);
		contentPane.add(scrollPane);
		
		tbPub = new JTable();
		scrollPane.setViewportView(tbPub);
		
		JLabel lblNewLabel_1_1_1 = new JLabel("List publishers:");
		lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.BOLD, 15));
		lblNewLabel_1_1_1.setBounds(41, 11, 132, 19);
		contentPane.add(lblNewLabel_1_1_1);
		
		JButton btnAdd = new JButton("Add");
		btnAdd.setDefaultCapable(false);
		btnAdd.setBounds(84, 333, 89, 24);
		contentPane.add(btnAdd);
		btnAdd.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnUpdate = new JButton("Update");
		btnUpdate.setDefaultCapable(false);
		btnUpdate.setBounds(273, 333, 89, 24);
		contentPane.add(btnUpdate);
		btnUpdate.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		JButton btnDelete = new JButton("Delete");
		btnDelete.setDefaultCapable(false);
		btnDelete.setBounds(457, 333, 89, 24);
		contentPane.add(btnDelete);
		btnDelete.setFont(new Font("Tahoma", Font.PLAIN, 15));
		
		txtName = new JTextField();
		txtName.setBounds(260, 276, 198, 21);
		contentPane.add(txtName);
		txtName.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtName.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("Name");
		lblNewLabel.setBounds(199, 277, 51, 19);
		contentPane.add(lblNewLabel);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 15));
		
		txtFind = new JTextField();
		txtFind.setFont(new Font("Tahoma", Font.PLAIN, 15));
		txtFind.setColumns(10);
		txtFind.setBounds(401, 11, 172, 21);
		contentPane.add(txtFind);
		
		JLabel lblFindByUsername = new JLabel("Find by name:");
		lblFindByUsername.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblFindByUsername.setBounds(290, 11, 101, 19);
		contentPane.add(lblFindByUsername);
		
		tbHeader.add("ID");
		tbHeader.add("Name");
		getListPublishers();
				
		lbCheck = new JLabel("");
		lbCheck.setOpaque(true);
		lbCheck.setHorizontalAlignment(SwingConstants.CENTER);
		lbCheck.setHorizontalTextPosition(SwingConstants.CENTER);
		lbCheck.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lbCheck.setBounds(10, 433, 612, 19);
		contentPane.add(lbCheck);
		
		JLabel lbSearchIcon = new JLabel("");
		lbSearchIcon.setOpaque(true);
		lbSearchIcon.setIcon(searchIcon);
		lbSearchIcon.setBounds(579, 11, 20, 20);
		contentPane.add(lbSearchIcon);
		
		tbPub.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
		    public void valueChanged(ListSelectionEvent e) {
		        if (!e.getValueIsAdjusting()) {
		            int selectedRow = tbPub.getSelectedRow();
		            if (selectedRow >= 0) {
		                Vector vt = (Vector) tbContent.get(selectedRow);
		                txtName.setText(vt.get(1).toString());
		                pu = new Publisher((int) vt.get(0), (String) vt.get(1));
		            }
		        }
		    }
		});
		
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (pu.getId() == -1) 
						throw new Exception("Please select publisher you want to delete");
					else {
						int result = JOptionPane.showConfirmDialog(frame,"Delete this publisher?", 
								"Are you sure?",
					             JOptionPane.YES_NO_OPTION,
					             JOptionPane.QUESTION_MESSAGE);
					    if(result == JOptionPane.YES_OPTION){
							pp.deletePublisher(pu.getId());
							showSuccessMessage("Deleted successfully", "Success");
							clearInforInput();
					    }
					}
				}
				catch (Exception ex) {
					showErrorMessage(ex.getMessage(), "Deleted fail");
				}
			}
		});
		
		btnUpdate.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {
					if (pu.getId() == -1) {
						errors.add("Please select publisher you want to update");
						throw new Exception();
					}
					
					String name = txtName.getText().toString();
					
					if (name.equals("")) {				
						errors.add("Input field can't be blank");
						throw new Exception();
					}
											
					pp.updatePublisher(pu.getId(), name);
					showSuccessMessage("Updated publisher successfully", "Success");
					clearInforInput();
				}
				catch (SQLException ex) {
					showErrorMessage(ex.getMessage(), "Updated fail");
					errors = new ArrayList<>();
				}
				catch (Exception ex) {
					showErrorMessage(String.join("\n", errors), "Updated fail");
					errors = new ArrayList<>();
				}
			}
		});
		
		btnAdd.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				try {					
					if (btnAdd.getText().equals("Add")) {
						clearInforInput();
						btnAdd.setText("Save");
						btnUpdate.setEnabled(false);
						btnDelete.setEnabled(false);
					}
					else {
						String name = txtName.getText().toString();
						
						if (name.equals("")) {
							errors.add("Input field can't be blank.");
							throw new Exception();
						}
												
						pp.insertPublisher(name);
						btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						showSuccessMessage("Added publisher successfully", "Success");
						clearInforInput();
					}
				}
				catch (SQLException ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "+ex.getMessage()+ " Try again?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE);
				    if(result == JOptionPane.NO_OPTION){
				    	btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						clearInforInput();
				    }
				    errors = new ArrayList<>();
				}
				catch (Exception ex) {
					int result = JOptionPane.showConfirmDialog(frame,"Add failed. "+String.join("\n", errors)+ " Try again?", 
							"Are you sure?",
				             JOptionPane.YES_NO_OPTION,
				             JOptionPane.ERROR_MESSAGE);
				    if(result == JOptionPane.NO_OPTION){
				    	btnUpdate.setEnabled(true);
						btnDelete.setEnabled(true);
						btnAdd.setText("Add");
						clearInforInput();
				    }
				    errors = new ArrayList<>();
				}
			}
		});
		
		txtFind.addCaretListener(new CaretListener() {
			public void caretUpdate(CaretEvent e) {
				if (txtFind.getText().equals("")) getListPublishers();
				else getListPublishersByName(txtFind.getText());
			}
		});
	}
}
