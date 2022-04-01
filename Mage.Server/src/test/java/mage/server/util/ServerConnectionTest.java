package mage.server.util;

import mage.server.util.config.Server;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.Arrays;
import java.util.Collections;
import java.util.function.Consumer;

import static org.assertj.core.api.Assertions.assertThat;

public class ServerConnectionTest {

    private final String hostAddress = RandomStringUtils.randomAlphabetic(20);

    class NonMultiHomeTemplate {
        final Server server;
        final ServerConnection testee;

        NonMultiHomeTemplate(Server server) {
            this.server = server;
            this.testee = new ServerConnection(server, hostAddress);
        }

        @Test
        @DisplayName("produce the expected scheme")
        void scheme() {
            assertThat(testee.getUri().getScheme()).isEqualTo("bisocket");
        }

        @Test
        @DisplayName("produce the expected port")
        void port() {
            assertThat(testee.getUri().getPort()).isEqualTo(server.getPort().intValue());
        }

        @Test
        @DisplayName("produce the expected serialisation parameter")
        void serialisation() {
            final String query = testee.getUri().getQuery();

            assertThat(query).contains("serializationtype=jboss");
        }

        @Test
        @DisplayName("produce the expecte thread pool")
        void threadPool() {
            final String query = testee.getUri().getQuery();

            assertThat(query).contains("onewayThreadPool=mage.remote.CustomThreadPool");
        }

        @Test
        @DisplayName("produce the expected max pool size parameter")
        void threadPoolSize() {
            final String query = testee.getUri().getQuery();

            assertThat(query).contains("maxPoolSize=" + server.getMaxPoolSize().intValue());
        }
    }

    @Nested
    @DisplayName("when not multihome with localhost")
    class NonMultihome extends NonMultiHomeTemplate {
        NonMultihome() {
            super(new Builders.ServerBuilder().with(s -> {
                s.multihome = false;
                s.serverAddress = "localhost";
                s.port = RandomUtils.nextInt(1000, 65000);
                s.maxPoolSize = RandomUtils.nextInt(1, 15);
            }).build());
        }

        @Test
        @DisplayName("generate an the construction host")
        void ipv4Gen() {
            assertThat(testee.getUri().getHost())
                    .isEqualTo(hostAddress);
        }
    }

    private final String randomHost = RandomStringUtils.randomAlphabetic(15);

    @Nested
    @DisplayName("when not multihome not on localhost")
    class StandardHostTest extends NonMultiHomeTemplate {
        StandardHostTest() {
            super(new Builders.ServerBuilder().with(s -> {
                s.multihome = false;
                s.serverAddress = randomHost;
                s.port = RandomUtils.nextInt(1000, 65000);
                s.maxPoolSize = RandomUtils.nextInt(1, 15);
            }).build());
        }

        @Test
        @DisplayName("generate the selected host as host")
        void hostGen() {
            assertThat(testee.getUri().getHost()).isEqualTo(randomHost);
        }
    }

    @Nested
    @DisplayName("when multihome")
    class MultiHome {
        private final int maxPoolSize = RandomUtils.nextInt(1, 15);
        private final Builders.ServerBuilder serverBuilder = new Builders.ServerBuilder().with(s -> {
            s.multihome = true;
            s.maxPoolSize = maxPoolSize;
        });

        private final Consumer<Builders.HomeBuilder> randomBuilder = h -> {
            h.internal = RandomStringUtils.randomAlphanumeric(15);
            h.external = RandomStringUtils.randomAlphanumeric(15);
            h.port = RandomUtils.nextInt();
        };

        @Test
        @DisplayName("should set scheme to bisocket")
        void socket() {
            final URI uri = baseTestee();

            assertThat(uri.getScheme()).isEqualTo("bisocket");
        }

        private URI baseTestee() {
            return new ServerConnection(serverBuilder.with(s ->
                    s.home = Collections.singletonList(new Builders.HomeBuilder()
                            .with(randomBuilder).build())
            ).build(), hostAddress).getUri();
        }

        @Test
        @DisplayName("produce the expected serialisation parameter")
        void serialisation() {
            final String query = baseTestee().getQuery();

            assertThat(query).contains("serializationtype=jboss");
        }

        @Test
        @DisplayName("produce the expecte thread pool")
        void threadPool() {
            final String query = baseTestee().getQuery();

            assertThat(query).contains("onewayThreadPool=mage.remote.CustomThreadPool");
        }

