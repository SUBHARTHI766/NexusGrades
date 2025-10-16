import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

class Student {
    String name;
    int rollNumber;
    double[] marks;
    double average;
    String grade;

    public Student(String name, int rollNumber, double[] marks) {
        this.name = name;
        this.rollNumber = rollNumber;
        this.marks = marks;
        calculateAverageAndGrade();
    }

    private void calculateAverageAndGrade() {
        double total = 0;
        for (double mark : marks) {
            total += mark;
        }
        average = total / marks.length;

        if (average >= 90) grade = "A+";
        else if (average >= 80) grade = "A";
        else if (average >= 70) grade = "B+";
        else if (average >= 60) grade = "B";
        else if (average >= 50) grade = "C";
        else grade = "F";
    }

    public Object[] toRow() {
        Object[] row = new Object[5];
        row[0] = rollNumber;
        row[1] = name;
        row[2] = average;
        row[3] = grade;
        row[4] = String.format("%.2f", average);
        return row;
    }
}

public class StudentGradeManagement extends JFrame {
    private ArrayList<Student> students = new ArrayList<>();
    private JTextField nameField, rollNoField, marksField;
    private DefaultTableModel tableModel;

    public StudentGradeManagement() {
        setTitle("Student Grade Management");
        setSize(600, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(4, 2, 10, 10));

        panel.add(new JLabel("Name:"));
        nameField = new JTextField();
        panel.add(nameField);

        panel.add(new JLabel("Roll Number:"));
        rollNoField = new JTextField();
        panel.add(rollNoField);

        panel.add(new JLabel("Marks (comma separated):"));
        marksField = new JTextField();
        panel.add(marksField);

        JButton addBtn = new JButton("Add Student");
        panel.add(addBtn);

        add(panel, BorderLayout.NORTH);

        // Table to display student info
        String[] columns = {"Roll Number", "Name", "Average", "Grade"};
        tableModel = new DefaultTableModel(columns, 0);
        JTable table = new JTable(tableModel);
        add(new JScrollPane(table), BorderLayout.CENTER);

        addBtn.addActionListener(e -> addStudent());

    }

    private void addStudent() {
        try {
            String name = nameField.getText().trim();
            int rollNo = Integer.parseInt(rollNoField.getText().trim());
            String[] marksStr = marksField.getText().trim().split(",");
            double[] marks = new double[marksStr.length];
            for (int i = 0; i < marksStr.length; i++) {
                marks[i] = Double.parseDouble(marksStr[i].trim());
            }

            Student student = new Student(name, rollNo, marks);
            students.add(student);
            tableModel.addRow(new Object[]{student.rollNumber, student.name, String.format("%.2f", student.average), student.grade});

            nameField.setText("");
            rollNoField.setText("");
            marksField.setText("");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input! Please check your input.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new StudentGradeManagement().setVisible(true);
        });
    }
}
