package mage.remote;

import mage.choices.Choice;
import mage.remote.handlers.PingMessageHandler;
import mage.remote.handlers.WriteListener;
import mage.remote.handlers.server.ConnectionHandler;
import mage.remote.handlers.server.HeartbeatHandler;
import mage.remote.handlers.server.ServerExceptionHandler;
import mage.remote.handlers.server.ServerRequestHandler;
import mage.remote.interfaces.MageServer;
import mage.remote.messages.MessageType;
import mage.remote.messages.callback.ChatMessageCallback;
import mage.remote.messages.callback.ConstructCallback;
import mage.remote.messages.callback.DraftInitCallback;
import mage.remote.messages.callback.DraftOverCallback;
import mage.remote.messages.callback.DraftPickCallback;
import mage.remote.messages.callback.DraftUpdateCallback;
import mage.remote.messages.callback.GameAskCallback;
import mage.remote.messages.callback.GameChooseAbilityCallback;
import mage.remote.messages.callback.GameChooseChoiceCallback;
import mage.remote.messages.callback.GameChoosePileCallback;
import mage.remote.messages.callback.GameEndInfoCallback;
import mage.remote.messages.callback.GameErrorCallback;
import mage.remote.messages.callback.GameInformCallback;
import mage.remote.messages.callback.GameInformPersonalCallback;
import mage.remote.messages.callback.GameInitCallback;
import mage.remote.messages.callback.GameMultiAmountCallback;
import mage.remote.messages.callback.GameOverCallback;
import mage.remote.messages.callback.GamePlayManaCallback;
import mage.remote.messages.callback.GamePlayXManaCallback;
import mage.remote.messages.callback.GameSelectAmountCallback;
import mage.remote.messages.callback.GameSelectCallback;
import mage.remote.messages.callback.GameStartedCallback;
import mage.remote.messages.callback.GameTargetCallback;
import mage.remote.messages.callback.GameUpdateCallback;
import mage.remote.messages.callback.InformClientCallback;
import mage.remote.messages.callback.JoinedTableCallback;
import mage.remote.messages.callback.ReplayDoneCallback;
import mage.remote.messages.callback.ReplayGameCallback;
import mage.remote.messages.callback.ReplayInitCallback;
import mage.remote.messages.callback.ReplayUpdateCallback;
import mage.remote.messages.callback.ShowTournamentCallback;
import mage.remote.messages.callback.SideboardCallback;
import mage.remote.messages.callback.StartDraftCallback;
import mage.remote.messages.callback.TournamentStartedCallback;
import mage.remote.messages.callback.UserRequestDialogCallback;
import mage.remote.messages.callback.ViewLimitedDeckCallback;
import mage.remote.messages.callback.ViewSideboardCallback;
import mage.remote.messages.callback.WatchGameCallback;
import mage.view.AbilityPickerView;
import mage.view.CardsView;
import mage.view.ChatMessage;
import mage.view.DeckView;
import mage.view.DraftPickView;
import mage.view.DraftView;
import mage.view.GameClientMessage;
import mage.view.GameEndView;
import mage.view.GameView;
import mage.view.UserRequestMessage;
import org.apache.log4j.Logger;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.concurrent.DefaultEventExecutorGroup;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;


/**Allows server to make requests to client
 * @author BetaSteward_at_googlemail.com
 */
public class ClientCallbackImpl implements ClientCallback {
    private static final Logger logger = Logger.getLogger(ClientCallbackImpl.class);

    private static final int IDLE_PING_TIME = 30;
    private static final int IDLE_TIMEOUT = 60;
//    private static final PingMessage ping = new PingMessage();

    private SslContext sslCtx;

    private final MageServer server;
//    private final MessageHandler h;
    private final PingMessageHandler pingMessageHandler = new PingMessageHandler();
    private final EventExecutorGroup handlersExecutor = new DefaultEventExecutorGroup(Runtime.getRuntime().availableProcessors() * 2);
    private final ServerRequestHandler serverMessageHandler;

