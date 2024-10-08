package parser.grammar

import parser.parselets.AssignParser
import parser.parselets.BinaryOperatorParser
import parser.parselets.DeclarationParser
import parser.parselets.FnParser
import parser.parselets.IdentifierParser
import parser.parselets.InfixParser
import parser.parselets.LiteralParser
import parser.parselets.PrefixParser
import token.TokenType

class GrammarV1 : Grammar {
    private val prefixParsers = mutableMapOf<TokenType, PrefixParser>()
    private val infixParsers = mutableMapOf<TokenType, InfixParser>()

    init {
        prefix(TokenType.IDENTIFIER, IdentifierParser())
        prefix(TokenType.NUMBER_LITERAL, LiteralParser())
        prefix(TokenType.STRING_LITERAL, LiteralParser())

        prefix(TokenType.LET_KEYWORD, DeclarationParser(true))
        prefix(TokenType.PRINT, FnParser())

        infix(TokenType.ASSIGNATION, AssignParser(1))
        infix(TokenType.SUM, BinaryOperatorParser(2))
        infix(TokenType.SUB, BinaryOperatorParser(2))
        infix(TokenType.MUL, BinaryOperatorParser(3))
        infix(TokenType.DIV, BinaryOperatorParser(4))
    }

    override fun getPrefixParser(type: TokenType): PrefixParser? {
        return prefixParsers[type]
    }

    override fun getInfixParser(type: TokenType): InfixParser? {
        return infixParsers[type]
    }

    private fun prefix(
        type: TokenType,
        parser: PrefixParser,
    ) {
        prefixParsers[type] = parser
    }

    private fun infix(
        type: TokenType,
        parser: InfixParser,
    ) {
        infixParsers[type] = parser
    }

    override fun getPrecedence(type: TokenType): Int {
        return infixParsers[type]?.getPrecedence() ?: 0
    }
}
