package byx.script.core.parser.ast.expr;

import byx.script.core.parser.ast.ASTVisitor;

import java.util.Map;

/**
 * 对象字面量
 */
public class ObjectLiteral implements Expr {
    private final Map<String, Expr> fields;

    public ObjectLiteral(Map<String, Expr> fields) {
        this.fields = fields;
    }

    public Map<String, Expr> getFields() {
        return fields;
    }

    @Override
    public <R, C> R visit(ASTVisitor<R, C> visitor, C ctx) {
        return visitor.visit(this, ctx);
    }
}
