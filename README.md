# Cron Expression Parser

The Cron Expression Parser is a command-line application written in Java that parses a cron expression and expands each field to show the times at which it will run.

## Project Description

Cron expressions are used to schedule tasks in Unix-like operating systems. They consist of five time fields (minute, hour, day of month, month, and day of week) plus a command. The Cron Expression Parser takes a cron expression as input and expands each field to show the times at which the task specified by the cron expression will run.

## Usage

1. **Compilation:**

    To compile the program, navigate to the directory containing `CronExpressionParser.java` and run the following command:

    ```bash
    javac CronExpressionParser.java
    ```

2. **Execution:**

    After compilation, you can run the program by providing a valid cron expression as a command-line argument. For example:

    ```bash
    java CronExpressionParser "*/15 0 1,15 * 1-5 /usr/bin/find"
    ```

    Replace `"*/15 0 1,15 * 1-5 /usr/bin/find"` with your desired cron expression.

3. **Output:**

    The program will output a table with each field expanded to show the times at which it will run, along with the specified command.

    Example output for the cron expression "*/15 0 1,15 * 1-5 /usr/bin/find":

    ```
    minute:         0 15 30 45
    hour:           0
    day of month:   1 15
    month:          1 2 3 4 5 6 7 8 9 10 11 12
    day of week:    1 2 3 4 5
    command:    /usr/bin/find
    ```

## Cron Expression Format

The cron expression should follow the standard format with five time fields (minute, hour, day of month, month, and day of week), plus a command. Each field should be separated by a space.

For example:

- `*/15 0 1,15 * 1-5 /usr/bin/find`

## Unit Tests

The project includes unit tests for the `CronExpressionParser` class. These tests ensure that the parser functions correctly under various scenarios.

To run the unit tests, you can use a testing framework such as JUnit. The test class is named `CronExpressionParserTest`.

## Notes

- If the provided cron expression is invalid or in an incorrect format, the program will display an error message.
- The program supports wildcard (\*), ranges (-), and steps (/) in each field of the cron expression.

