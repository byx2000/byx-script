package byx.script.core.interpreter.builtin;

import byx.script.core.interpreter.value.DoubleValue;
import byx.script.core.interpreter.value.ObjectValue;
import byx.script.core.interpreter.value.Value;

/**
 * Native.Math
 */
public class Math extends ObjectValue {
    public static final Math INSTANCE = new Math();

    private Math() {
        setCallableField("sin_d", DoubleValue.class, x -> Value.of(java.lang.Math.sin(x.getValue())));
        setCallableField("cos_d", DoubleValue.class, x -> Value.of(java.lang.Math.cos(x.getValue())));
        setCallableField("tan_d", DoubleValue.class, x -> Value.of(java.lang.Math.tan(x.getValue())));
        setCallableField("pow_d", DoubleValue.class, DoubleValue.class, (x, n) -> Value.of(java.lang.Math.pow(x.getValue(), n.getValue())));
        setCallableField("exp_d", DoubleValue.class, x -> Value.of(java.lang.Math.exp(x.getValue())));
        setCallableField("ln_d", DoubleValue.class, x -> Value.of(java.lang.Math.log(x.getValue())));
        setCallableField("log10_d", DoubleValue.class, x -> Value.of(java.lang.Math.log10(x.getValue())));
        setCallableField("sqrt_d", DoubleValue.class, x -> Value.of(java.lang.Math.sqrt(x.getValue())));
        setCallableField("round_d", DoubleValue.class, x -> Value.of((int) java.lang.Math.round(x.getValue())));
        setCallableField("ceil_d", DoubleValue.class, x -> Value.of((int) java.lang.Math.ceil(x.getValue())));
        setCallableField("floor_d", DoubleValue.class, x -> Value.of((int) java.lang.Math.floor(x.getValue())));
    }
}
