package schoolmanagementsystem;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

    public class AddStudent extends JFrame {

        JTextField nameField, rollField, gradeField;

        public AddStudent() {
            setTitle("Add New Student");
            setSize(300, 250);
            setLayout(new GridLayout(5, 2, 10, 10));
            setLocationRelativeTo(null);

            // Labels and Fields
            add(new JLabel("Student Name:"));
            nameField = new JTextField();
            add(nameField);

            add(new JLabel("Roll Number:"));
            rollField = new JTextField();
            add(rollField);

            add(new JLabel("Grade:"));
            gradeField = new JTextField();
            add(gradeField);

            JButton addBtn = new JButton("Add Student");
            add(addBtn);

            JButton cancelBtn = new JButton("Cancel");
            add(cancelBtn);

            // Action listener for Add button
            addBtn.addActionListener(e -> {
                String name = nameField.getText();
                String rollNoStr = rollField.getText();
                String grade = gradeField.getText();

                if (name.isEmpty() || rollNoStr.isEmpty() || grade.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Please fill all fields!");
                    return;
                }

                try {
                    int rollNo = Integer.parseInt(rollNoStr);

                    Connection con = DBConnection.getConnection();
                    String query = "INSERT INTO students(name, roll_no, grade) VALUES (?, ?, ?)";
                    PreparedStatement pst = con.prepareStatement(query);
                    pst.setString(1, name);
                    pst.setInt(2, rollNo);
                    pst.setString(3, grade);

                    int rows = pst.executeUpdate();
                    if (rows > 0) {
                        JOptionPane.showMessageDialog(this, "Student added successfully ✅");
                        dispose(); // close this window
                    } else {
                        JOptionPane.showMessageDialog(this, "Failed to add student ❌");
                    }

                    con.close();

                } catch (NumberFormatException nfe) {
                    JOptionPane.showMessageDialog(this, "Roll number must be numeric!");
                } catch (SQLException sqle) {
                    JOptionPane.showMessageDialog(this, "Error: " + sqle.getMessage());
                }
            });

            // Cancel button
            cancelBtn.addActionListener(e -> dispose());

            setVisible(true);
        }
    }


