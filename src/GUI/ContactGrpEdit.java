package GUI;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Domain.DTO.ContactGrpDTO;
import Presenter.ContactGrpManager;

public class ContactGrpEdit extends JFrame implements ActionListener {

    private JPanel groupPanel;
    private HashMap<JCheckBox, ContactGrpDTO> groupCheckBoxMap;
    private JButton submitButton;
    private JLabel outputLabel = new JLabel("");
    private int cid;

    public ContactGrpEdit(String mode, int cid) {
        super("Group Update Form - " + mode);
        this.cid = cid;

        setLayout(new BorderLayout());

        groupPanel = new JPanel();
        groupPanel.setLayout(new GridLayout(0, 1)); 
        groupCheckBoxMap = new HashMap<>();
        loadGroups(cid);

        submitButton = new JButton("Submit Changes");
        add(new JScrollPane(groupPanel), BorderLayout.CENTER);
        add(submitButton, BorderLayout.SOUTH);
        add(outputLabel, BorderLayout.NORTH);

        submitButton.addActionListener(this);

        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void loadGroups(int cid) {
        try {
          
            List<ContactGrpDTO> contactGroups = ContactGrpManager.getGroupsByContactId(cid);
           
            List<ContactGrpDTO> allGroups = ContactGrpManager.getAllGrps();

            groupPanel.removeAll(); 
            groupCheckBoxMap.clear();

            for (ContactGrpDTO group : allGroups) {
                JCheckBox groupCheckBox = new JCheckBox(group.getGrpId() + " - " + group.getGrpName());
                boolean isMapped = contactGroups.stream().anyMatch(cg -> cg.getGrpId() == group.getGrpId());
                groupCheckBox.setSelected(isMapped);
                groupCheckBoxMap.put(groupCheckBox, group);
                groupPanel.add(groupCheckBox);
            }

            groupPanel.revalidate();
            groupPanel.repaint();
        } catch (Exception e) {
            GUIHelper.MessageBox("Error loading groups: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            try {
                for (JCheckBox groupCheckBox : groupCheckBoxMap.keySet()) {
                    ContactGrpDTO group = groupCheckBoxMap.get(groupCheckBox);
                    int grpid = group.getGrpId();
                    boolean isSelected = groupCheckBox.isSelected();

                    if (isSelected) {
                        boolean mappingExists = ContactGrpManager.doesMappingExists(cid, grpid);
                        if (!mappingExists) {
                            ContactGrpManager.addGroupMapping(cid, grpid);
                        }
                    } else {
                        ContactGrpManager.deleteGroupMapping(cid, grpid);
                    }
                }
                GUIHelper.MessageBox("Changes submitted successfully.");
                dispose(); 
            } catch (Exception ex) {
                ex.printStackTrace();
                GUIHelper.MessageBox("An error occurred: " + ex.getMessage());
            }
        }
    }
}
