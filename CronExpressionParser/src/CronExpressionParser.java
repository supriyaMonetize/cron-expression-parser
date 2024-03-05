import java.util.*;

public class CronExpressionParser {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.err.println("Input a valid cron expression");
            System.exit(1);
        }

        String cronExpression = args[0];
        String[] fields = cronExpression.split("\\s+");
        if (fields.length != 6) {
            System.err.println("Invalid cron expression format");
            System.exit(1);
        }

        String[] minutes = expandField(fields[0], 0, 59);
        String[] hours = expandField(fields[1], 0, 23);
        String[] daysOfMonth = expandField(fields[2], 1, 31);
        String[] months = expandField(fields[3], 1, 12);
        String[] daysOfWeek = expandField(fields[4], 0, 6);

        printField("minute:      ", minutes);
        printField("hour:        ", hours);
        printField("day of month:", daysOfMonth);
        printField("month:       ", months);
        printField("day of week: ", daysOfWeek);
        System.out.println("command:    " + fields[5]);
    }

    public static String[] expandField(String field, int min, int max) {
        if (field.equals("*")) {
            return expandRange(min, max);
        }

        Set<Integer> values = new TreeSet<>();
        String[] parts = field.split(",");
        for (String part : parts) {
            if (part.contains("/")) {
                expandStep(values, part, min, max);
            } else if (part.contains("-")) {
                expandRange(values, part, min, max);
            } else {
                int value = Integer.parseInt(part);
                if (isValidValue(value, min, max)) {
                    values.add(value);
                } else {
                    throw new IllegalArgumentException("Invalid cron expression");
                }
            }
        }
        return values.stream().map(Object::toString).toArray(String[]::new);
    }

    private static String[] expandRange(int start, int end) {
        List<String> result = new ArrayList<>();
        for (int i = start; i <= end; i++) {
            result.add(Integer.toString(i));
        }
        return result.toArray(new String[0]);
    }

    private static void expandRange(Set<Integer> values, String range, int min, int max) {
        String[] rangeParts = range.split("-");
        int start = Integer.parseInt(rangeParts[0]);
        int end = Integer.parseInt(rangeParts[1]);
        if (isValidRange(start, end, min, max)) {
            for (int i = start; i <= end; i++) {
                values.add(i);
            }
        } else {
            throw new IllegalArgumentException("Invalid cron expression");
        }
    }

    private static void expandStep(Set<Integer> values, String step, int min, int max) {
        String[] divParts = step.split("/");
        int start = min;
        int end = max;
        if (divParts[0].contains("-")) {
            String[] rangeParts = divParts[0].split("-");
            start = Integer.parseInt(rangeParts[0]);
            end = Integer.parseInt(rangeParts[1]);
        }else if (!divParts[0].equals("*")) {
            start = Integer.parseInt(divParts[0]);
        }
        int interval = Integer.parseInt(divParts[1]);
        if (start >= min && start <= max) {

            for (int i = start; i <= end; i += interval) {
                if (isValidValue(i, min, max)) {
                    values.add(i);
                } else {
                    throw new IllegalArgumentException("Invalid cron expression");
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid cron expression");
        }
    }

    private static boolean isValidValue(int value, int min, int max) {
        return value >= min && value <= max;
    }

    private static boolean isValidRange(int start, int end, int min, int max) {
        return start >= min && start <= max && end >= min && end <= max && start <= end;
    }

    private static void printField(String name, String[] values) {
        System.out.println(name + "\t" + String.join(" ", values));
    }
}
