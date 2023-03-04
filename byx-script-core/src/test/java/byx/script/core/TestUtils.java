package byx.script.core;

import org.junit.jupiter.api.function.Executable;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 测试工具类
 */
public class TestUtils {
    public static void verify(String script, String expectedOutput) {
        verify(Collections.emptyList(), script, "", expectedOutput);
    }

    public static void verify(List<Path> importPaths, String script, String expectedOutput) {
        verify(importPaths, script, "", expectedOutput);
    }

    public static void verify(List<Path> importPaths, String script, String input, String expectedOutput) {
        String output = getScriptOutput(importPaths, script, input);
        assertEquals(replaceBlank(expectedOutput), replaceBlank(output), "实际输出与期望输出不符");
    }

    public static void verifyException(Class<? extends Exception> type, List<Path> importPaths, String script) {
        assertThrows(type, () -> {
            ByxScriptRunner runner = new ByxScriptRunner();
            runner.addImportPaths(importPaths);
            runner.run(script);
        });
    }

    private static String getScriptOutput(List<Path> importPaths, String script, String input) {
        try (
                ByteArrayInputStream is = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8));
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                Scanner scanner = new Scanner(is);
                PrintStream printStream = new PrintStream(os)
        ) {
            ByxScriptRunner runner = new ByxScriptRunner(scanner, printStream);
            runner.addImportPaths(importPaths);
            runner.run(script);
            return os.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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
        } catch (Throwable t) {
            throw new RuntimeException(t);
        }
    }

    public static String getOutput(Executable executable) {
        return getOutput("", executable);
    }

    private static String replaceBlank(String s) {
        s = s.replaceAll("\\r\\n", "\n");
        s = s.replaceAll("\\s+\\n", "\n");
        s = s.replaceAll("\\s+$", "");
        return s;
    }
}
