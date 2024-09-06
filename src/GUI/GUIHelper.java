package GUI;

import javax.swing.*;

public class GUIHelper {

    public static void MessageBox(String str) {
        JOptionPane.showMessageDialog(null,str);
    }
    
    public static int convertToInteger(JTextField jf) {
        try {
            return Integer.parseInt(jf.getText());
        } catch (NumberFormatException e) { 
            return Integer.MIN_VALUE;
        }
    }
    
    public static double convertToDouble(JTextField jf) {
        try {
            return Double.parseDouble(jf.getText());
        } catch (NumberFormatException e) {
            return Double.NaN;
        }
    }
    
    public static boolean populateComboBox(JComboBox box, String[] arr) {
        box.removeAllItems();
        for (int i = 0; i < arr.length; ++i)
            box.addItem(arr[i]);
        return true;
    }
 }
    
