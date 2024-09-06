package GUI;

import javax.swing.*;
import Domain.DTO.PhoneDTO;
import Presenter.PhoneManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.List;

public class PhoneEdit extends JFrame implements ActionListener {

    private JList<String> phoneList;
    private DefaultListModel<String> phoneListModel;
    private JTextField phoneNumberField;
    private JButton addButton, updateButton, deleteButton, submitButton; 
    private JLabel outputLabel = new JLabel("");
    private int contactId; 

    public PhoneEdit(String mode, int contactId) throws Exception {
        super("Phone Update Form - " + mode);
        this.contactId = contactId;

        setLayout(new BorderLayout());

        phoneListModel = new DefaultListModel<>();
        phoneList = new JList<>(phoneListModel);
        loadPhones(contactId);

        phoneNumberField = new JTextField(20);
        addButton = new JButton("Add Phone");
        updateButton = new JButton("Update Phone");
        deleteButton = new JButton("Delete Phone");
        submitButton = new JButton("Submit Changes"); 

        add(new JScrollPane(phoneList), BorderLayout.CENTER);
        JPanel inputPanel = new JPanel();
        inputPanel.add(phoneNumberField);
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

    private void loadPhones(int contactId) throws Exception {
        List<PhoneDTO> phones = PhoneManager.getPhones(contactId);
        phoneListModel.clear();
        for (PhoneDTO phone : phones) {
            phoneListModel.addElement(phone.getPh());  
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        String phoneNumber = phoneNumberField.getText();
        String selectedPhone = phoneList.getSelectedValue();

        try {
            if (cmd.equals("Add Phone")) {
                if (!phoneNumber.isEmpty()) {
                    PhoneDTO newPhone = new PhoneDTO();
                    newPhone.setPh(phoneNumber);
                    newPhone.setCid(contactId);
                    boolean result = PhoneManager.addPhone(newPhone);
                    if (result) {
                       GUIHelper.MessageBox("Phone number added successfully.");
                        phoneNumberField.setText(""); 
                    } else {
                       GUIHelper.MessageBox("Failed to add phone number");
                    }
                    loadPhones(contactId);  
                } else {
                   GUIHelper.MessageBox("Please enter a phone number to add.");
                }

            } else if (cmd.equals("Update Phone")) {
                if (selectedPhone != null && !phoneNumber.isEmpty()) {
                    PhoneDTO updatedPhone = new PhoneDTO();
                    updatedPhone.setPh(phoneNumber);
                    updatedPhone.setCid(contactId);
                    boolean result = PhoneManager.updatePhone(updatedPhone, selectedPhone);
                    if (result) {
                        GUIHelper.MessageBox("Phone number updated successfully.");
                        phoneNumberField.setText(""); 
                    } else {
                        GUIHelper.MessageBox("Failed to delete phone number");
                    }
                    loadPhones(contactId);  
                } else {
                   GUIHelper.MessageBox("Please select a number to update.");
                }

            } else if (cmd.equals("Delete Phone")) {
                if (selectedPhone != null) {
                    boolean result = PhoneManager.deletePhone(contactId, selectedPhone);
                    if (result) {
                        GUIHelper.MessageBox("Phone number deleted successfully.");
                    } else {
                        GUIHelper.MessageBox("Failed to delete phone number");
                    }
                    loadPhones(contactId);  
                } else {
                    GUIHelper.MessageBox("Please select a phone number to delete.");
                }

            } else if (cmd.equals("Submit Changes")) {
                GUIHelper.MessageBox("Changes submitted!");
                dispose(); 
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            outputLabel.setText("An error occurred: " + ex.getMessage());
        }
    }

}
