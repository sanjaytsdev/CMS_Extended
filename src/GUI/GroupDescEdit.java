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

public class GroupDescEdit extends JFrame implements ActionListener{

    private JLabel descLabel;
    private JTextField descText;
    private JButton updateButton;
    private int grpid;

    public GroupDescEdit(String mode, int grpid) {
        super("Group Description Form - " + mode);
        this.grpid = grpid;

        descLabel = new JLabel("New Description:");
        descText = new JTextField(20);
        updateButton = new JButton("update");

        setLayout(new FlowLayout());
        add(descLabel);
        add(descText);
        add(updateButton);

        updateButton.addActionListener(this);

        setSize(300, 130);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

     @Override
    public void actionPerformed(ActionEvent e) {
        String desc = descText.getText();
        ContactGrpDTO cgdto = new ContactGrpDTO();
        cgdto.setGrpId(grpid);

        if (!desc.isEmpty()) {
             cgdto.setGrpDesc(desc);;
        }

        boolean result;
         try {
            result = ContactGrpManager.UpdateGrp(cgdto);
            if (result) {
                 System.out.println("Group description updated");
                 GUIHelper.MessageBox("Group description updated successfully!");
                 descText.setText("");
                 dispose();
            } else {
                 GUIHelper.MessageBox("Failed to update group description");
            }
         } catch (Exception e1) {
             e1.printStackTrace();
        }

    }
}
 