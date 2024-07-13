package gui;

import evaluator.CalculatorEvaluator;
import gui.component.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculatorGUI() {
        setTitle("Basic Calculator");
        setSize(500, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout(10, 10));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adding whitespace between frame and window
        outerPanel.setBackground(new Color(230, 230, 250));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout(10, 10));
        innerPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Frame around the calculator components
        innerPanel.setBackground(new Color(255, 255, 255));

        inputField = new JTextField();
        inputField.setFont(new Font("Arial", Font.PLAIN, 24));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        inputField.setBackground(Color.WHITE);
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultLabel = new JLabel("Result: ");
        resultLabel.setFont(new Font("Arial", Font.PLAIN, 18));
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(inputField, BorderLayout.NORTH);
        displayPanel.add(resultLabel, BorderLayout.CENTER);

        add(displayPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        add(buttonPanel, BorderLayout.CENTER);
    }

    private void addKeyBindings(JButton button, String key) {
        InputMap inputMap = button.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = button.getActionMap();

        inputMap.put(KeyStroke.getKeyStroke(key), key);
        actionMap.put(key, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                button.doClick();
            }
        });
    }

    private JPanel createButtonPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 4, 10, 10));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+",
                "(", ")", "C", "CE"
        };

        for (String text : buttons) {
            RoundedButton button = new RoundedButton(text);
            button.setFont(new Font("Arial", Font.PLAIN, 24));
            button.setFocusPainted(false);
            button.setBackground(new Color(173, 216, 230));
            button.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            button.addActionListener(new ButtonClickListener());
            panel.add(button);

            // Add key bindings
            addKeyBindings(button, text);
        }

        // add keybindings expects JButtons so we need to cast component to JButton
        // Custom key bindings for operations
        addKeyBindings((JButton) panel.getComponent(3), "DIVIDE");
        addKeyBindings((JButton) panel.getComponent(7), "MULTIPLY");
        addKeyBindings((JButton) panel.getComponent(11), "SUBTRACT");
        addKeyBindings((JButton) panel.getComponent(15), "ADD");
        addKeyBindings((JButton) panel.getComponent(17), "C");
        addKeyBindings((JButton) panel.getComponent(18), "CE");

        return panel;
    }

    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String command = e.getActionCommand();

            switch (command) {
                case "=":
                    try {
                        int result = CalculatorEvaluator.evaluateExpression(inputField.getText());
                        resultLabel.setText("Result: " + result);
                    } catch (Exception ex) {
                        resultLabel.setText("Error: Invalid expression");
                    }
                    break;
                case "C":
                    inputField.setText("");
                    resultLabel.setText("Result: ");
                    break;
                case "CE":
                    if (!inputField.getText().isEmpty()) {
                        inputField.setText(inputField.getText().substring(0, inputField.getText().length() - 1));
                    }
                    break;
                default:
                    inputField.setText(inputField.getText() + command);
                    break;
            }
        }
    }
}
