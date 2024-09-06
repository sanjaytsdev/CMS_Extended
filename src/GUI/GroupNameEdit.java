package GUI;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import Domain.DTO.ContactGrpDTO;
import Presenter.ContactGrpManager;

public class GroupNameEdit extends JFrame implements ActionListener{

    private JLabel nameLabel;
    private JTextField nameText;
    private JButton updateButton;
    private int grpid;

    public GroupNameEdit(String mode, int grpid) {
        super("Group name Form - " + mode);
        this.grpid = grpid;

        nameLabel = new JLabel("New Name:");
        nameText = new JTextField(20);
        updateButton = new JButton("update");

        setLayout(new FlowLayout());
        add(nameLabel);
        add(nameText);
        add(updateButton);

        updateButton.addActionListener(this);

        setSize(300, 110);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String name = nameText.getText();
        ContactGrpDTO cgdto = new ContactGrpDTO();
        cgdto.setGrpId(grpid);

        if (!name.isEmpty()) {
             cgdto.setGrpName(name);
        }

        if (name.equals(" ")) {
            GUIHelper.MessageBox("Group name cannot be empty!");
            return;
        }

        boolean result;
         try {
            result = ContactGrpManager.UpdateGrp(cgdto);
            if (result) {
                 System.out.println("Group name updated");
                 GUIHelper.MessageBox("Group name updated successfully!");
                 nameText.setText("");
                 dispose();
            } else {
                 GUIHelper.MessageBox("Failed to update Group name");
            }
         } catch (Exception e1) {
             e1.printStackTrace();
        }

    }
}
