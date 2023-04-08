package byx.script.core.interpreter.exception;

import byx.script.core.parser.exception.FastException;

public class BreakException extends FastException {
    public static final BreakException INSTANCE = new BreakException();
}
