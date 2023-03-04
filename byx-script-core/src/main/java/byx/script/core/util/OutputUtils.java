package byx.script.core.util;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class OutputUtils {
    public static String getOutput(String input, Executable executable) {
        try (
                ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream os = new ByteArrayOutputStream()
        ) {
            InputStream in = System.in;
            PrintStream out = System.out;
            System.setIn(is);
            System.setOut(new PrintStream(os));
            executable.execute();
            System.setOut(out);
            System.setIn(in);
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getOutput(Executable executable) {
        return getOutput("", executable);
    }
}
