package GUI;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.text.NumberFormatter;


import Presenter.ContactManager;

public class ContactDelete extends JFrame implements ActionListener{

    private JLabel contactIdLabel;
    private JFormattedTextField contactIdText;
    private JButton deleteButton;
    private JLabel outputLabel = new JLabel("");

    public ContactDelete(String mode) {
        super("Group Form - " + mode);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        contactIdLabel = new JLabel("Contact ID:");
        contactIdText = new JFormattedTextField(numberFormat);
        deleteButton = new JButton("Delete");
        outputLabel = new JLabel("");

        setLayout(new GridLayout(3, 2));
        add(contactIdLabel);
        add(contactIdText);
        add(deleteButton);
        add(outputLabel);

        deleteButton.addActionListener(this);

        setTitle("Group Delete Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {

            String contIdText = contactIdText.getText();
            if (contIdText.isEmpty()) {
                GUIHelper.MessageBox("Please enter a valid contact ID.");
                return;
            }

            int cid = Integer.parseInt(contIdText);
            if (!ContactManager.DoesExists(cid)) {
                GUIHelper.MessageBox("The contact ID does not exist.");
                return;
            }
 
            boolean result = ContactManager.Delete(cid);

            if (result) {
                System.out.println("Contact delete success");
                GUIHelper.MessageBox("Contact deleted successfully!");
                contactIdText.setText("");
                dispose();
            } else {
                GUIHelper.MessageBox("Failed to delete the contact.");
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            GUIHelper.MessageBox("An error occurred while deleting the contact.");
        }
    }

}
