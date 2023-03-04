package byx.script.core.parser.ast.stmt;

import byx.script.core.parser.ast.ASTVisitor;
import byx.script.core.parser.ast.expr.Expr;

/**
 * 变量声明
 * var varName = expr
 */
public class VarDeclare implements Statement {
    private final String varName;
    private final Expr value;

    public VarDeclare(String varName, Expr value) {
        this.varName = varName;
        this.value = value;
    }

    public String getVarName() {
        return varName;
    }

    public Expr getValue() {
        return value;
    }

    @Override
    public <R, C> R visit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
