package GUI;

import javax.swing.*;
import javax.swing.text.NumberFormatter;

import Domain.DTO.ContactGrpDTO;
import Presenter.ContactGrpManager;

import java.awt.*;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.awt.event.ActionEvent;

public class GroupEdit extends JFrame implements ActionListener{
    private JLabel groupIdLabel;
    private JLabel groupNameLabel;
    private JLabel groupDescriptionLabel;
    private JTextField groupIdText;
    private JButton updateNameButton;
    private JButton updateDescButton;
    private JButton updateButton;

    public GroupEdit(String mode) {
        super("Edit Group - " + mode);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        groupIdLabel = new JLabel("Group ID:");
        groupIdText = new JFormattedTextField(numberFormat);
        groupNameLabel = new JLabel("Update Group Name:");
        updateNameButton = new JButton("Update Name");
        groupDescriptionLabel = new JLabel("Update Group Description:");
        updateDescButton = new JButton("Update Desc");
        updateButton = new JButton("Update");

        setLayout(new GridLayout(4, 2));
        add(groupIdLabel);
        add(groupIdText);
        add(groupNameLabel);
        add(updateNameButton);
        add(groupDescriptionLabel);
        add(updateDescButton);
        add(updateButton);

        updateNameButton.addActionListener(this);
        updateDescButton.addActionListener(this);
        updateButton.addActionListener(this);

        setTitle("Group Edit Form");
        setSize(400, 250);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void viewHandler(GroupEdit editForm) throws Exception {
        if (!editForm.groupIdText.getText().isEmpty()) {
            GUIHelper.MessageBox("Please enter a group ID first.");
        } else {
            ContactGrpDTO cgdto = getEntityFromUI(editForm);
            if (cgdto == null) {
                GUIHelper.MessageBox("Error in input.");
                return;
            }

            boolean result = ContactGrpManager.UpdateGrp(cgdto);

            if (result) {
                GUIHelper.MessageBox("Group updated successfully!");
                editForm.groupIdText.setText("");
            } else {
                GUIHelper.MessageBox("Failed to update group!");
            }
        }
    }

    public static ContactGrpDTO getEntityFromUI(GroupEdit editForm) {
        ContactGrpDTO cgdto = new ContactGrpDTO();
        cgdto.setGrpId(GUIHelper.convertToInteger(editForm.groupIdText));
        return cgdto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();
        GUIHelper.MessageBox("From Action Performed " + cmd);

        if (cmd.equals("Update Name")) {
            if (!groupIdText.getText().isEmpty()) {
                try {
                    int grpId = Integer.parseInt(groupIdText.getText());
                    if (!ContactGrpManager.doesExists(grpId)) {
                        GUIHelper.MessageBox("Group ID does not exist.");
                        return;
                    } else {
                        GroupNameEdit s = new GroupNameEdit(cmd, grpId);
                        s.setVisible(true);
                        return;
                    }
                } catch (Exception ex) {
                    return;
                }
            } else {
                GUIHelper.MessageBox("Please enter a group ID first.");
            }
        }
    
        if (cmd.equals("Update Desc")) {
            if (!groupIdText.getText().isEmpty()) {
                try {
                    int grpId = Integer.parseInt(groupIdText.getText());
                    if (!ContactGrpManager.doesExists(grpId)) {
                        GUIHelper.MessageBox("Group ID does not exist.");
                        return;
                    } else {
                        GroupDescEdit s = new GroupDescEdit(cmd, grpId);
                        s.setVisible(true);
                        return;
                    }
                } catch (Exception ex) {
                    return;
                }
            } 
            else {
                GUIHelper.MessageBox("Please enter a group ID first.");
            }
        } 
        
        if (e.getActionCommand().equals("Submit")) {
            GUIHelper.MessageBox("Updating group");
             try {
                 viewHandler(this);
             } catch (Exception ex) {
                 throw new RuntimeException(ex);
             }
         }

    }

}
