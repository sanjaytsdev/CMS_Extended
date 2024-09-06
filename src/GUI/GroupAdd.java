package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Domain.DTO.ContactGrpDTO;
import Presenter.ContactGrpManager;

public class GroupAdd extends JFrame implements ActionListener {

    private JLabel grpIdLabel;
    private JTextField grpText;
    private JButton submitButton;
    private JLabel outputLabel;
    private int contactId;

    public GroupAdd(String mode, int contactId) {
        super("Group Form - " + mode);
        this.contactId = contactId;

        grpIdLabel = new JLabel("Group ID:");
        grpText = new JTextField(20);
        submitButton = new JButton("Submit");
        outputLabel = new JLabel("");

        setLayout(new GridLayout(2, 2));
        add(grpIdLabel);
        add(grpText);
        add(submitButton);
        add(outputLabel);

        submitButton.addActionListener(this);

        setTitle("Group Add Form");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            String groupIdText = grpText.getText();
            if (groupIdText.isEmpty()) {
                GUIHelper.MessageBox("Please enter a valid group ID.");
                return;
            }

            int grpid = Integer.parseInt(groupIdText);
            ContactGrpDTO groupDTO = new ContactGrpDTO();
            groupDTO.setGrpId(grpid);

            if (!ContactGrpManager.doesExists(grpid)) {
                GUIHelper.MessageBox("The group ID does not exist.");
                return;
            }

            boolean result = ContactGrpManager.Add(groupDTO, contactId);

            if (result) {
                System.out.println("Group add success");
                GUIHelper.MessageBox("Group added and mapped to contact successfully!");
                dispose();
            } else {
                GUIHelper.MessageBox("Failed to add and map the group or mapping exist.");
            }

        } catch (NumberFormatException ex) {
            GUIHelper.MessageBox("Please enter a valid number for the group ID.");
        } catch (Exception ex) {
            ex.printStackTrace();
            GUIHelper.MessageBox("An error occurred while adding the group.");
        }
    }
}
