package GUI;

import javax.swing.*;
import Domain.DTO.PhoneDTO;
import Presenter.PhoneManager;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PhoneForm extends JFrame implements ActionListener {

    private JLabel phoneLabel;
    private JTextField phoneText;
    private JButton submitButton;
    private int contactId;

    public PhoneForm(String mode, int contactId) {
        super("Phone Form - " + mode);
        this.contactId = contactId;

        phoneLabel = new JLabel("Phone number:");
        phoneText = new JTextField(20);
        submitButton = new JButton("Submit");

        setLayout(new FlowLayout());
        add(phoneLabel);
        add(phoneText);
        add(submitButton);

        submitButton.addActionListener(this);

        setSize(300, 150);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String phoneNumber = phoneText.getText();
        PhoneDTO phoneDTO = new PhoneDTO();
        phoneDTO.setPh(phoneNumber);
        phoneDTO.setCid(contactId);

        boolean result;
        try {
            result = PhoneManager.addPhone(phoneDTO);
            if (result) {
                System.out.println("Phone add success");
                GUIHelper.MessageBox("Phone number added successfully!");
                phoneText.setText("");
                dispose();
            } else {
                GUIHelper.MessageBox("Failed to add phone number.");
            }
        } catch (Exception e1) {
            e1.printStackTrace();
        }
    }
}
