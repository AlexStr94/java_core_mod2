import java.util.Scanner;

import static java.lang.Character.toLowerCase;

public class Calculator {
    private static final String WELCOME_MESSAGE = """
            Вас приветствует программа 'Калькулятор'.
            Калькульятор поддерживает следующие операции: +, -, *, /.
            Сбросить калькулятор можно клавишей 'с', а выйти - клавишей 's'.
            """;
    private static final String INVALID_VALUE_MESSAGE = "Вы ввели неверное значение, попробуйте еще раз:";
    private static final char[] OPERATIONS = {'+', '-', '*', '/'};
    private static final char RESET_CHAR = 'c';
    private static final char EXIT_CHAR = 's';
    private static final Scanner SCANNER = new Scanner(System.in);
    private static boolean continuationOfCalculations = false;
    private static double calculations = 0;

    public static void main(String[] args) {
        start();
    }

    private static void start() {
        System.out.println(WELCOME_MESSAGE);
        getFirstOperand();
    }

    private static void getFirstOperand() {
        double firstOperand;
        if (!continuationOfCalculations) {
            System.out.println("Введите первый операнд и нажмите Enter:");
            firstOperand = getOperand();
        } else {
            firstOperand = calculations;
        }
        getOperation(firstOperand);
    }

    private static void getOperation(double firstOperand) {
        System.out.println("Введите операцию и нажмите Enter:");
        char operation = getOperation();
        getSecondOperand(firstOperand, operation);
    }

    private static void getSecondOperand(double firstOperand, char operation) {
        System.out.println("Введите второй операнд и нажмите Enter:");
        double secondOperand = getOperand();
        calculate(firstOperand, secondOperand, operation);
    }

    private static void calculate(double firstOperand, double secondOperand, char operation) {
        switch (operation) {
            case '+':
                calculations = firstOperand + secondOperand;
                break;
            case '-':
                calculations = firstOperand - secondOperand;
                break;
            case '/':
                calculations = firstOperand / secondOperand;
                break;
            case '*':
                calculations = firstOperand * secondOperand;
        }
        continuationOfCalculations = true;
        System.out.printf("Результат: %.2f\n", calculations);
        getFirstOperand();
    }

    private static void invalidValue() {
        System.out.println(INVALID_VALUE_MESSAGE);
    }

    private static double getOperand() {
        double operand;
        String input = SCANNER.nextLine();
        checkCommands(input);
        try {
            operand = Double.parseDouble(input);
        } catch (NumberFormatException e) {
            invalidValue();
            return getOperand();
        }

        return operand;
    }

    private static char getOperation() {
        String input = SCANNER.nextLine();
        checkCommands(input);
        char operation = input.charAt(0);
        if (input.length() > 1 || new String(OPERATIONS).indexOf(operation) == -1) {
            invalidValue();
            return getOperation();
        }
        return operation;

    }

    private static void checkCommands(String input) {
        if (toLowerCase(input.charAt(0)) == RESET_CHAR) {
            continuationOfCalculations = false;
            getFirstOperand();
        }
        if (toLowerCase(input.charAt(0)) == EXIT_CHAR) {
            System.exit(0);
        }
    }
}