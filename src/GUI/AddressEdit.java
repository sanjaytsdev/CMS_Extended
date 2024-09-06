package GUI;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import Domain.DTO.AddressDTO;
import Presenter.AddressManager;


public class AddressEdit extends JFrame implements ActionListener{

    private JList<String> addrList;
    private DefaultListModel<String> addrListModel;
    private JTextField addressField;
    private JButton addButton, updateButton, deleteButton, submitButton; 
    private JLabel outputLabel = new JLabel("");
    private int cid; 

    public AddressEdit(String mode, int cid) throws Exception {
        super("Address Update Form - " + mode);
        this.cid = cid;

        setLayout(new BorderLayout());

        addrListModel = new DefaultListModel<>();
        addrList = new JList<>(addrListModel);
        loadAddress(cid);

        addressField = new JTextField(20);
        addButton = new JButton("Add Address");
        updateButton = new JButton("Update Address");
        deleteButton = new JButton("Delete Address");
        submitButton = new JButton("Submit Changes"); 
        add(new JScrollPane(addrList), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel();
        inputPanel.add(addressField);
        inputPanel.add(addButton);
        inputPanel.add(updateButton);
        inputPanel.add(deleteButton);
        inputPanel.add(submitButton);
        add(inputPanel, BorderLayout.SOUTH);
        add(outputLabel, BorderLayout.NORTH);

        addButton.addActionListener(this);
        updateButton.addActionListener(this);
        deleteButton.addActionListener(this);
        submitButton.addActionListener(this); 

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadAddress(int cid) throws Exception {
        List<AddressDTO> addr = AddressManager.getAddress(cid);
        addrListModel.clear();
        for (AddressDTO address : addr) {
            addrListModel.addElement(address.getAddr());  
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String addresss = addressField.getText();
        String selectedAddr = addrList.getSelectedValue();

        try {
            if (cmd.equals("Add Address")) {
                if (!addresss.isEmpty()) {
                    AddressDTO newAddr = new AddressDTO();
                    newAddr.setAddr(addresss);
                    newAddr.setCid(cid);
                    boolean result = AddressManager.addAddress(newAddr);
                    if (result) {
                        GUIHelper.MessageBox("Address added successfully");
                        addressField.setText(""); 
                    } else {
                       GUIHelper.MessageBox("Failed to add Address");
                    }
                    loadAddress(cid);  
                } else {
                    GUIHelper.MessageBox("Please enter an Address to add.");
                }

            } else if (cmd.equals("Update Address")) {
                if (selectedAddr != null && !addresss.isEmpty()) {
                    AddressDTO updatedAddr = new AddressDTO();
                    updatedAddr.setAddr(addresss);
                    updatedAddr.setCid(cid);
                    boolean result = AddressManager.updateAddress(updatedAddr, selectedAddr);
                    if (result) {
                        GUIHelper.MessageBox("Address updated successfully.");
                        addressField.setText(""); 
                    } else {
                        GUIHelper.MessageBox("Failed to delete Address");
                    }
                    loadAddress(cid);  
                } else {
                    GUIHelper.MessageBox("Please select a Address to update");
                }

            } else if (cmd.equals("Delete Address")) {
                if (selectedAddr != null) {
                    boolean result = AddressManager.deleteAddress(cid, selectedAddr);
                    if (result) {
                        GUIHelper.MessageBox("Address deleted successfully.");
                    } else {
                        GUIHelper.MessageBox("Failed to delete Address");
                    }
                    loadAddress(cid);  
                } else {
                    GUIHelper.MessageBox("Please select a address to delete");
                }

            } else if (cmd.equals("Submit Changes")) {
                GUIHelper.MessageBox("Changes submitted!");
                dispose(); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            GUIHelper.MessageBox("An error occured");
        }
    }

}
