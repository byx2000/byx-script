package byx.script.core.parser.ast.expr;

import byx.script.core.parser.ast.ASTVisitor;

/**
 * 变量引用
 */
public class Var extends Expr {
    private final String varName;

    public Var(String varName) {
        this.varName = varName;
    }

    public String getVarName() {
        return varName;
    }

    @Override
    public <R, C> R doVisit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
