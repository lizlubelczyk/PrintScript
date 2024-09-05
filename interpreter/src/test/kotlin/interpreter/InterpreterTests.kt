package interpreter

import ast.CallPrintExpr
import ast.DeclareExpr
import ast.Expression
import ast.IdentifierExpr
import ast.NumberExpr
import ast.StringExpr
import ast.TypeExpr
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import token.Position

class InterpreterTests {
    @Test
    fun `test interpret with single expression`() {
        val printCollector = PrintCollector()
        val scope = Scope()
        val interpreter = Interpreter()
        val program =
            listOf<Expression>(
                DeclareExpr(TypeExpr("x", "number", Position(0, 0)), NumberExpr(10, Position(0, 0)), Position(0, 0)),
            ).iterator()

        interpreter.interpret(program, scope, printCollector)

        val result = scope.getVariable("x")?.value

        assertEquals(10, result)
    }

    @Test
    fun `test interpret with string expression`() {
        val printCollector = PrintCollector()
        val scope = Scope()
        val interpreter = Interpreter()

        val program =
            listOf<Expression>(
                DeclareExpr(TypeExpr("message", "string", Position(0, 0)), StringExpr("Hello, world!", Position(0, 0)), Position(0, 0)),
                CallPrintExpr(IdentifierExpr("message", Position(0, 0)), Position(0, 0)),
            ).iterator()

        interpreter.interpret(program, scope, printCollector)

        val result = scope.getVariable("message")?.value

        assertEquals("Hello, world!", result)
    }

    @Test
    fun `test interpreter print`() {
        val printCollector = PrintCollector()
        val scope = Scope()
        val interpreter = Interpreter()

        val program =
            listOf<Expression>(
                DeclareExpr(TypeExpr("message", "string", Position(0, 0)), StringExpr("Hello, world!", Position(0, 0)), Position(0, 0)),
                CallPrintExpr(IdentifierExpr("message", Position(0, 0)), Position(0, 0)),
            ).iterator()

        interpreter.interpret(program, scope, printCollector)

        val result = printCollector.getMessages()

        assertEquals(listOf("Hello, world!"), result)
    }
}
