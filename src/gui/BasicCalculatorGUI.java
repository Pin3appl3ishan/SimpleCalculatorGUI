package gui;

import evaluator.CalculatorEvaluator;
import gui.component.RoundedButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.Objects;

public class BasicCalculatorGUI extends JFrame {
    private JTextField inputField;
    private JLabel resultLabel;

    public BasicCalculatorGUI() {
        setTitle("Calculator");
        setSize(400, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));
        setResizable(false);

        URL iconUrl = getClass().getResource("/assets/delete.png");
        ImageIcon icon = new ImageIcon(iconUrl);
        setIconImage(icon.getImage());

        JPanel outerPanel = new JPanel();
        outerPanel.setLayout(new BorderLayout(10, 10));
        outerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Adding whitespace between frame and window
        outerPanel.setBackground(new Color(30, 30, 30));

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BorderLayout(10, 10));
        innerPanel.setBackground(new Color(50, 50, 50));

        inputField = new JTextField(12);
        inputField.setFont(new Font("Arial", Font.PLAIN, 18));
        inputField.setHorizontalAlignment(JTextField.RIGHT);
        inputField.setEditable(false);
        inputField.setBackground(new Color(30, 30, 30));
        inputField.setForeground(new Color(191, 191, 185));
        inputField.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        resultLabel = new JLabel("0 ");
        resultLabel.setFont(new Font("Arial", Font.BOLD, 32));
        resultLabel.setHorizontalAlignment(SwingConstants.RIGHT);
        resultLabel.setForeground(Color.WHITE);
        resultLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());
        displayPanel.add(inputField, BorderLayout.NORTH);
        displayPanel.add(resultLabel, BorderLayout.CENTER);
        displayPanel.setBackground(new Color(30,30, 30));

        innerPanel.add(displayPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        innerPanel.add(buttonPanel, BorderLayout.CENTER);

        outerPanel.add(innerPanel, BorderLayout.CENTER);
        add(outerPanel, BorderLayout.CENTER);
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
        panel.setBackground(new Color(30, 30, 30));

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
            button.setBackground(new Color(40, 40, 40));
            button.setForeground(new Color(255,250,240));
            button.setBorder(BorderFactory.createEmptyBorder());
            button.addActionListener(new ButtonClickListener());
            panel.add(button);

            // Add key bindings
            addKeyBindings(button, text);
        }

        // add keybindings expects JButtons ,so we need to cast component to JButton
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
                        resultLabel.setText("" + result);
                    } catch (Exception ex) {
                        resultLabel.setText("Error: Invalid expression");
                    }
                    break;
                case "C":
                    inputField.setText("");
                    resultLabel.setText("0  ");
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
