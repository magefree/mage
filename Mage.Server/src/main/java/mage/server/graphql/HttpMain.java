package mage.server.graphql;

import graphql.schema.GraphQLSchema;
import org.eclipse.jetty.server.Handler;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.handler.HandlerList;
import org.eclipse.jetty.server.handler.ResourceHandler;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

/**
 * An very simple example of serving a qraphql schema over http and using websockets for subscriptions.
 * <p>
 * More info can be found here : http://graphql.org/learn/serving-over-http/
 */
@SuppressWarnings("unchecked")
public class HttpMain {
    private static final int PORT = 3000;

    public static void main(String[] args) throws Exception {
        //
        // This example uses Jetty as an embedded HTTP server
        Server server = new Server(PORT);

        //
        // In Jetty, handlers are how your get called back on a request
        ServletContextHandler servletContextHandler = new ServletContextHandler();
        servletContextHandler.setContextPath("/");

        ServletHolder stockTicker = new ServletHolder("ws-stockticker", GameServlet.class);
        servletContextHandler.addServlet(stockTicker, "/stockticker");

        // this allows us to server our index.html and GraphIQL JS code
        ResourceHandler resource_handler = new ResourceHandler();
        resource_handler.setDirectoriesListed(false);
        resource_handler.setWelcomeFiles(new String[]{"index.html"});
        resource_handler.setResourceBase("./src/main/resources/httpmain");

        HandlerList handlers = new HandlerList();
        handlers.setHandlers(new Handler[]{resource_handler, servletContextHandler});
        server.setHandler(handlers);

        server.start();

        server.join();
    }
}