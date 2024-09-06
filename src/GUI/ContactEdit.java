package GUI;

import javax.swing.*;
import Domain.DTO.ContactDTO;
import Presenter.ContactManager;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;
import javax.swing.text.NumberFormatter;

class ContactEdit extends JFrame implements ActionListener {

    private JLabel contactIdLabel;
    private JLabel contactgrpIdLabel;
    private JLabel contactNameLabel;
    private JLabel contactPhoneLabel;
    private JLabel contactAddrLabel;
    private JFormattedTextField contactIdText;
    private JTextField contactNameText;
    private JButton newPhoneButton;
    private JButton newAddrButton;
    private JButton groupsButton;
    private JButton submitButton;
    private JButton listButton;


    public ContactEdit(String mode) {
        super("Contact Edit Form - " + mode);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        contactIdLabel = new JLabel("Contact id :");
        contactIdText = new JFormattedTextField(numberFormat);
        contactgrpIdLabel = new JLabel("Contact Group:");
        groupsButton = new JButton("Update Groups");
        contactNameLabel = new JLabel("Contact Name:");
        contactNameText = new JTextField(20);
        contactPhoneLabel = new JLabel("Phone:");
        newPhoneButton = new JButton("Update Phone");
        contactAddrLabel = new JLabel("Address:");
        newAddrButton = new JButton("Update Address");
        submitButton = new JButton("Submit");
        listButton = new JButton("List");

        setLayout(new GridLayout(6, 2));
        add(contactIdLabel);
        add(contactIdText);
        add(contactgrpIdLabel);
        add(groupsButton);
        add(contactNameLabel);
        add(contactNameText);
        add(contactPhoneLabel);
        add(newPhoneButton);
        add(contactAddrLabel);
        add(newAddrButton);
        add(submitButton);
        add(listButton);


        groupsButton.addActionListener(this);
        newPhoneButton.addActionListener(this);
        newAddrButton.addActionListener(this);
        submitButton.addActionListener(this);
        listButton.addActionListener(this);

        setTitle("Contact Edit Form");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static ContactDTO getEntityFromUI(ContactEdit cntfm) throws ParseException {
        ContactDTO cdto = new ContactDTO();
        cntfm.contactIdText.commitEdit(); 
        if (cntfm.contactIdText.getText().isEmpty()) {
            GUIHelper.MessageBox("Contact ID cannot be empty!");
            return null; 
        }
        String name = cntfm.contactNameText.getText();
        if (!name.isEmpty()) {
            cdto.setcName(name);
       }

       if (name.equals(" ")) {
           GUIHelper.MessageBox("Contact name cannot be empty!");
       }
        return cdto;
    }

    public static void viewHandler(ContactEdit cntfm) throws Exception {
        if (cntfm.contactIdText.getText().isEmpty()) {
            GUIHelper.MessageBox("Contact ID cannot be empty!");
        }
        String name = cntfm.contactNameText.getText();

       if (name.equals(" ")) {
           GUIHelper.MessageBox("Contact name cannot be empty!");
       }
        ContactDTO cdto = getEntityFromUI(cntfm);
        if (cdto == null) {
            GUIHelper.MessageBox("Invalid retrieval of Entity");
            return;
        }

        boolean result = ContactManager.updateContact(cdto);
        if (result) {
            System.out.println("Contact added successfully!");
            GUIHelper.MessageBox("Contact added successfully!");
            cntfm.clearFields();
        } else {
            System.out.println("Failed to add contact");
            GUIHelper.MessageBox("Failed to add contact");
            cntfm.clearFields();
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        GUIHelper.MessageBox("From Action Performed " + cmd);

        if (cmd.equals("List")) {
            try {
                ContactList s = new ContactList();
                s.setVisible(true);
                return;
            } catch (Exception ex) {
                return;
            }
        }

        if (cmd.equals("Update Address")) { 
            if (!contactIdText.getText().trim().isEmpty()) {
                try {
                    contactIdText.commitEdit();
                    int contactId = Integer.parseInt(contactIdText.getText());
                    if (!ContactManager.DoesExists(contactId)) {
                        GUIHelper.MessageBox("Contact ID does not exist.");
                        clearFields();
                        return;
                    } else {
                        AddressEdit af = new AddressEdit("EDIT", contactId);
                        af.setVisible(true);
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                } 
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID.");
                clearFields();
            }
            return;
        }
        

        if (cmd.equals("Update Phone")) {  
            if (!contactIdText.getText().trim().isEmpty()) {
                try {
                    contactIdText.commitEdit();
                    int contactId = Integer.parseInt(contactIdText.getText());
                    if (!ContactManager.DoesExists(contactId)) {
                        GUIHelper.MessageBox("Contact ID does not exist.");
                        clearFields();
                        return;
                    } else {
                        PhoneEdit pf = new PhoneEdit("EDIT", contactId);
                        pf.setVisible(true);
                    }
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
               
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID.");
                clearFields();
            }
            return;
        }

        if (cmd.equals("Update Groups")) {  
            if (!contactIdText.getText().trim().isEmpty()) {
            try {
                contactIdText.commitEdit();
                int contactId = Integer.parseInt(contactIdText.getText());
                if (!ContactManager.DoesExists(contactId)) {
                    GUIHelper.MessageBox("Contact ID does not exist.");
                    clearFields();
                    return;
                } else {
                    ContactGrpEdit gd = new ContactGrpEdit("EDIT", contactId);
                    gd.setVisible(true);
                }
                } catch (Exception ex) {
                ex.printStackTrace();
                }
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID.");
                clearFields();
            }
            return;
        }

        if (e.getActionCommand().equals("Submit")) {
            if (!contactIdText.getText().trim().isEmpty()) {
            GUIHelper.MessageBox("Updating contact");
            clearFields();
             try {
                 viewHandler(this);
             } catch (Exception ex) {
                 throw new RuntimeException(ex);
             }
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID.");
                clearFields();
            }
         }
    }

    private void clearFields() {
        contactIdText.setText("");
        contactNameText.setText("");
    }
}
