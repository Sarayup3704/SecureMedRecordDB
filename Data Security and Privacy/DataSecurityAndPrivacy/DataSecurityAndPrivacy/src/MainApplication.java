import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.sql.*;

public class MainApplication {

    private static JFrame frmSecurityprivacy;
    private JTextField textField;
    private JPasswordField passwordField;
    private JTable healthDataTable;
    private JPanel cardPanel;
    private CardLayout cardLayout;
    private JLabel lblGroup;
    private JComboBox comboBox;
    private JButton btnLogin;
    private JButton btnNewButton;
    private JLabel lblNewLabel_1;
    private JLabel lblQuery;
    private JButton btnRun;
    private JScrollPane scrollPane;
    private static String Present_group;
    private JButton btnLogout;

<<<<<<< HEAD
    // JDBC URL, username, and password of MySQL server
=======
    
>>>>>>> 943bca12c730ea0b5afbcbea5a37211c2ee42c1b
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/security";
    private static final String DB_USER = "manager";
    private static final String DB_PASSWORD = "Chandu@30";
    private JButton btnLogin_1;
    private JButton btnRegister;
    private JTextField textField_1;
    private JTable table;
    private static JTextArea textArea_1;
    private JScrollPane scrollPane_1;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    MainApplication window = new MainApplication();
                    window.frmSecurityprivacy.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public MainApplication() {
        initialize();
    }

    private void initialize() {
        frmSecurityprivacy = new JFrame();
        frmSecurityprivacy.setTitle("Security-Privacy");
        frmSecurityprivacy.setBounds(100, 100, 631, 393);
        frmSecurityprivacy.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frmSecurityprivacy.getContentPane().setLayout(new BorderLayout());

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);
        
        JavaConnection jcon = new JavaConnection();
        jcon.DBdesign();

        // Add login panel
        JPanel loginPanel = createLoginPanel();
        cardPanel.add(loginPanel, "login");

        // Add an empty panel for the data display (to be populated after login)
        cardPanel.add(new JPanel(), "data");

