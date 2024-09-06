package GUI;

import javax.swing.*;
import Domain.DTO.ContactDTO;
import Presenter.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.text.ParseException;

import javax.swing.text.NumberFormatter;

public class contactFormAdd extends JFrame implements ActionListener {

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


    public contactFormAdd(String mode) {
        super("Contact Form - " + mode);
    
        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        contactIdLabel = new JLabel("Contact id :");
        contactIdText = new JFormattedTextField(numberFormat);
        contactgrpIdLabel = new JLabel("Contact Group:");
        groupsButton = new JButton("Add Groups");
        contactNameLabel = new JLabel("Contact Name:");
        contactNameText = new JTextField(20);
        contactPhoneLabel = new JLabel("Phone:");
        newPhoneButton = new JButton("Add Phone");
        contactAddrLabel = new JLabel("Address:");
        newAddrButton = new JButton("Add Address");
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

        setTitle("Contact Form");
        setSize(500, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static ContactDTO getEntityFromUI(contactFormAdd cntfm) throws ParseException {
        ContactDTO cdto = new ContactDTO();
        cntfm.contactIdText.commitEdit(); 
        cdto.setcId(GUIHelper.convertToInteger(cntfm.contactIdText));
        cdto.setcName(cntfm.contactNameText.getText());
        return cdto;
    }

    public static void viewHandler(contactFormAdd cntfm) throws Exception {
        String cname = cntfm.contactNameText.getText().trim();
        if (cntfm.contactIdText.getText().isEmpty()) {
            GUIHelper.MessageBox("Please enter a contact ID first.");
        } else if (cname.isEmpty()) {
            GUIHelper.MessageBox("Please enter a contact name.");
            return;
        } else if (cname.equals(" ")) {
            GUIHelper.MessageBox("Please enter a contact name.");
            return;
        } else {
            ContactDTO cdto = getEntityFromUI(cntfm);
            if (cdto == null) {
                GUIHelper.MessageBox("Invalid retrieval of Entity");
                return;
            }
    
            if (ContactManager.DoesExists(cdto.getcId())) {
                GUIHelper.MessageBox("The ID already exists");
                return;
            }
    
            boolean result = ContactManager.addContact(cdto);
            if (result) {
                System.out.println("Contact added successfully!");
                GUIHelper.MessageBox("Contact added successfully!");
                cntfm.clearFields();
            } else {
                System.out.println("Failed to add contact");
                GUIHelper.MessageBox("Failed to add contact");
            }
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

        if (cmd.equals("Add Address")) {  
            if (!contactIdText.getText().isEmpty()) {
                try {
                    contactIdText.commitEdit();
                    int contactId = Integer.parseInt(contactIdText.getText());
                    AddressForm af = new AddressForm("ADD", contactId);
                    af.setVisible(true);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                } 
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID first.");
            }

            return;
        }

        if (cmd.equals("Add Phone")) {  
            if (!contactIdText.getText().isEmpty()) {
                try {
                    contactIdText.commitEdit();
                    int contactId = Integer.parseInt(contactIdText.getText());
                    PhoneForm pf = new PhoneForm("ADD", contactId);
                    pf.setVisible(true);
                } catch (ParseException e1) {
                    e1.printStackTrace();
                }
               
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID first.");
            }
            return;
        }

        if (cmd.equals("Add Groups")) {
            if (!contactIdText.getText().isEmpty()) {
                try {
                    contactIdText.commitEdit();
                    int contactId = Integer.parseInt(contactIdText.getText());
                    GroupAdd gd = new GroupAdd("ADD", contactId);
                    gd.setVisible(true);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } else {
                GUIHelper.MessageBox("Please enter a Contact ID first.");
            }
            return;
        }

        if (e.getActionCommand().equals("Submit")) {
            GUIHelper.MessageBox("Creating a contact");
             try {
                 viewHandler(this);
             } catch (Exception ex) {
                 throw new RuntimeException(ex);
             }
         }
    }

    private void clearFields() {
        contactIdText.setText("");
        contactNameText.setText("");
    }
}
