package GUI;

import java.awt.*;
import javax.swing.*;

import Presenter.*;

public class ContactGroupList extends JFrame {

    public ContactGroupList() throws Exception {
        String[] columnNames = {"ID", "Group Name", "Group Description"};
        Object[][] data = ContactGrpManager.getGrpAsArray();

        JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane);

        this.setSize(600, 700);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
