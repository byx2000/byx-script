package byx.script.core;

import byx.script.core.parser.ByxScriptParser;
import byx.script.core.parser.exception.ByxScriptParseException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ByxScriptParserTest {
    private void verify(Executable executable, String errMsg) {
        ByxScriptParseException e = assertThrowsExactly(ByxScriptParseException.class, executable);
        assertTrue(e.getMessage().contains(errMsg),
                String.format("actual msg: %s", e.getMessage()));
    }

    @Test
    public void testVarDeclareException() {
        verify(() -> ByxScriptParser.parse("var "), "expect identifier");
        verify(() -> ByxScriptParser.parse("var = 100"), "expect identifier");
        verify(() -> ByxScriptParser.parse("var a"), "expect '='");
        verify(() -> ByxScriptParser.parse("var a 100"), "expect '='");
        verify(() -> ByxScriptParser.parse("var a = @#@%#$"), "invalid expression");
        verify(() -> ByxScriptParser.parse("var a = +-"), "invalid expression");
        verify(() -> ByxScriptParser.parse("var a = 3+(6-)"), "invalid expression");
        verify(() -> ByxScriptParser.parse("var a = 3+*4"), "invalid expression");
    }

    @Test
    public void testFuncDeclareException() {
        verify(() -> ByxScriptParser.parse("function "), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun"), "expect '('");
        verify(() -> ByxScriptParser.parse("function fun(a, b"), "expect ')'");
        verify(() -> ByxScriptParser.parse("function fun(a, b) return 100"), "expect '{'");
        verify(() -> ByxScriptParser.parse("function fun(a, b) {return 100"), "expect '}'");
        verify(() -> ByxScriptParser.parse("function (a, b) {return 100}"), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun(a, , b)"), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun(, a, b)"), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun(+-)"), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun(a, b, )"), "expect identifier");
        verify(() -> ByxScriptParser.parse("function fun(a, 123, b)"), "expect identifier");
    }
}
