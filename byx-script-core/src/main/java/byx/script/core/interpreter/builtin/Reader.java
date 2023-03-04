package byx.script.core.interpreter.builtin;

import byx.script.core.interpreter.value.ObjectValue;
import byx.script.core.interpreter.value.Value;

import java.util.Scanner;

/**
 * 内建对象，读取输入
 */
public class Reader extends ObjectValue {
    public Reader(Scanner scanner) {
        setCallableField("nextLine", () -> Value.of(scanner.nextLine()));
        setCallableField("nextInt", () -> Value.of(scanner.nextInt()));
        setCallableField("nextDouble", () -> Value.of(scanner.nextDouble()));
        setCallableField("nextBool", () -> Value.of(scanner.nextBoolean()));
        setCallableField("hasNext", () -> Value.of(scanner.hasNext()));
    }
}