        frmSecurityprivacy.getContentPane().add(cardPanel, BorderLayout.CENTER);
    }

    private JPanel createLoginPanel() {
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(null);

        JLabel lblNewLabel = new JLabel("Username");
        lblNewLabel.setBounds(216, 84, 97, 15);
        loginPanel.add(lblNewLabel);

        JLabel lblLastName = new JLabel("Password");
        lblLastName.setBounds(216, 126, 87, 15);
        loginPanel.add(lblLastName);

        textField = new JTextField();
        textField.setBounds(334, 82, 114, 19);
        loginPanel.add(textField);
        textField.setColumns(10);

        passwordField = new JPasswordField();
        passwordField.setBounds(334, 124, 114, 19);
        loginPanel.add(passwordField);

        btnLogin = new JButton("Login");
        btnLogin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                String username = textField.getText();
                String password = new String(passwordField.getPassword());
                String hashpassword = hashString(password);
                boolean isAuthenticated = authenticateUser(username, hashpassword);

                if (isAuthenticated) {
                	textField.setVisible(false);
                	passwordField.setVisible(false);
                	lblNewLabel.setVisible(false);
                	lblLastName.setVisible(false);
                	btnLogin.setVisible(false);
                	btnNewButton.setVisible(false);
                	lblNewLabel_1.setText("Dashboard");
                	lblQuery.setVisible(true);
                	btnRun.setVisible(true);
                	textField_1.setVisible(true);
                	textArea_1.setVisible(true);
                	scrollPane_1.setVisible(true);
                	btnLogout.setVisible(true);
                	
                	String grouptype = getGroupNameByUsernameAndPassword(username, hashpassword);
                    if (grouptype.equals("R")) {
                    	//System.out.println("in r group");
                    	Present_group = "R";
                    } else if(grouptype.equals("H")) {
                    	System.out.println("in h group");
                        Present_group = "H";
                    }
                } else {
                    JOptionPane.showMessageDialog(frmSecurityprivacy, "Login failed", "Login Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnLogin.setBounds(368, 162, 80, 19);
        loginPanel.add(btnLogin);
        
        btnNewButton = new JButton("Create an Account");
        btnNewButton.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		lblNewLabel_1.setText("Register");
        		btnLogin.setVisible(false);
        		lblGroup.setVisible(true);
        		comboBox.setVisible(true);
        		btnLogin_1.setVisible(true);
        		btnRegister.setVisible(true);
        		btnNewButton.setVisible(false);
        	}
        });
        btnNewButton.setBounds(243, 193, 167, 19);
        loginPanel.add(btnNewButton);
        
        lblNewLabel_1 = new JLabel("Login");
        lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 20));
        lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
        lblNewLabel_1.setBounds(243, 26, 185, 25);
        loginPanel.add(lblNewLabel_1);
        
        lblGroup = new JLabel("Group");
        lblGroup.setBounds(216, 164, 87, 15);
        loginPanel.add(lblGroup);
        lblGroup.setVisible(false);
        
        comboBox = new JComboBox();
        comboBox.setBounds(334, 161, 114, 20);
        comboBox.addItem("R");
        comboBox.addItem("H");
        loginPanel.add(comboBox);
        
        btnLogin_1 = new JButton("Login");
        btnLogin_1.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		lblNewLabel_1.setText("Login");
        		btnLogin.setVisible(true);
        		lblGroup.setVisible(false);
        		comboBox.setVisible(false);
        		btnLogin_1.setVisible(false);
        		btnRegister.setVisible(false);
        		btnNewButton.setVisible(true);
        		
        	}
        });
        btnLogin_1.setBounds(216, 193, 80, 19);
        loginPanel.add(btnLogin_1);
        btnLogin_1.setVisible(false);
        
        btnRegister = new JButton("Register");
        btnRegister.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		String username = textField.getText();;
        		String password = new String(passwordField.getPassword());;
        		String hashpassword = hashString(password);
        		String group = (String) comboBox.getSelectedItem();
        		if(username.isEmpty() || password.isEmpty()) {
        			JOptionPane.showMessageDialog(frmSecurityprivacy, "Please provide all information", "Registration Error", JOptionPane.ERROR_MESSAGE);
        		}
        		else {
        			registeruser(username, hashpassword, group);
        		}
        		
        	}
        });
        btnRegister.setBounds(351, 193, 97, 19);
        loginPanel.add(btnRegister);
        
        lblQuery = new JLabel("Query");
        lblQuery.setBounds(37, 95, 70, 15);
        loginPanel.add(lblQuery);
        lblQuery.setVisible(false);
        
        btnRun = new JButton("Run");
        btnRun.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		textArea_1.setText("");
        		String query = textField_1.getText();
        		System.out.println(query);
        		executeQueryAndRetrieveString(query);
