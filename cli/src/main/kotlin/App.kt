import cli.Analyze
import cli.Cli
import cli.Execute
import cli.Format
import cli.Validate
import com.github.ajalt.clikt.core.subcommands

fun main(args: Array<String>) =
    Cli()
        .subcommands(Execute(), Format(), Analyze(), Validate())
        .main(args)
