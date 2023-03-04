package byx.script.core.parser.ast.stmt;

import byx.script.core.parser.ast.ASTVisitor;
import byx.script.core.parser.ast.expr.Expr;

/**
 * 赋值语句
 */
public class Assign implements Statement {
    private final Expr lhs, rhs;

    public Assign(Expr lhs, Expr rhs) {
        this.lhs = lhs;
        this.rhs = rhs;
    }

    public Expr getLhs() {
        return lhs;
    }

    public Expr getRhs() {
        return rhs;
    }

    @Override
    public <R, C> R visit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