//        		String data = executeQueryAndRetrieveString(query);
//        		System.out.println(data);
//        		textArea_1.append(data);
        	}
        });
        btnRun.setBounds(532, 92, 87, 20);
        btnRun.setVisible(false);
        
        loginPanel.add(btnRun);
        
        textField_1 = new JTextField();
        textField_1.setBounds(101, 93, 390, 19);
        loginPanel.add(textField_1);
        textField_1.setColumns(10);
        
        scrollPane_1 = new JScrollPane();
        scrollPane_1.setBounds(47, 126, 572, 229);
        loginPanel.add(scrollPane_1);
        
        textArea_1 = new JTextArea();
        scrollPane_1.setViewportView(textArea_1);
        textArea_1.setEditable(false);
        
        btnLogout = new JButton("Logout");
        btnLogout.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent arg0) {
        		textField.setVisible(true);
            	passwordField.setVisible(true);
            	lblNewLabel.setVisible(true);
            	lblLastName.setVisible(true);
            	btnLogin.setVisible(true);
            	btnNewButton.setVisible(true);
            	lblNewLabel_1.setText("Login");
            	lblQuery.setVisible(false);
            	btnRun.setVisible(false);
            	textField_1.setVisible(false);
            	textArea_1.setVisible(false);
            	scrollPane_1.setVisible(false);
            	btnLogout.setVisible(false);
            	textField.setText("");
            	passwordField.setText("");
            	textField_1.setText("");
            	textArea_1.setText("");
        		
        		
        		
        		
        	}
        });
        btnLogout.setBounds(532, 12, 87, 25);
        loginPanel.add(btnLogout);
        btnLogout.setVisible(false);
        
        
        comboBox.setVisible(false);
        btnRegister.setVisible(false);
        textField_1.setVisible(false);
        textArea_1.setVisible(false);
        scrollPane_1.setVisible(false);
        
        return loginPanel;
    }

    
    private void registeruser(String username, String password, String group) {
    	try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String insertQuery = "INSERT INTO Userinfo (username, password, groupname) VALUES (?, ?, ?)";

            try (PreparedStatement preparedStatement = connection.prepareStatement(insertQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, group);

                int rowsAffected = preparedStatement.executeUpdate();

                if (rowsAffected > 0) {
                	JOptionPane.showMessageDialog(frmSecurityprivacy, "Registeration success, please login now", "Registration Success", JOptionPane.INFORMATION_MESSAGE);
                	lblNewLabel_1.setText("Login");
            		btnLogin.setVisible(true);
            		lblGroup.setVisible(false);
            		comboBox.setVisible(false);
            		btnLogin_1.setVisible(false);
            		btnRegister.setVisible(false);
            		btnNewButton.setVisible(true);
                } else {
                    System.out.println("Failed to insert data.");
                    JOptionPane.showMessageDialog(frmSecurityprivacy, "Error when register user", "Registration Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLIntegrityConstraintViolationException e) {
            	JOptionPane.showMessageDialog(frmSecurityprivacy, "User already exist try with different username", "Registration Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
        	JOptionPane.showMessageDialog(frmSecurityprivacy, "Error: "+e, "Registration Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private boolean authenticateUser(String username, String hashedPassword) {
        String query = "SELECT * FROM Userinfo WHERE username = ? AND password = ?";
        try (
                Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
                PreparedStatement preparedStatement = connection.prepareStatement(query);
        ) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, hashedPassword);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                return resultSet.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    
    
    public static String getGroupNameByUsernameAndPassword(String username, String password) {
        String groupName = null;

        try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
            String selectQuery = "SELECT groupname FROM Userinfo WHERE username = ? AND password = ?";

            try (PreparedStatement preparedStatement = connection.prepareStatement(selectQuery)) {
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);

                try (ResultSet resultSet = preparedStatement.executeQuery()) {
                    if (resultSet.next()) {
                        groupName = resultSet.getString("groupname");
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return groupName;
    }


    public static String hashString(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hashedBytes = md.digest(input.getBytes());

            StringBuilder sb = new StringBuilder();
            for (byte b : hashedBytes) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }
    
    public static void executeQueryAndRetrieveString(String queryString) {
        String result = "";
        String queryStringhash = queryString.replace("healthcare", "healthcare_hash");
        
       if(queryString.toLowerCase().contains("insert") && Present_group.equals("R")) {
    	   JOptionPane.showMessageDialog(frmSecurityprivacy, "Group R users don't have permission to insert data", "Access denied", JOptionPane.ERROR_MESSAGE);
       }
       else if(queryString.toLowerCase().contains("insert") && Present_group.equals("H")) {
    	   textArea_1.setText("");
//    	   textArea_1.append(queryString);
    	   textArea_1.append("\n");
    	   queryString = queryString.toLowerCase();
    	   try {
	    	   String[] splitquery = queryString.split("values");
	    	   String first_query = splitquery[0];
	    	   String second_query = splitquery[1];
	    	   second_query = second_query.replace("\"", "");
	    	   second_query = second_query.replace(", ", ",");
	    	   second_query = second_query.replace("(", "");
	    	   second_query = second_query.replace(")", "");
	    	   String[] all_items = second_query.split(",");
	    	   String first_name = all_items[0];
	    	   String last_name = all_items[1];
	    	   String gender = all_items[2];
	    	   String age = all_items[3];
	    	   String weight = all_items[4];
	    	   String height = all_items[5];
	    	   String health_history = all_items[6];
	    	       	   
	    	   String newqueryString = first_query+" values (\""+ JavaConnection.hashString(first_name)+"\", \""+JavaConnection.hashString(last_name)+"\", \""+JavaConnection.hashString(gender)+"\", \""+JavaConnection.hashString(age)+"\", \""+JavaConnection.hashString(weight)+"\", \""+JavaConnection.hashString(height)+"\", \""+JavaConnection.hashString(health_history)+"\");";
	    	   newqueryString = newqueryString.replace("healthcare", "healthcare_hash");
	    	   
	    	   System.out.println(newqueryString);
	    	   textArea_1.append("\n");
//	    	   textArea_1.append(newqueryString);
	    	   try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD);
	  	             Statement statement = connection.createStatement()) {
	  	            int rowsAffected = statement.executeUpdate(queryString);
	  	            int hash_rowsAffected = statement.executeUpdate(newqueryString);
	
	  	          textArea_1.append("Rows affected: " + rowsAffected);
	  	        textArea_1.append("Insert query executed successfully.");
	
		  	    } catch (SQLException e) {
		  	            e.printStackTrace();
		  	    }
    	   
    	   } catch(Exception e) {
    		   textArea_1.append(e.toString());
    	   }
       }
       else if(queryString.toLowerCase().contains("select")) {
	        if(queryString.contains("first_name") && Present_group.equals("R")) {
	        	JOptionPane.showMessageDialog(frmSecurityprivacy, "Group R users don't have permission to access the first_name", "Access denied", JOptionPane.ERROR_MESSAGE);
	        }
	        else if(queryString.contains("last_name") && Present_group.equals("R")) {
	        	JOptionPane.showMessageDialog(frmSecurityprivacy, "Group R users don't have permission to access the last_name", "Access denied", JOptionPane.ERROR_MESSAGE);
	        }
	        else {
	        	try (Connection connection = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASSWORD)) {
	        	    try (PreparedStatement preparedStatement1 = connection.prepareStatement(queryString);
	        	         PreparedStatement preparedStatement2 = connection.prepareStatement(queryStringhash)) {
	        	        
	        	        try (ResultSet resultSet1 = preparedStatement1.executeQuery(); 
	        	             ResultSet resultSet2 = preparedStatement2.executeQuery()) {
	        	        	if(Present_group.equals("R")) {
	        	        		if(queryString.contains("*")) {
	        	        			textArea_1.append("ID\tgender\tage\tweight\theight\thealth_history");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("id")) {
	        	        			textArea_1.append("ID\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("gender")) {
	        	        			textArea_1.append("Gender\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("age")) {
	        	        			textArea_1.append("age\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("weight")) {
	        	        			textArea_1.append("weight\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("height")) {
	        	        			textArea_1.append("height\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("health_history")) {
	        	        			textArea_1.append("health_history\t");
	        	        		}
	        	        		
	        	        	}
	        	        	else if(Present_group.equals("H")) {
	        	        		if(queryString.contains("*")) {
	        	        			textArea_1.append("ID\tfirst_name\tlast_name\tgender\tage\tweight\theight\thealth_history");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("id")) {
	        	        			textArea_1.append("ID\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("first_name")) {
	        	        			textArea_1.append("first_name\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("last_name")) {
	        	        			textArea_1.append("last_name\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("gender")) {
	        	        			textArea_1.append("gender\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("age")) {
	        	        			textArea_1.append("age\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("weight")) {
	        	        			textArea_1.append("weight\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("height")) {
	        	        			textArea_1.append("height\t");
	        	        		}
	        	        		if(queryString.toLowerCase().contains("health_history")) {
	        	        			textArea_1.append("health_history\t");
	        	        		}
	        	        	}
	        	            int flag = 0;
	        	            while (resultSet1.next() && resultSet2.next()) {
	        	                try {
	        	                    String id_value = resultSet1.getString("id");
	        	                    result = result + id_value + "\t";
	        	                } catch (Exception e) {
	        	                    result = result + "";
	        	                }
		                    	try {
		                    		if(Present_group.equals("H")) {
		                    			String firstname_value = resultSet1.getString("first_name");
		                    			String hash_firstname = hashString(firstname_value);
		                    			String firstname_hash_value = resultSet2.getString("first_name");
		                    			if(hash_firstname.equals(firstname_hash_value)) {
		                    				result = result+firstname_value+"\t";
		                    			}
		                    			else {
		                    				flag=1;
		                    				break;
		                    			}
		                    		}
		                    		else if(Present_group.equals("R"))
		                    		{
		                    			result = result+"";
		                    		}
		                    		
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		if(Present_group.equals("H")) {
		                    			String lastname_value = resultSet1.getString("last_name");
		                    			String hash_lastname = hashString(lastname_value);
		                    			String lastname_hash_value = resultSet2.getString("last_name");
		                    			if(hash_lastname.equals(lastname_hash_value)) {
		                    				result = result+lastname_value+"\t";
		                    			}
		                    			else {
		                    				flag=1;
		                    				System.out.println("change 2");
		                    				break;
		                    			}
		                    		}
		                    		else if(Present_group.equals("R")) {
		                    			result = result+"";
		                    		}
		                    		
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		String gender_value = resultSet1.getString("gender");
		                    		String hash_gender = hashString(gender_value);
		                			String gender_hash_value = resultSet2.getString("gender");
		                			if(hash_gender.equals(gender_hash_value)) {
		                				result = result+gender_value+"\t";
		                			}
		                			else {
		                				flag=1;
		                				System.out.println("change 3");
		                				break;
		                			}
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		String age_value = resultSet1.getString("age");
		                    		String hash_age = hashString(age_value);
		                			String age_hash_value = resultSet2.getString("age");
		                			if(hash_age.equals(age_hash_value)) {
		                				result = result+age_value+"\t";
		                			}
		                			else {
		                				flag=1;
		                				break;
		                			}
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		String weight_value = resultSet1.getString("weight");
		                    		String hash_weight = hashString(weight_value);
		                			String weight_hash_value = resultSet2.getString("weight");
		                			if(hash_weight.equals(weight_hash_value)) {
		                				result = result+weight_value+"\t";
		                			}
		                			else {
		                				flag=1;
		                				break;
		                			}
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		String height_value = resultSet1.getString("height");
		                    		String hash_height = hashString(height_value);
		                			String height_hash_value = resultSet2.getString("height");
		                			if(hash_height.equals(height_hash_value)) {
		                				result = result+height_value+"\t";
		                			}
		                			else {
		                				flag=1;
		                				System.out.println("change 5");
		                				break;
		                			}
		                    	} catch(Exception e) {
		                    		result = result+"";
		                    	}
		                    	try {
		                    		String health_history_value = resultSet1.getString("health_history");
		                    		String hash_health_history = hashString(health_history_value);
		                			String firstname_hash_value = resultSet2.getString("health_history");
		                			if(hash_health_history.equals(firstname_hash_value)) {
		                				result = result+health_history_value+"\n";
		                			}
		                			else {
		                				flag=1;
		                				System.out.println("change 6");
		                				break;
		                			}
		                    		
		                    	} catch(Exception e) {
		                    		result = result+"\n";
		                    	}
		                    	
		                    }
	        	            System.out.println(result);
	        	            textArea_1.append("\n\n");
	                    	textArea_1.append(result);
	                    	
		                    if(flag == 1) {
		                    	JOptionPane.showMessageDialog(frmSecurityprivacy, "Database value modified by someone", "Database modification alert", JOptionPane.ERROR_MESSAGE);
		                    }
		                }
		            }
		        } catch (SQLException e) {
		        	JOptionPane.showMessageDialog(frmSecurityprivacy, "Error : "+e, "Error", JOptionPane.ERROR_MESSAGE);
		        }
	        }
       }

    }
}
