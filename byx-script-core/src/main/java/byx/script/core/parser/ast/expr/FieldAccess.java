package byx.script.core.parser.ast.expr;

import byx.script.core.parser.ast.ASTVisitor;

/**
 * 字段访问
 */
public class FieldAccess extends Expr {
    private final Expr expr;
    private final String field;

    public FieldAccess(Expr expr, String field) {
        this.expr = expr;
        this.field = field;
    }

    public Expr getExpr() {
        return expr;
    }

    public String getField() {
        return field;
    }

    @Override
    public <R, C> R doVisit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
