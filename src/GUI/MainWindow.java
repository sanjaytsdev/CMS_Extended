package GUI;

import javax.swing.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainWindow extends JFrame{
    static JMenuBar mb;
    static JMenu m1;
    static JMenu m2;
    static JMenuItem it1, it2, it3, it4, it5, it6, it7, it8;
    static JFrame f;

    public static void main(String [] args) {
        f = new JFrame("Contact Management");
        mb = new JMenuBar();

        m1 = new JMenu("Contact Group");

        it1 = new JMenuItem("Group Add");
        it2 = new JMenuItem("Group Edit");
        it3 = new JMenuItem("Group Delete");
        it4 = new JMenuItem("Exit");

        m2 = new JMenu("Contact");

        it5 = new JMenuItem("Contact Add");
        it6 = new JMenuItem("Contact Edit");
        it7 = new JMenuItem("Contact Delete");
        it8 = new JMenuItem("Exit");

        m1.add(it1);
        m1.add(it2);
        m1.add(it3);
        m1.add(it4);
        m2.add(it5);
        m2.add(it6);
        m2.add(it7);
        m2.add(it8);
       

        mb.add(m1);
        mb.add(m2);

        it1.addActionListener(new MenuActionListener());
        it2.addActionListener(new MenuActionListener());
        it3.addActionListener(new MenuActionListener());
        it4.addActionListener(new MenuActionListener());
        it5.addActionListener(new MenuActionListener());
        it6.addActionListener(new MenuActionListener());
        it7.addActionListener(new MenuActionListener());
        it8.addActionListener(new MenuActionListener());

        f.setJMenuBar(mb);

        f.setSize(500, 500);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }
}

class MenuActionListener implements ActionListener {
    @Override
    public void actionPerformed(ActionEvent e) {
        String f = e.getActionCommand();
        if (f.equals("Group Add")) {
            groupFormAdd form = new groupFormAdd("ADD");
            return ;
        } else if (f.equals("Group Edit")) {
            GroupEdit form = new GroupEdit("EDIT");
            return;
        } else if (f.equals("Group Delete")) {
            try {
                GroupDelete form = new GroupDelete("DELETE");
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (f.equals("Contact Add")) {
            try {
                contactFormAdd form = new contactFormAdd("ADD");
                return;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (f.equals("Contact Delete")) {
            try {
                ContactDelete form = new ContactDelete("DELETE");
                return;
            } catch (Exception ex) {
                throw new RuntimeException(ex);
            }
        } else if (f.equals("Contact Edit")) {
                ContactEdit form = new ContactEdit("EDIT");
            return;
        } else if (f.equals("Exit")) {
            System.exit(0);
        }
    }
}

