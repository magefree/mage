package mage.server.graphql;

import graphql.ExecutionInput;
import graphql.ExecutionResult;
import graphql.GraphQL;
import graphql.execution.instrumentation.ChainedInstrumentation;
import graphql.execution.instrumentation.Instrumentation;
import graphql.execution.instrumentation.tracing.TracingInstrumentation;
import mage.server.graphql.utill.JsonKit;
import mage.server.graphql.utill.QueryParameters;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WebSocketAdapter;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Collections.singletonList;
import java.util.logging.Level;

/**
 * A websocket object is created per browser client.  This is the main interface code between the backing
 * publisher of event objects, graphql subscriptions in the middle and responses back to the browser.
 */

public class GameWebSocket extends WebSocketAdapter {

    private static final Logger log = LoggerFactory.getLogger(GameWebSocket.class);

    private final GameGraphqlPublisher graphqlPublisher = new GameGraphqlPublisher();
    private final AtomicReference<Subscription> subscriptionRef = new AtomicReference<>();

    @Override
    public void onWebSocketConnect(Session session) {
        super.onWebSocketConnect(session);
    }

    @Override
    public void onWebSocketClose(int statusCode, String reason) {
        log.info("Closing web socket");
        super.onWebSocketClose(statusCode, reason);
        Subscription subscription = subscriptionRef.get();
        if (subscription != null) {
            subscription.cancel();
        }
    }

    @Override
    public void onWebSocketText(String graphqlQuery) {
        log.info("Websocket said {}", graphqlQuery);

        QueryParameters parameters = QueryParameters.from(graphqlQuery);

        ExecutionInput executionInput = ExecutionInput.newExecutionInput()
                .query(parameters.getQuery())
                .variables(parameters.getVariables())
                .operationName(parameters.getOperationName())
                .build();

        Instrumentation instrumentation = new ChainedInstrumentation(
                singletonList(new TracingInstrumentation())
        );

        //
        // In order to have subscriptions in graphql-java you MUST use the
        // SubscriptionExecutionStrategy strategy.
        //
        GraphQL graphQL = GraphQL
                .newGraphQL(graphqlPublisher.getGraphQLSchema())
                .instrumentation(instrumentation)
                .build();

        ExecutionResult executionResult = graphQL.execute(executionInput);

        Publisher<ExecutionResult> stockPriceStream = executionResult.getData();

        stockPriceStream.subscribe(new Subscriber<ExecutionResult>() {

            @Override
            public void onSubscribe(Subscription s) {
                subscriptionRef.set(s);
                request(1);
            }

            @Override
            public void onNext(ExecutionResult er) {
                log.debug("Sending stick price update");
                Object stockPriceUpdate = er.getData();

                try {
                    getRemote().sendString(JsonKit.toJsonString(stockPriceUpdate));
                } catch (IOException ex) {
                    java.util.logging.Logger.getLogger(GameWebSocket.class.getName()).log(Level.SEVERE, null, ex);
                }
                request(1);
            }

            @Override
            public void onError(Throwable t) {
                log.error("Subscription threw an exception", t);
                getSession().close();
            }

            @Override
            public void onComplete() {
                log.info("Subscription complete");
                getSession().close();
            }
        });
    }

    private void request(int n) {
        Subscription subscription = subscriptionRef.get();
        if (subscription != null) {
            subscription.request(n);
        }
    }
}