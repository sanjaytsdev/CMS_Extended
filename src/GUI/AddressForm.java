package GUI;

import javax.swing.*;
import Domain.DTO.AddressDTO;
import Presenter.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AddressForm extends JFrame implements ActionListener {

    private JLabel addrLabel;
    private JTextField addressText;
    private JButton submitButton;
    private int contactId;

    public AddressForm(String mode, int contactId) {
        super("Address Form - " + mode);
        this.contactId = contactId;

        addrLabel = new JLabel("Address:");
        addressText = new JTextField(20);
        submitButton = new JButton("Submit");

        setLayout(new FlowLayout());
        add(addrLabel);
        add(addressText);
        add(submitButton);

        submitButton.addActionListener(this);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String address = addressText.getText();
        AddressDTO addressDTO = new AddressDTO();
        addressDTO.setAddr(address);
        addressDTO.setCid(contactId);

        boolean result;
        try {
            result = AddressManager.addAddress(addressDTO);
            if (result) {
                System.out.println("Address add success!");
                GUIHelper.MessageBox("Address added successfully!");
                addressText.setText("");
                dispose();
            } else {
                GUIHelper.MessageBox("Failed to add Address.");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }

}
