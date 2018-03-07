package mage.server.graphql;

import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;

/**
 * In Jetty this is how you map a servlet to a websocket per request
 */
public class GameServlet extends WebSocketServlet {
    @Override
    public void configure(WebSocketServletFactory factory) {
        factory.getPolicy().setMaxTextMessageBufferSize(1024 * 1024);
        factory.getPolicy().setIdleTimeout(30 * 1000);
        factory.register(GameWebSocket.class);
    }
}
