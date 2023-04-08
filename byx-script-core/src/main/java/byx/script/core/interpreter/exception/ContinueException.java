package byx.script.core.interpreter.exception;

import byx.script.core.parser.exception.FastException;

public class ContinueException extends FastException {
    public static final ContinueException INSTANCE = new ContinueException();
}
