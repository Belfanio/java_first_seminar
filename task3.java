package Lesson1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import jdk.jfr.Recording;
import jdk.jfr.consumer.RecordedEvent;
import jdk.jfr.consumer.RecordingFile;

import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.*;

// Реализовать простой калькулятор

// Введите первое число: 12
// Введите операцию: +
// Введите второе число: 1
// Ответ: 13
public class task03 {

    private static final Logger LOGGER = Logger.getLogger(task03.class.getName());
    public static void main(String[] args)throws Exception {
        // Создаем файловый обработчик
        FileHandler fileHandler = new FileHandler("task03Log.txt");
        // Добавляем обработчик к логгеру
        LOGGER.addHandler(fileHandler);

        // Начинаем запись событий
        Recording recording = new Recording();
        recording.start();
        // --------------- Начало рабочего кода ----------------------
        clearScreen();
        Scanner input = new Scanner(System.in);
        System.out.print("Введите первое число: ");
        double num1 = input.nextDouble();
        LOGGER.info("Первое число: " + num1);
        char operator = '\u0000'; // тип char ссылочный, поэтому присваиваем null pointer ссылку
        String operates = "+-*/";
        while (true) {
            System.out.print("Введите операцию +, -, *, /: ");
            operator = input.next().charAt(0);
            LOGGER.info("операнд: " + operator);
            if (operates.contains(Character.toString(operator))) {
                break;
            } else {
                System.out.printf("Ошибка, введен: %s\n", operator);
                LOGGER.info("Ошибка, введен: " + operator);
            }
        }
        System.out.print("Введите второе число: ");
        double num2 = input.nextDouble();
        LOGGER.info("Второе число: " + num2);
        double result = 0;
        switch (operator) {
            case '+':
                result = num1 + num2;
                break;
            case '-':
                result = num1 - num2;
                break;
            case '*':
                result = num1 * num2;
                break;
            case '/':
                result = num1 / num2;
                break;
            default:
                System.out.println("Операция неверна.");
                LOGGER.info("Операция неверна." + operator);
                return;
        }
        String strResult = result % 1 != 0 ? String.format("%.2f", result) : String.format("%.0f", result);
        System.out.printf("Ответ: %s\n", strResult);
        LOGGER.info("Ответ: \n" + strResult);
        // --------------- Окончание рабочего кода ----------------------
        // Останавливаем запись событий
        recording.stop();

        try {
            // Сохраняем записанные события в файл
            Files.createDirectories(Paths.get("profile"));
            recording.dump(Paths.get("profile", "myrecording.jfr"));
        } catch (IOException e) {
            // Читаем записанные события из файла
            try {
                RecordingFile recordingFile = new RecordingFile(Paths.get("profile", "myrecording.jfr"));
                while (recordingFile.hasMoreEvents()) {
                    RecordedEvent event = recordingFile.readEvent();
                    // Обрабатываем записанные события
                    // ...
                }

            } catch (IOException e1) {
                // Обработка исключения
                e1.printStackTrace();
            }
            // Обработка исключения
            e.printStackTrace();
        }
    }

    /*
     * Метод очистки консоли терминала
     */
    public static void clearScreen() {
        System.out.print("\033[H\033[2J");
        System.out.flush();
    }
}