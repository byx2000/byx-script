package byx.script.core.parser.ast.stmt;

import byx.script.core.parser.ast.ASTVisitor;

/**
 * 继续循环
 */
public class Continue implements Statement {
    @Override
    public <R, C> R visit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
