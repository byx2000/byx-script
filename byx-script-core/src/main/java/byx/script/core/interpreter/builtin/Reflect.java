package byx.script.core.interpreter.builtin;

import byx.script.core.interpreter.value.*;

/**
 * Native.Reflect
 */
public class Reflect extends ObjectValue {
    public static final Reflect INSTANCE = new Reflect();

    private Reflect() {
        setCallableField("typeId", Value.class, v -> Value.of(v.typeId()));
        setCallableField("hashCode", Value.class, v -> Value.of(v.hashCode()));
        setCallableField("fields", AbstractValue.class, v -> Value.of(v.getFields().keySet().stream().map(Value::of).toList()));
        setCallableFieldNoReturn("setField", AbstractValue.class, StringValue.class, Value.class, (obj, field, value) -> obj.setField(field.getValue(), value));
        setCallableField("getField", AbstractValue.class, StringValue.class, (obj, field) -> obj.getField(field.getValue()));
        setCallableField("hasField", AbstractValue.class, StringValue.class, (obj, field) -> Value.of(obj.hasField(field.getValue())));
    }
}
