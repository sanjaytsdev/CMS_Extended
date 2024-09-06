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

import Presenter.ContactGrpManager;

public class GroupDelete extends JFrame implements ActionListener {
    private JLabel grpIdLabel;
    private JFormattedTextField grpIdText;
    private JButton deleteButton;

    public GroupDelete(String mode) {
        super("Group Form - " + mode);

        NumberFormat numberFormat = NumberFormat.getInstance();
        numberFormat.setGroupingUsed(false);
        NumberFormatter numberFormatter = new NumberFormatter(numberFormat);
        numberFormatter.setAllowsInvalid(false);
        numberFormatter.setCommitsOnValidEdit(true);

        grpIdLabel = new JLabel("Group ID:");
        grpIdText = new JFormattedTextField(numberFormat);
        deleteButton = new JButton("Delete");

        setLayout(new GridLayout(4, 2));
        add(grpIdLabel);
        add(grpIdText);
        add(deleteButton);

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

            String groupIdText = grpIdText.getText();
            if (groupIdText.isEmpty()) {
                GUIHelper.MessageBox("Please enter a group ID first.");
            } else {
                int grpid = Integer.parseInt(groupIdText);
                if (!ContactGrpManager.doesExists(grpid)) {
                    GUIHelper.MessageBox("The group ID does not exist.");
                    return;
                }
                boolean result = ContactGrpManager.Delete(grpid);
                if (result) {
                    System.out.println(result);
                    System.out.println("Group delete success");
                    GUIHelper.MessageBox("Group deleted successfully!");
                    grpIdText.setText("");
                    dispose();
                } else {
                    GUIHelper.MessageBox("Failed to delete the group.");
                }

            }
            
        } catch (Exception ex) {
            ex.printStackTrace();
           GUIHelper.MessageBox("An error occurred while deleting the group.");
        }
    }
}
