package gui;

import evaluator.CalculatorEvaluator;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculatorGUI() {
        setTitle("Basic Calculator");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        inputField = new JTextField(20);
        JButton calculateButton = new JButton("Calculate");
        resultLabel = new JLabel("Result: ");

        calculateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String expression = inputField.getText();
                try {
                    int result = CalculatorEvaluator.evaluateExpression(expression);
                    resultLabel.setText("Result: " + result);
                } catch (Exception ex) {
                    resultLabel.setText("Error: Invalid expression");
                }
            }
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(inputField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        add(calculateButton, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        add(resultLabel, gbc);
    }
}
