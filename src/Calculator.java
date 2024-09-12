import java.util.Scanner;

import static java.lang.Character.toLowerCase;

public class Calculator {
    enum Stages {
        GET_FIRST_OPERAND,
        GET_OPERATION,
        GET_SECOND_OPERAND,
        CALCULATE,
    }

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
    private static double firstOperand, secondOperand, calculations;
    private static char operation;
    private static Stages currentStage = Stages.GET_FIRST_OPERAND;

    public static void main(String[] args) {
        System.out.println(WELCOME_MESSAGE);
        while (true) {
            updateStage();
        }
    }

    public static void updateStage() {
        switch (currentStage) {
            case GET_FIRST_OPERAND:
                currentStage = getFirstOperand();
                break;
            case GET_OPERATION:
                currentStage = getOperation();
                break;
            case GET_SECOND_OPERAND:
                currentStage = getSecondOperand();
                break;
            case CALCULATE:
                currentStage = calculate();
                break;
        }
    }

    private static Stages getFirstOperand() {
        System.out.println("Введите первый операнд и нажмите Enter:");
        firstOperand = getOperand();

        return Stages.GET_OPERATION;
    }

    private static Stages getOperation() {
        System.out.println("Введите операцию и нажмите Enter:");
        operation = getOperationChar();
        return Stages.GET_SECOND_OPERAND;
    }

    private static Stages getSecondOperand() {
        System.out.println("Введите второй операнд и нажмите Enter:");
        secondOperand = getOperand();
        if (operation == '/' && secondOperand == 0) {
            System.out.println("Делить на ноль нельзя");
            return getSecondOperand();
        }
        return Stages.CALCULATE;
    }

    private static Stages calculate() {
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
        firstOperand = calculations;
        System.out.printf("Результат: %.2f\n", calculations);
        return Stages.GET_OPERATION;
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

    private static char getOperationChar() {
        String input = SCANNER.nextLine();
        checkCommands(input);
        char operation = input.charAt(0);
        if (input.length() > 1 || new String(OPERATIONS).indexOf(operation) == -1) {
            invalidValue();
            return getOperationChar();
        }
        return operation;

    }

    private static void checkCommands(String input) {
        if (toLowerCase(input.charAt(0)) == RESET_CHAR) {
            getFirstOperand();
        }
        if (toLowerCase(input.charAt(0)) == EXIT_CHAR) {
            System.exit(0);
        }
    }
}