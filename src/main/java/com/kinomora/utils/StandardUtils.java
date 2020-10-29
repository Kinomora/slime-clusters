package com.kinomora.utils;

public class StandardUtils {

    private static StringBuilder points = new StringBuilder();
    private static StringBuilder output = new StringBuilder();

    public static String fancyTime(double input) {
        int temp = (int) input;
        int eval;
        output.setLength(0);

        if (input / 60 > 1) {//60 seconds or fewer
            if (input / 3600 > 1) {//60 minutes or fewer
                if (input / 86400 > 1) {//24 hours or fewer
                    if (input / 604800 > 1) {//1 week or fewer
                        if (input / 2419200 > 1) {// 4 weeks or more
                            return output.append("A very long time..").toString();
                        }
                        eval = (temp - (temp % 604800)) / 604800;
                        output.append(eval).append(" week");
                        if (eval > 1) {
                            output.append("s");
                        }
                        output.append(", ");
                        temp = temp % 604800;
                    }
                    eval = (temp - (temp % 86400)) / 86400;
                    output.append(eval).append(" day");
                    if (eval > 1 || eval == 0) {
                        output.append("s");
                    }
                    output.append(", ");
                    temp = temp % 86400;
                }
                eval = (temp - (temp % 3600)) / 3600;
                output.append(eval).append(" hour");
                if (eval > 1 || eval == 0) {
                    output.append("s");
                }
                output.append(", ");
                temp = temp % 3600;
            }
            eval = (temp - (temp % 60)) / 60;
            output.append(eval).append(" minute");
            if (eval > 1 || eval == 0) {
                output.append("s");
            }
            output.append(", and ");
            temp = temp % 60;
        }
        output.append(temp).append(" second");
        if (temp > 1 || temp == 0) {
            output.append("s");
        }
        return output.toString();
    }

    public static String clean(double number, int pointsIn) {
        points.setLength(0);
        points.append("%.");
        points.append(pointsIn).append("f");
        return String.format(String.valueOf(points), number);
    }

    public static String dirtyFormat(String input, int size) {
        if (input.length() > size) {
            return "error";
        } else if (input.length() == size) {
            return input;
        } else {
            while (input.length() < size) {
                input = input + " ";
            }
            return input;
        }
    }
}