        @Test
        @DisplayName("produce the expected max pool size parameter")
        void threadPoolSize() {
            final String query = baseTestee().getQuery();

            assertThat(query).contains("maxPoolSize=" + maxPoolSize);
        }

        @Test
        @DisplayName("should not set the port")
        void noPort() {
            final URI uri = new ServerConnection(serverBuilder.with(s ->
                    s.home = Collections.singletonList(new Builders.HomeBuilder()
                            .with(randomBuilder).build())
            ).build(), hostAddress).getUri();

            assertThat(uri.getPort()).isEqualTo(-1);
            assertThat(uri.toString()).contains("/multihome");
        }

        @Test
        @DisplayName("should set multihome as host")
        void multihomeHost() {
            final URI uri = new ServerConnection(serverBuilder.with(s ->
                    s.home = Collections.singletonList(new Builders.HomeBuilder()
                            .with(randomBuilder).build())
            ).build(), hostAddress).getUri();

            assertThat(uri.getHost()).isEqualTo("multihome");
        }

        @Test
        @DisplayName("should set one home")
        void oneHome() {
            final String internal = RandomStringUtils.randomAlphabetic(20);
            final String external = RandomStringUtils.randomAlphabetic(20);
            final int port = RandomUtils.nextInt(1000, 65000);
            final int externalPort = RandomUtils.nextInt(1000, 65000);
            final URI uri = new ServerConnection(serverBuilder.with(s ->
                    s.home = Collections.singletonList(new Builders.HomeBuilder()
                            .with(h -> {
                                h.external = external;
                                h.internal = internal;
                                h.port = port;
                                h.externalPort = externalPort;
                            }).build())
            ).build(), hostAddress).getUri();
            final String queryString = uri.getQuery();

            assertThat(queryString).contains(String.format("homes=%s:%d", internal, port));
            assertThat(queryString).contains(String.format("connecthomes=%s:%d", external, externalPort));
        }

        @Test
        @DisplayName("should set multiple homes")
        void multipleHomes() {
            final String internal1 = RandomStringUtils.randomAlphabetic(20),
                    internal2 = RandomStringUtils.randomAlphabetic(20);
            final String external1 = RandomStringUtils.randomAlphabetic(20),
                    external2 = RandomStringUtils.randomAlphabetic(20);
            final int port1 = RandomUtils.nextInt(1000, 65000),
                    port2 = RandomUtils.nextInt(1000, 65000);
            final int externalPort1 = RandomUtils.nextInt(1000, 65000),
                    externalPort2 = RandomUtils.nextInt(1000, 65000);
            final URI uri = new ServerConnection(serverBuilder.with(s ->
                    s.home = Arrays.asList(new Builders.HomeBuilder()
                            .with(h -> {
                                h.external = external1;
                                h.internal = internal1;
                                h.port = port1;
                                h.externalPort = externalPort1;
                            }).build(), new Builders.HomeBuilder()
                            .with(h -> {
                                h.external = external2;
                                h.internal = internal2;
                                h.port = port2;
                                h.externalPort = externalPort2;
                            }).build())
            ).build(), hostAddress).getUri();
            final String queryString = uri.getQuery();

            assertThat(queryString).contains(String.format("homes=%s:%d!%s:%d", internal1, port1, internal2, port2));
            assertThat(queryString).contains(String.format("connecthomes=%s:%d!%s:%d", external1, externalPort1, external2, externalPort2));
        }

        @Test
        @DisplayName("should set same port if external port not set")
        void onePort() {
            final String internal = RandomStringUtils.randomAlphabetic(20);
            final String external = RandomStringUtils.randomAlphabetic(20);
            final int port = RandomUtils.nextInt(1000, 65000);
            final URI uri = new ServerConnection(serverBuilder.with(s ->
                    s.home = Collections.singletonList(new Builders.HomeBuilder()
                            .with(h -> {
                                h.external = external;
                                h.internal = internal;
                                h.port = port;
                            }).build())
            ).build(), hostAddress).getUri();
            final String queryString = uri.getQuery();

            assertThat(queryString).contains(String.format("homes=%s:%d", internal, port));
            assertThat(queryString).contains(String.format("connecthomes=%s:%d", external, port));
        }
    }

    @Test
    @DisplayName("computeLocalAddress should return an ipv4")
    void ipv4ForLocalAddress() {
        assertThat(ServerConnection.computeLocalAddress()).matches("[0-9]+\\.[0-9]+\\.[0-9]+\\.[0-9]+");
    }
}
