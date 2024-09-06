package GUI;

import javax.swing.*;
import Domain.DTO.ContactGrpDTO;
import Presenter.ContactGrpManager;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class groupFormAdd extends JFrame implements ActionListener {

    private JLabel groupNameLabel;
    private JLabel groupDescriptionLabel;
    private JTextField groupNameText;
    private JTextField groupDescriptionText;
    private JButton submitButton;
    private JButton listButton;

    public groupFormAdd(String mode) {
        super("Group Form - " + mode);

        groupNameLabel = new JLabel("Group Name");
        groupNameText = new JTextField(20);
        groupDescriptionLabel = new JLabel("Group Description");
        groupDescriptionText = new JTextField(20);
        submitButton = new JButton("Submit");
        listButton = new JButton("List");

        setLayout(new GridLayout(3,2)); 
        add(groupNameLabel);
        add(groupNameText);
        add(groupDescriptionLabel);
        add(groupDescriptionText);
        add(submitButton);
        add(listButton);

        submitButton.addActionListener(this);
        listButton.addActionListener(this);

        setTitle("Group Form");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void CalibrateUI(String mode) {
        if (mode.equals("DELETE") || mode.equals("EDIT")) {
            this.submitButton.setText(mode);
            groupDescriptionText.setEditable(false);
            groupNameText.setEditable(false);
        } else {
            this.submitButton.setText(mode);
        }
    }

    public static void viewHandler(groupFormAdd gf) throws Exception {

        String gname = gf.groupNameText.getText().trim();

        if (gname.isEmpty()) {
            GUIHelper.MessageBox("Group Name cannot be empty.");
        } else {
            ContactGrpDTO cdto = getEntityFromUI(gf);
            if (cdto == null) {
                System.out.println("Error in input");
                return;
            }
    
            boolean result = ContactGrpManager.AddGrp(cdto);
    
            if (result) {
                System.out.println("success!");
                GUIHelper.MessageBox("Group created successfully!");
                gf.clearFields(); 
            } else {
                System.out.println("failed");
                GUIHelper.MessageBox("Failed to create group!");
            }
        }
    }

    public static ContactGrpDTO getEntityFromUI(groupFormAdd groupForm) {
        ContactGrpDTO cgdto = new ContactGrpDTO();
        cgdto.setGrpName(groupForm.groupNameText.getText());
        cgdto.setGrpDesc(groupForm.groupDescriptionText.getText());
        return cgdto;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("List")) {
            try {
                ContactGroupList lst = new ContactGroupList();
                lst.setVisible(true);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (e.getActionCommand().equals("Submit")) {
           GUIHelper.MessageBox("Creating a group");
            try {
                viewHandler(this);
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        }
    }

    private void clearFields() {
        groupNameText.setText("");
        groupDescriptionText.setText("");
    }
}
