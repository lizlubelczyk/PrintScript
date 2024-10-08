package ast

import lib.Position

class BooleanExpr(val value: Boolean, override val pos: Position) : Expression {
    override fun <R, C> accept(
        visitor: ExpressionVisitor<R, C>,
        context: C,
    ): R {
        return visitor.visit(this, context)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is BooleanExpr) return false

        return true
    }
}
