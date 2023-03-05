package byx.script.core.parser.ast.stmt;

import byx.script.core.parser.ast.ASTVisitor;

/**
 * 跳出循环
 */
public class Break extends Statement {
    @Override
    public <R, C> R doVisit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
