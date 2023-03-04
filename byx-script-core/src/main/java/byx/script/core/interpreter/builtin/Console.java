package byx.script.core.interpreter.builtin;

import byx.script.core.interpreter.value.*;

import java.io.PrintStream;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 内建对象，输出
 */
public class Console extends ObjectValue {
    public Console(PrintStream printStream) {
        setCallableFieldNoReturn("println", args -> printStream.println(args.stream().map(v -> valueToString(v, true)).collect(Collectors.joining(" "))));
        setCallableFieldNoReturn("print", args -> printStream.print(args.stream().map(v -> valueToString(v, true)).collect(Collectors.joining(" "))));
    }

    // 将Value转换成可打印的字符串
    private static String valueToString(Value value, boolean deep) {
        if (value instanceof IntegerValue) {
            return String.valueOf(((IntegerValue) value).getValue());
        } else if (value instanceof DoubleValue) {
            return String.valueOf(((DoubleValue) value).getValue());
        } else if (value instanceof BoolValue) {
            return String.valueOf(((BoolValue) value).getValue());
        } else if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        } else if (value instanceof ListValue) {
            if (!deep) {
                return "[...]";
            }
            List<Value> values = ((ListValue) value).getElems();
            return "[" + values.stream().map(v -> valueToString(v, false)).collect(Collectors.joining(", ")) + "]";
        } else if (value instanceof CallableValue) {
            return "f(...)";
        } else if (value instanceof ObjectValue) {
            if (!deep) {
                return "{...}";
            }
            Map<String, Value> fields = ((ObjectValue) value).getFields();
            return "{" + fields.entrySet().stream()
                    .map(e -> e.getKey() + ": " + valueToString(e.getValue(), false))
                    .collect(Collectors.joining(", ")) + "}";
        } else {
            return value.toString();
        }
    }
}
