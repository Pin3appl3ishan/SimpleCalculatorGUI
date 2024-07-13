package evaluator;

import java.util.Stack;

public class CalculatorEvaluator {
    public static int evaluateExpression(String expression) {
        expression = expression.replaceAll("\\s", "");
        return evaluate(expression);
    }

    private static int evaluate(String s) {
        Stack<Integer> nums = new Stack<>();
        Stack<Character> ops = new Stack<>();
        int num = 0;
        int n = s.length();
        char op = '+';

        for (int i = 0; i < n; i++) {
            char c = s.charAt(i);

            if (Character.isDigit(c)) {
                num = num * 10 + (c - '0');
            }

            if (c == '(') {
                int j = i;
                int braces = 0;
                for (; i < n; i++) {
                    if (s.charAt(i) == '(') braces++;
                    if (s.charAt(i) == ')') braces--;
                    if (braces == 0) break;
                }
                num = evaluate(s.substring(j + 1, i));
            }

            if (!Character.isDigit(c) || i == n - 1) {
                if (op == '+') nums.push(num);
                else if (op == '-') nums.push(-num);
                else if (op == '*') nums.push(nums.pop() * num);
                else if (op == '/') nums.push(nums.pop() / num);
                op = c;
                num = 0;
            }
        }

        int result = 0;
        for (int i : nums) result += i;
        return result;
    }
}
