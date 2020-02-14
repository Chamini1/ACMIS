import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import net.proteanit.sql.DbUtils;

import java.awt.Color;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JTable;
import java.sql.*;
import javax.swing.JScrollPane;
import javax.swing.JComboBox;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class SearchFrame extends JFrame {

	private JPanel contentPane;
	private JTextField textField;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SearchFrame frame = new SearchFrame();
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
	
	public SearchFrame() {
		
		String[] aValue = null;
		int[] selectedID = null;
				
		SingleConnection dbcon = SingleConnection.getInstance();
	    
        try {
			Connection con = dbcon.getConnection();
		} catch (ClassNotFoundException e1) {
			e1.printStackTrace();
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 708, 467);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.WHITE);
		panel.setBounds(0, 0, 190, 417);
		contentPane.add(panel);
		panel.setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(37, 98, 110, 26);
		panel.add(textField);
		textField.setColumns(10);
		String searchID = textField.getText();
		String[] searchIDSpiltArry = searchID.split("0", 0);
		String searchIDSpilt = searchIDSpiltArry[0];
		
		JLabel lblEnterStudentId = new JLabel("Enter student ID");
		lblEnterStudentId.setForeground(Color.BLACK);
		lblEnterStudentId.setBounds(49, 61, 96, 26);
		panel.add(lblEnterStudentId);
		
		JButton btnNewButton = new JButton("SEARCH");
		btnNewButton.addActionListener(new ActionListener() {
		public void actionPerformed(ActionEvent arg0) {
			
			String search_query = null;
			
			String searchID = textField.getText();
			String[] searchIDSpiltArry = searchID.split("0", 0);
			String searchIDSpilt = searchIDSpiltArry[0];
			
			System.out.println(searchIDSpilt);
			switch(searchIDSpilt){
			case "YFM":
				search_query = "Select User_id, Status, Full_name, NIC, DOB, Gender, TP_number, Email, Address  FROM yf_deails WHERE User_id = '"+searchID+"';";
				break;
			case "ARD":
				search_query = "Select User_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address  FROM arduino_and_3d_printing WHERE User_id = '"+searchID+"';";
				break;
			case "BE":
				search_query = "Select User_id, Club_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address  FROM business_english WHERE User_id = '"+searchID+"';";
				break;
			case "EE":
				search_query = "Select User_id, Club_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address  FROM english_enrichment WHERE User_id = '"+searchID+"';";
				break;
			case "SP":
				search_query = "Select User_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address, Gaurdian_name, Gaurdian_contact  FROM scratch_programming WHERE User_id = '"+searchID+"';";
				break;
			case "SC":
				search_query = "Select User_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address, Gaurdian_name, Gaurdian_contact  FROM scrabble_club WHERE User_id = '"+searchID+"';";
				break;
			case "RC":
				search_query = "Select User_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address, Gaurdian_name, Gaurdian_contact  FROM reading_club WHERE User_id = '"+searchID+"';";
				break;
			default:
				JOptionPane.showMessageDialog(textField, "User status updated to Blacklist", "", JOptionPane.ERROR_MESSAGE);
			}
			
	        try {
				Connection con = dbcon.getConnection();
				System.out.println(search_query);
				PreparedStatement pst = con.prepareStatement(search_query);
				ResultSet rs = pst.executeQuery();
				table.setModel(DbUtils.resultSetToTableModel(rs));
			} catch (ClassNotFoundException e1) {
				e1.printStackTrace();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			
			
		}
		});
		btnNewButton.setBounds(47, 140, 89, 23);
		panel.add(btnNewButton);
		
		JLabel lblViewAll = new JLabel("View All");
		lblViewAll.setBounds(66, 222, 64, 26);
		panel.add(lblViewAll);
		
		String[] search_options = new String[] {"YF Members","Club Members","Course students","Visitors"};
		JComboBox comboBox = new JComboBox(search_options);
		comboBox.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent event) {
				JComboBox<String> combo = ((JComboBox<String>)event.getSource());
				String select_viewall = (String) combo.getSelectedItem();
				
				System.out.println(select_viewall);
				
				String view_query;
				
				switch(select_viewall){
				case "YF Members":
					view_query = "Select User_id, Status, Full_name, NIC, DOB, Gender, TP_number, Email, Address  FROM yf_deails;";
					break;
				case "Club Members":
					view_query = "Select User_id, Status, Club_id, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address  FROM club_member;";
					break;
				case "Course students":
					view_query = "Select User_id, Status, Full_name, NIC, DOB, Age, Gender, TP_number, Email, Address, Gaurdian_name, Gaurdian_contact  FROM course_student;";
					break;
				case "Visitors":
					view_query = "Select Full_name, NIC, TP_number, Purpopse  FROM visitor;";
					break;
				default:
					view_query = null;
				}
				
				try {
					Connection con = dbcon.getConnection();
					System.out.println(view_query);
					PreparedStatement pst = con.prepareStatement(view_query);
					ResultSet rs = pst.executeQuery();
					System.out.println(rs);
					table.setModel(DbUtils.resultSetToTableModel(rs));
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			
		});
		comboBox.setBounds(26, 259, 136, 26);
		panel.add(comboBox);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		panel_1.setBounds(145, 0, 547, 428);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {
			}
		});
		scrollPane.setBounds(56, 11, 481, 352);
		panel_1.add(scrollPane);
		
		table = new JTable();
		ListSelectionModel model = table.getSelectionModel();
		int selectRowIndex = table.getSelectedRow();
		System.out.println(selectRowIndex);

		scrollPane.setViewportView(table);
		//JScrollPane scrollPanel = new JScrollPane(table);
		
		JButton btnActive = new JButton("Active");
		btnActive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String actionButtonQueryActive = null;
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectRowIndex = table.getSelectedRow();

				String userid = model.getValueAt(selectRowIndex, 0).toString();
				String[] userIDSpiltArry = userid.split("0", 0);
				String userIDSpilt = userIDSpiltArry[0];
								
				switch(userIDSpilt){
				case "YFM":
					actionButtonQueryActive = "UPDATE yf_deails SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "CM":
					actionButtonQueryActive = "UPDATE club_member SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "ARD":
					actionButtonQueryActive = "UPDATE arduino_and_3d_printing SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "BE":
					actionButtonQueryActive = "UPDATE business_english SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "EE":
					actionButtonQueryActive = "UPDATE english_enrichment SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "SC":
					actionButtonQueryActive = "UPDATE scrabble_club SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				case "SP":
					actionButtonQueryActive = "UPDATE scratch_programming SET Status = 'Active' WHERE User_id ='"+userid+"';";
					break;
				default:
					System.out.println("Select a row");
				
				}
				
				try {
					Connection con = dbcon.getConnection();
					System.out.println(actionButtonQueryActive);
					PreparedStatement pst = con.prepareStatement(actionButtonQueryActive);
					pst.executeUpdate();
					System.out.println("updated");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				model.setValueAt("Active",selectRowIndex, 1);
				JOptionPane.showMessageDialog(textField, "User status updated to Active", "Status Update", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnActive.setBounds(133, 382, 89, 23);
		panel_1.add(btnActive);
		
		JButton btnDeactive = new JButton("Deactive");
		btnDeactive.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String actionButtonQueryDeactive = null;
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectRowIndex = table.getSelectedRow();
				System.out.println(selectRowIndex);
				String userid = model.getValueAt(selectRowIndex, 0).toString();
				System.out.println(userid);
				String[] userIDSpiltArry = userid.split("0", 0);
				String userIDSpilt = userIDSpiltArry[0];
				System.out.println(userIDSpilt);
				
				switch(userIDSpilt){
				case "YFM":
					actionButtonQueryDeactive = "UPDATE yf_deails SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "CM":
					actionButtonQueryDeactive = "UPDATE club_member SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "ARD":
					actionButtonQueryDeactive = "UPDATE arduino_and_3d_printing SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "BE":
					actionButtonQueryDeactive = "UPDATE business_english SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "EE":
					actionButtonQueryDeactive = "UPDATE english_enrichment SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "SC":
					actionButtonQueryDeactive = "UPDATE scrabble_club SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				case "SP":
					actionButtonQueryDeactive = "UPDATE scratch_programming SET Status = 'Deactive' WHERE User_id ='"+userid+"';";
					break;
				default:
					System.out.println("Select a row");
				
				}
				
				try {
					Connection con = dbcon.getConnection();
					System.out.println(actionButtonQueryDeactive);
					PreparedStatement pst = con.prepareStatement(actionButtonQueryDeactive);
					pst.executeUpdate();
					System.out.println("updated");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				model.setValueAt("Deactive",selectRowIndex, 1);
				JOptionPane.showMessageDialog(textField, "User status updated to Deactive", "Status Update", JOptionPane.INFORMATION_MESSAGE);
			}
		});
		btnDeactive.setBounds(263, 382, 89, 23);
		panel_1.add(btnDeactive);
		
		JButton btnBL = new JButton("BlackList");
		btnBL.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				String actionButtonQueryBL = null;
				
				DefaultTableModel model = (DefaultTableModel)table.getModel();
				int selectRowIndex = table.getSelectedRow();
				System.out.println(selectRowIndex);
				String userid = model.getValueAt(selectRowIndex, 0).toString();
				System.out.println(userid);
				String[] userIDSpiltArry = userid.split("0", 0);
				String userIDSpilt = userIDSpiltArry[0];
				System.out.println(userIDSpilt);
				
				switch(userIDSpilt){
				case "YFM":
					actionButtonQueryBL = "UPDATE yf_deails SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "CM":
					actionButtonQueryBL = "UPDATE club_member SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "ARD":
					actionButtonQueryBL = "UPDATE arduino_and_3d_printing SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "BE":
					actionButtonQueryBL = "UPDATE business_english SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "EE":
					actionButtonQueryBL = "UPDATE english_enrichment SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "SC":
					actionButtonQueryBL = "UPDATE scrabble_club SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				case "SP":
					actionButtonQueryBL = "UPDATE scratch_programming SET Status = 'BlackList' WHERE User_id ='"+userid+"';";
					break;
				default:
					System.out.println("Select a row");
				
				}
				
				try {
					Connection con = dbcon.getConnection();
					System.out.println(actionButtonQueryBL);
					PreparedStatement pst = con.prepareStatement(actionButtonQueryBL);
					pst.executeUpdate();
					System.out.println("updated");
				} catch (ClassNotFoundException e1) {
					e1.printStackTrace();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				
				model.setValueAt("BlackList",selectRowIndex, 1);
				JOptionPane.showMessageDialog(textField, "User status updated to Blacklist", "Status Update", JOptionPane.INFORMATION_MESSAGE); 
				
			}
		});
		btnBL.setBounds(393, 382, 89, 23);
		panel_1.add(btnBL);
	}
}
		



