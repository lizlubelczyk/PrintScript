package runner

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource
import runner.utils.ErrorCollector
import runner.utils.StringWriter
import runner.utils.TestObserver
import java.io.File
import java.util.stream.Stream

class FormatterTests {
    companion object {
        @JvmStatic
        fun data(): Stream<String> {
            return Stream.of(
                "simple-test",
                "test-print",
            )
        }
    }

    @ParameterizedTest
    @MethodSource("data")
    fun testFormatter(directory: String) {
        val observer = TestObserver()
        val errorHandler = ErrorCollector()
        val runner =
            Runner(
                listOf(
                    observer,
                ),
            )

        val file = File("src/test/resources/formatter/$directory/main.ps")
        val expected = File("src/test/resources/formatter/$directory/formatted.ps").readText()
        val config = File("src/test/resources/formatter/$directory/config.json")

        val stringWriter = StringWriter()

        runner.runFormat(file, stringWriter, config, errorHandler)

        assertEquals(stringWriter.toString(), expected)
    }
}
