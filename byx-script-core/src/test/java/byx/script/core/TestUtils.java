package byx.script.core;

import java.nio.file.Path;
import java.util.Collections;
import java.util.List;

import static byx.script.core.util.OutputUtils.getOutput;
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
        return getOutput(input, () -> {
            ByxScriptRunner runner = new ByxScriptRunner();
            runner.addImportPaths(importPaths);
            runner.run(script);
        });
    }

    private static String replaceBlank(String s) {
        s = s.replaceAll("\\r\\n", "\n");
        s = s.replaceAll("\\s+\\n", "\n");
        s = s.replaceAll("\\s+$", "");
        return s;
    }
}
