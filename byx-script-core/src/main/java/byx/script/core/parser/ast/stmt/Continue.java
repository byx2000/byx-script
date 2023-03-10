package byx.script.core.parser.ast.stmt;

import byx.script.core.parser.ast.ASTVisitor;

/**
 * 继续循环
 */
public class Continue extends Statement {
    public static final Continue INSTANCE = new Continue();

    private Continue() {}

    @Override
    public <R, C> R doVisit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
