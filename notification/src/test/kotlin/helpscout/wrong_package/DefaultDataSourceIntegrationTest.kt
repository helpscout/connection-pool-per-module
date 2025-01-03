package helpscout.wrong_package

import helpscout.shared.config.DataSourceContext
import helpscout.shared.config.ModuleName.DEFAULT
import helpscout.shared.config.RoutingDataSourceTransactionInterceptors
import helpscout.wrong_package.DefaultDataSourceIntegrationTest.TestController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.context.annotation.EnableAspectJAutoProxy
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.testcontainers.containers.MySQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

@Testcontainers
@SpringBootTest(
    classes = [
        ServletWebServerFactoryAutoConfiguration::class,
        RoutingDataSourceTransactionInterceptors::class,
        TestController::class
    ],
    webEnvironment = RANDOM_PORT
)
@EnableAspectJAutoProxy
@AutoConfigureMockMvc
@ActiveProfiles("test")
class DefaultDataSourceIntegrationTest {

    @Autowired
    private lateinit var mvc: MockMvc

    companion object {
        @Container
        private val mysqlContainer = MySQLContainer("mysql:latest")
            .withDatabaseName("helpscout")

        @JvmStatic
        @DynamicPropertySource
        fun dynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.datasource.reading.url") { "jdbc:mysql://${mysqlContainer.host}:${mysqlContainer.firstMappedPort}/helpscout" }
            registry.add("spring.datasource.writing.url") { "jdbc:mysql://${mysqlContainer.host}:${mysqlContainer.firstMappedPort}/helpscout" }
        }
    }

    @Test
    fun `test endpoint for wrongly mapped package`() {
        mvc.perform(get("/test/default").contentType(APPLICATION_JSON))
            .andExpect(status().isOk())
    }


    @RestController
    @RequestMapping("/test")
    class TestController {
        @GetMapping("/default")
        fun getDefault() {
            assertThat(DataSourceContext.current).isEqualTo(DEFAULT)
        }
    }
}