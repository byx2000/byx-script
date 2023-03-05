package byx.script.core.interpreter.exception;

/**
 * ByxScript执行的线程被中断时抛出此异常
 */
public class InterruptException extends InterpretException {
    public InterruptException() {
        super("thread interrupted");
    }
}
