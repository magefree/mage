package mage.server.util;

import mage.server.util.config.Server;
import org.apache.http.client.utils.URIBuilder;
import org.apache.log4j.Logger;

import java.net.*;
import java.util.Enumeration;
import java.util.Optional;
import java.util.stream.Collectors;

public class ServerConnection {

    private static final Logger logger = Logger.getLogger(ServerConnection.class);

    private final Server server;
    private final String defaultHostAddress;

    public ServerConnection(final Server server, String defaultHostAddress) {
        this.server = server;
        this.defaultHostAddress = defaultHostAddress;
    }

    public static String computeLocalAddress() {
        try {
            for (Enumeration<NetworkInterface> interfaces = NetworkInterface.getNetworkInterfaces(); interfaces.hasMoreElements(); ) {
                NetworkInterface iface = interfaces.nextElement();
                if (iface.isLoopback()) {
                    continue;
                }
                for (InterfaceAddress addr : iface.getInterfaceAddresses()) {
                    if (addr != null) {
                        InetAddress iaddr = addr.getAddress();
                        if (iaddr instanceof Inet4Address) {
                            return iaddr.getHostAddress();
                        }
                    }
                }
            }
            return "localhost";
        } catch (SocketException e) {
            logger.error("Could not resolve the local address. Returning localhost.", e);
            return "localhost";
        }
    }

    public URI getUri() {
        try {
            if (!server.isMultihome()) {
                return standardGenerator();
            } else {
                return multiHomeGenerator();
            }
        } catch (URISyntaxException e) {
            throw new ConfigurationException(e);
        }
    }

    private URIBuilder baseBuilder() {
        return new URIBuilder()
                .setScheme("bisocket")
                .addParameter("serializationtype", "jboss")
                .addParameter("maxPoolSize", String.valueOf(server.getMaxPoolSize().intValue()))
                .addParameter("onewayThreadPool", "mage.remote.CustomThreadPool");
    }

    private URI standardGenerator() throws URISyntaxException {
        final URIBuilder baseBuilder = baseBuilder()
                .setPort(server.getPort().intValue());
        if (server.getServerAddress().equals("localhost")) {
            return baseBuilder
                    .setHost(defaultHostAddress)
                    .build();
        }
        return baseBuilder
                .setHost(server.getServerAddress())
                .build();
    }

    private URI multiHomeGenerator() throws URISyntaxException {
        final String homes = server.getHome().stream()
                .map(h -> String.format("%s:%d", h.getInternal(), h.getPort()))
                .collect(Collectors.joining("!"));
        final String connectHomes = server.getHome().stream()
                .map(h -> String.format("%s:%d", h.getExternal(), Optional.ofNullable(h.getExternalport())
                        .orElse(h.getPort())))
                .collect(Collectors.joining("!"));
        return baseBuilder()
                .setPort(-1)
                .setHost("multihome")
                .addParameter("homes", homes)
                .addParameter("connecthomes", connectHomes)
                .build();

    }
}
