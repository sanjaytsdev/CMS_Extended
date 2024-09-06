package GUI;

import Domain.DAO.ContactDAO;
import javax.swing.*;
import java.awt.*;

public class ContactList extends JFrame {

    public ContactList() throws Exception {
        String[] columnNames = {"ID", "Contact Name", "Address", "Phone", "Groups"};
        Object[][] data = (new ContactDAO()).getContactAsArray();
        final JTable table = new JTable(data, columnNames);
        table.setPreferredScrollableViewportSize(new Dimension(500, 70));
        table.setFillsViewportHeight(true);

        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        this.setTitle("Contact List");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(600, 700);
        this.setLocationRelativeTo(null);  
        this.setVisible(true);
    }
}
