package mage.remote;

import mage.utils.FluentBuilder;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;

public class ConnectionTest {

    static class ConnectionBuilder extends FluentBuilder<Connection, ConnectionBuilder> {

        public int port;
        public String host;
        public String parameter;

        private ConnectionBuilder() {
            super(ConnectionBuilder::new);
        }

        @Override
        protected Connection makeValue() {
            final Connection result = new Connection(parameter);
            result.setHost(host);
            result.setPort(port);
            return result;
        }
    }

    private ConnectionBuilder baseBuilder() {
        return new ConnectionBuilder();
    }

    class TestsTemplate {
        final ConnectionBuilder testeeBuilder;

        TestsTemplate(ConnectionBuilder testeeBuilder) {
            this.testeeBuilder = testeeBuilder;
        }

        @Test
        @DisplayName("produce the expected scheme")
        void scheme() throws Exception {
            final URI testee = make(testeeBuilder);
            assertThat(testee.getScheme()).isEqualTo("bisocket");
        }

        URI make(ConnectionBuilder builder) {
            try {
                return new URI(builder.build().getURI());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        @Test
        @DisplayName("generate the expected port")
        void port() {
            final int expected = RandomUtils.nextInt(1000, 65000);
            final int port = make(testeeBuilder.with(c -> c.port = expected)).getPort();

            assertThat(port).isEqualTo(expected);
        }

        @Test
        @DisplayName("generate the expected serialisation parameter")
        void serialisation() {
            final String query = make(testeeBuilder).getQuery();

            assertThat(query).contains("serializationtype=java");
        }

        @Test
        @DisplayName("generate the expected threadpool parameter")
        void threadpool() {
            final String parameter = RandomStringUtils.randomAlphanumeric(12);
            final String query = make(testeeBuilder.with(c -> c.parameter = parameter)).getQuery();

            assertThat(query).contains("onewayThreadPool=mage.remote.CustomThreadPool" + parameter);
        }

    }

    @Nested
    @DisplayName("getUri when host is localhost should")
    class LocalhostTest extends TestsTemplate {

        LocalhostTest() {
            super(baseBuilder().with(c -> c.host = "localhost"));
        }

        @Test
        @DisplayName("generate an ipv4 as host")
        void ipv4Gen() {
            final String host = make(testeeBuilder).getHost();

            assertThat(host).matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
        }
    }

    private final String randomHost = RandomStringUtils.randomAlphabetic(15);

    @Nested
    @DisplayName("getUri when host is not localhost should")
    class StandardHostTest extends TestsTemplate {
        StandardHostTest() {
            super(baseBuilder().with(c -> c.host = randomHost));
        }

        @Test
        @DisplayName("generate the selected host as host")
        void hostGen() {
            final String host = make(testeeBuilder).getHost();

            assertThat(host).isEqualTo(randomHost);
        }
    }
}