    private final ServerExceptionHandler exceptionHandler;

    public ClientCallbackImpl(MageServer server) {
        this.server = server;
//        h = new MessageHandler();
        serverMessageHandler = new ServerRequestHandler(server);
        exceptionHandler = new ServerExceptionHandler(server);
    }

    @Override
    public void start(int port, boolean ssl) throws Exception {

        if (ssl) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContext.newServerContext(ssc.certificate(), ssc.privateKey());
        } else {
            sslCtx = null;
        }

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ServerInitializer());

            b.bind(port).sync().channel().closeFuture().sync();
        } catch (InterruptedException ex) {
            logger.fatal("Error starting server", ex);
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    private class ServerInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        public void initChannel(SocketChannel ch) throws Exception {

            if (sslCtx != null) {
                ch.pipeline().addLast(sslCtx.newHandler(ch.alloc()));
            }
            ch.pipeline().addLast(new ObjectDecoder(10 * 1024 * 1024, ClassResolvers.cacheDisabled(null)));
            ch.pipeline().addLast(new ObjectEncoder());

//            ch.pipeline().addLast("h", h);
            ch.pipeline().addLast("idleStateHandler", new IdleStateHandler(IDLE_TIMEOUT, IDLE_PING_TIME, 0));

            ch.pipeline().addLast(handlersExecutor, "heartbeatHandler", new HeartbeatHandler(server));
            ch.pipeline().addLast("pingMessageHandler", pingMessageHandler);

            ch.pipeline().addLast("connectionHandler", new ConnectionHandler());
            ch.pipeline().addLast(handlersExecutor, "serverMessageHandler", serverMessageHandler);
            ch.pipeline().addLast("exceptionHandler", exceptionHandler);
        }

    }

    private Channel findChannel(String sessionId) {
        for (Channel channel : clients) {
            if (channel.id().asLongText().equals(sessionId)) {
                return channel;
            }
        }
        return null;
    }

    @Override
    public void sendChatMessage(String sessionId, UUID chatId, ChatMessage message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ChatMessageCallback(chatId, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void informClient(String sessionId, String title, String message, MessageType type) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new InformClientCallback(title, message, type)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void informClients(String title, String message, MessageType type) {
        clients.writeAndFlush(new InformClientCallback(title, message, type)).addListener(WriteListener.getInstance());
    }

    @Override
    public void pingClient(String sessionId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            HeartbeatHandler heartbeatHandler = (HeartbeatHandler) ch.pipeline().get("heartbeatHandler");
            heartbeatHandler.pingClient();
        }
    }

    @Override
    public void joinedTable(String sessionId, UUID roomId, UUID tableId, UUID chatId, boolean owner, boolean tournament) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new JoinedTableCallback(roomId, tableId, chatId, owner, tournament)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameStarted(String sessionId, UUID gameId, UUID playerId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameStartedCallback(gameId, playerId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void initGame(String sessionId, UUID gameId, GameView gameView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameInitCallback(gameId, gameView)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameAsk(String sessionId, UUID gameId, GameView gameView, String question, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameAskCallback(gameId, gameView, question, options)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameTarget(String sessionId, UUID gameId, GameView gameView, String question, CardsView cardView, Set<UUID> targets, boolean required, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameTargetCallback(gameId, gameView, question, cardView, targets, required, options)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameSelect(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameSelectCallback(gameId, gameView, message, options)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameChooseAbility(String sessionId, UUID gameId, GameView gameView, AbilityPickerView abilities) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameChooseAbilityCallback(gameId, gameView, abilities)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameChoosePile(String sessionId, UUID gameId, GameView gameView, String message, CardsView pile1, CardsView pile2) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameChoosePileCallback(gameId, gameView, message, pile1, pile2)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameChooseChoice(String sessionId, UUID gameId, GameView gameView, Choice choice) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameChooseChoiceCallback(gameId, gameView, choice)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gamePlayMana(String sessionId, UUID gameId, GameView gameView, String message, Map<String, Serializable> options) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GamePlayManaCallback(gameId, gameView, message, options)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gamePlayXMana(String sessionId, UUID gameId, GameView gameView, String message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GamePlayXManaCallback(gameId, gameView, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameSelectAmount(String sessionId, UUID gameId, GameView gameView, String message, int min, int max) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameSelectAmountCallback(gameId, gameView, message, min, max)).addListener(WriteListener.getInstance());
        }
    }
    
    @Override
    public void gameMultiAmount(String sessionId, UUID gameId, GameView gameView, Map<String, Serializable> option, List<String> messages, int min, int max) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameMultiAmountCallback(gameId, gameView, option, messages, min, max)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void endGameInfo(String sessionId, UUID gameId, GameEndView view) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameEndInfoCallback(gameId, view)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void userRequestDialog(String sessionId, UUID gameId, UserRequestMessage userRequestMessage) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new UserRequestDialogCallback(gameId, userRequestMessage)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameUpdate(String sessionId, UUID gameId, GameView view) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameUpdateCallback(gameId, view)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameInform(String sessionId, UUID gameId, GameClientMessage message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameInformCallback(gameId, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameInformPersonal(String sessionId, UUID gameId, GameClientMessage message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameInformPersonalCallback(gameId, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameOver(String sessionId, UUID gameId, GameView view, String message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameOverCallback(gameId, view, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void gameError(String sessionId, UUID gameId, String message) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new GameErrorCallback(gameId, message)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void startDraft(String sessionId, UUID draftId, UUID playerId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new StartDraftCallback(draftId, playerId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void draftInit(String sessionId, UUID draftId, DraftPickView draftPickView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new DraftInitCallback(draftId, draftPickView)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void draftUpdate(String sessionId, UUID draftId, DraftView draftView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new DraftUpdateCallback(draftId, draftView)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void draftOver(String sessionId, UUID draftId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new DraftOverCallback(draftId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void draftPick(String sessionId, UUID draftId, DraftPickView draftPickView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new DraftPickCallback(draftId, draftPickView)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void sideboard(String sessionId, UUID tableId, DeckView deck, int time, boolean limited) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new SideboardCallback(tableId, deck, time, limited)).addListener(WriteListener.getInstance());
        }
    }
    
    @Override
    public void viewLimitedDeck(String sessionId, UUID tableId, DeckView deck, int time, boolean limited) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ViewLimitedDeckCallback(tableId, deck, time, limited)).addListener(WriteListener.getInstance());
        }
    }
    
    @Override
    public void viewSideboard(String sessionId, UUID gameId, UUID targetPlayerId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ViewSideboardCallback(gameId, targetPlayerId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void construct(String sessionId, UUID tableId, DeckView deck, int time) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ConstructCallback(tableId, deck, time)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void tournamentStarted(String sessionId, UUID tournamentId, UUID playerId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new TournamentStartedCallback(tournamentId, playerId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void showTournament(String sessionId, UUID tournamentId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ShowTournamentCallback(tournamentId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void watchGame(String sessionId, UUID gameId, UUID chatId, GameView game) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new WatchGameCallback(gameId, chatId, game)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void replayGame(String sessionId, UUID gameId) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ReplayGameCallback(gameId)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void replayInit(String sessionId, UUID gameId, GameView gameView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ReplayInitCallback(gameId, gameView)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void replayDone(String sessionId, UUID gameId, String result) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ReplayDoneCallback(gameId, result)).addListener(WriteListener.getInstance());
        }
    }

    @Override
    public void replayUpdate(String sessionId, UUID gameId, GameView gameView) {
        Channel ch = findChannel(sessionId);
        if (ch != null) {
            ch.writeAndFlush(new ReplayUpdateCallback(gameId, gameView)).addListener(WriteListener.getInstance());
        }
    }

}
