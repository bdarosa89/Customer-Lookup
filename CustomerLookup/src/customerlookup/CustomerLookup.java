/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package customerlookup;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.*;


/**
 *
 * @author Ben
 */

public class CustomerLookup extends JFrame implements ActionListener{

    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    
    private JTextField searchField;
    private final JButton searchButton;
    private  JTextArea resultsBox;
    private JComboBox searchLabel;
    private JComboBox searchBox2;
    CustomerLookup(){//Set up swing environment
        GridBagConstraints layoutConst;
        String[] searchParams = new String[]{"By Phone", "By Email"};
        String[] searchOrDestroy = new String[]{"Search","Delete"};
        setTitle("Customer Lookup");
        searchLabel = new JComboBox(searchParams);
        searchField = new JTextField(15);
        searchButton = new JButton("Go");
        searchButton.addActionListener(this);
        resultsBox = new JTextArea(15,30);
        resultsBox.setLineWrap(true);
        searchBox2 = new JComboBox(searchOrDestroy);
        
        setLayout(new GridBagLayout());
        
        layoutConst = new GridBagConstraints();
        layoutConst.gridx=1;
        layoutConst.gridy=0;
        layoutConst.insets = new Insets(0,0,0,0);
        add(searchLabel, layoutConst);
       
        layoutConst = new GridBagConstraints();
        layoutConst.gridx=2;
        layoutConst.gridy=0;
        layoutConst.insets = new Insets(0,0,0,0);
        add(searchField, layoutConst);
        
        layoutConst = new GridBagConstraints();
        layoutConst.gridx=3;
        layoutConst.gridy=0;
        layoutConst.insets = new Insets(0,0,0,0);
        add(searchButton, layoutConst);
        
        layoutConst = new GridBagConstraints();
        layoutConst.gridx=1;
        layoutConst.gridy=1;
        layoutConst.insets = new Insets(10,10,10,10);
        add(resultsBox, layoutConst);
        
        layoutConst = new GridBagConstraints();
        layoutConst.gridx=0;
        layoutConst.gridy=0;
        layoutConst.insets = new Insets(0,0,0,0);
        add(searchBox2, layoutConst);
    }

    /**
     *
     * @param e
     */
    
    /**
     * @param e
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        //Set the frame.
        CustomerLookup myFrame = new CustomerLookup();
        myFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        myFrame.pack();
        myFrame.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e){//On Go button Press.
        resultsBox.setText(null);
        String searchStr = StringEscapeUtils.escapeSql(searchField.getText());//escape the sql string to prevent injection
        Object searchParms = (String)searchLabel.getSelectedItem();
        Object searchOrDestroy = (String)searchBox2.getSelectedItem();
        CustomerInfo data = new CustomerInfo();
        data.getInfo(searchStr, searchParms, searchOrDestroy);//Use parameters to determine SQL statement.
        if (searchOrDestroy.equals("Search")){//If searched, display the results.
        resultsBox.append(data.getName()+"\n");
        resultsBox.append(data.getAddress()+"\n");
        resultsBox.append(data.getNumber()+"\n");
        resultsBox.append(data.getEmail()+"\n");
        }
        if (searchOrDestroy.equals("Delete")){//If deleting rows notify user how many.
            resultsBox.append(data.getRowsDeleted()+" Rows deleted.");
        }
    }
}
