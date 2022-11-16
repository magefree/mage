package mage.remote.handlers.client;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.cards.decks.DeckCardLists;
import mage.cards.repository.CardInfo;
import mage.cards.repository.ExpansionInfo;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.interfaces.ServerState;
import mage.players.PlayerType;
import mage.players.net.UserData;
import mage.remote.Connection;
import mage.utils.MageVersion;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserView;
import org.apache.log4j.Logger;
import mage.remote.handlers.WriteListener;
import mage.remote.interfaces.MageClient;
import mage.remote.messages.ClientMessage;
import mage.remote.messages.requests.ChatMessageRequest;
import mage.remote.messages.requests.ChatRoomIdRequest;
import mage.remote.messages.requests.CheatRequest;
import mage.remote.messages.requests.ConnectUserRequest;
import mage.remote.messages.requests.CreateTableRequest;
import mage.remote.messages.requests.CreateTournamentRequest;
import mage.remote.messages.requests.DisconnectUserRequest;
import mage.remote.messages.requests.EmailAuthTokenRequest;
import mage.remote.messages.requests.EndUserSessionRequest;
import mage.remote.messages.requests.GetCardInfoRequest;
import mage.remote.messages.requests.GetCardsRequest;
import mage.remote.messages.requests.GetExpansionInfoRequest;
import mage.remote.messages.requests.GetMainRoomIdRequest;
import mage.remote.messages.requests.GetRoomRequest;
import mage.remote.messages.requests.GetTablesRequest;
import mage.remote.messages.requests.GetTournamentChatIdRequest;
import mage.remote.messages.requests.GetTournamentRequest;
import mage.remote.messages.requests.GetUsersRequest;
import mage.remote.messages.requests.JoinChatRequest;
import mage.remote.messages.requests.JoinDraftRequest;
import mage.remote.messages.requests.JoinGameRequest;
import mage.remote.messages.requests.JoinTableRequest;
import mage.remote.messages.requests.JoinTournamentRequest;
import mage.remote.messages.requests.JoinTournamentTableRequest;
import mage.remote.messages.requests.LeaveChatRequest;
import mage.remote.messages.requests.LeaveTableRequest;
import mage.remote.messages.requests.LockUserRequest;
import mage.remote.messages.requests.MarkCardRequest;
import mage.remote.messages.requests.MuteUserRequest;
import mage.remote.messages.requests.NextPlayRequest;
import mage.remote.messages.requests.PickCardRequest;
import mage.remote.messages.requests.PingRequest;
import mage.remote.messages.requests.PlayerActionRequest;
import mage.remote.messages.requests.PreviousPlayRequest;
import mage.remote.messages.requests.QuitDraftRequest;
import mage.remote.messages.requests.QuitMatchRequest;
import mage.remote.messages.requests.QuitTournamentRequest;
import mage.remote.messages.requests.RegisterUserRequest;
import mage.remote.messages.requests.RemoveTableRequest;
import mage.remote.messages.requests.ReplayGameRequest;
import mage.remote.messages.requests.ResetPasswordRequest;
import mage.remote.messages.requests.SendBroadcastMessageRequest;
import mage.remote.messages.requests.SendFeedbackRequest;
import mage.remote.messages.requests.SendPlayerBooleanRequest;
import mage.remote.messages.requests.SendPlayerIntegerRequest;
import mage.remote.messages.requests.SendPlayerManaTypeRequest;
import mage.remote.messages.requests.SendPlayerStringRequest;
import mage.remote.messages.requests.SendPlayerUUIDRequest;
import mage.remote.messages.requests.ServerMessagesRequest;
import mage.remote.messages.requests.SetActivationRequest;
import mage.remote.messages.requests.SetBoosterLoadedRequest;
import mage.remote.messages.requests.SetPreferencesRequest;
import mage.remote.messages.requests.SkipForwardRequest;
import mage.remote.messages.requests.StartMatchRequest;
import mage.remote.messages.requests.StartReplayRequest;
import mage.remote.messages.requests.StartTournamentRequest;
import mage.remote.messages.requests.StopReplayRequest;
import mage.remote.messages.requests.StopWatchingRequest;
import mage.remote.messages.requests.SubmitDeckRequest;
import mage.remote.messages.requests.SwapSeatRequest;
import mage.remote.messages.requests.TableWaitingRequest;
import mage.remote.messages.requests.ToggleActivationRequest;
import mage.remote.messages.requests.UpdateDeckRequest;
import mage.remote.messages.requests.WatchGameRequest;
import mage.remote.messages.requests.WatchTableRequest;
import mage.remote.messages.requests.WatchTournamentTableRequest;

/**
 *
 * @author BetaSteward
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler<Serializable> {

    private final MageClient client;
    private ChannelHandlerContext ctx;
    private final BlockingQueue<Boolean> booleanQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<UUID> uuidQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<RoomView> roomViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<TableView> tableViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<TournamentView> tournamentViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<DraftPickView> draftPickViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<String>> stringListQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<CardInfo>> cardInfoListQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<ExpansionInfo>> expansionInfoListQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Optional<ServerState>> serverStateQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<UserView>> userViewListQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<Object> objectQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<TableView>> tableViewListQueue = new LinkedBlockingQueue<>();
    
    private Connection connection;
    private MageVersion version;
    private static final Logger logger = Logger.getLogger(ClientMessageHandler.class);
    private final boolean registering;

    public ClientMessageHandler(MageClient client, boolean registering) {
        this.client = client;
        this.registering = registering;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if(registering) {
            ctx.writeAndFlush(new RegisterUserRequest(connection, version)).addListener(new ClientMessageHandler.ListenerImpl());
        } else {
            ctx.writeAndFlush(new ConnectUserRequest(connection, version)).addListener(new ClientMessageHandler.ListenerImpl());
        }
        this.ctx = ctx;
        super.channelActive(ctx);
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Serializable msg) {
        if(msg instanceof ClientMessage) {
            ((ClientMessage)msg).handleMessage(this);
        }
    }

    public Object getServerMessages() throws Exception {
        objectQueue.clear();
        ctx.writeAndFlush(new ServerMessagesRequest()).addListener(WriteListener.getInstance());
        return objectQueue.take();
    }

    public List<String> getCards() throws Exception {
        stringListQueue.clear();
        ctx.writeAndFlush(new GetCardsRequest()).addListener(WriteListener.getInstance());
        return stringListQueue.take();
    }

    public List<CardInfo> getMissingCardsData(List<String> cards) throws Exception {
        cardInfoListQueue.clear();
        ctx.writeAndFlush(new GetCardInfoRequest(cards)).addListener(WriteListener.getInstance());
        return cardInfoListQueue.take();
    }

    public List<ExpansionInfo> getMissingExpansionData(List<String> setCodes) throws Exception {
        expansionInfoListQueue.clear();
        ctx.writeAndFlush(new GetExpansionInfoRequest(setCodes)).addListener(WriteListener.getInstance());
        return expansionInfoListQueue.take();
    }

    public UUID getChatRoomId(UUID roomId) throws Exception {
        uuidQueue.clear();
        ctx.writeAndFlush(new ChatRoomIdRequest(roomId)).addListener(WriteListener.getInstance());
        return uuidQueue.take();
    }

    public RoomView getRoom(UUID roomId) throws Exception {
        roomViewQueue.clear();
        ctx.writeAndFlush(new GetRoomRequest(roomId)).addListener(WriteListener.getInstance());
        return roomViewQueue.take();
    }

    public TableView createTable(UUID roomId, MatchOptions options) throws Exception {
        tableViewQueue.clear();
        ctx.writeAndFlush(new CreateTableRequest(roomId, options)).addListener(WriteListener.getInstance());
        return tableViewQueue.take();
    }

    public TableView getTable(UUID roomId, UUID tableId) throws Exception {
        tableViewQueue.clear();
        ctx.writeAndFlush(new TableWaitingRequest(roomId, tableId)).addListener(WriteListener.getInstance());
        return tableViewQueue.take();
    }

    public boolean joinTable(UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinTableRequest(roomId, tableId, name, playerType, skill, deckList, password)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public boolean joinTournamentTable(UUID roomId, UUID tableId, String name, PlayerType playerType, int skill, DeckCardLists deckList, String password) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinTournamentTableRequest(roomId, tableId, name, playerType, skill, deckList, password)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public void joinDraft(UUID draftId) throws Exception {
        ctx.writeAndFlush(new JoinDraftRequest(draftId)).addListener(WriteListener.getInstance());
    }

    public DraftPickView pickCard(UUID draftId, UUID cardId, Set<UUID> cardsHidden) throws Exception {
        draftPickViewQueue.clear();
        ctx.writeAndFlush(new PickCardRequest(draftId, cardId, cardsHidden)).addListener(WriteListener.getInstance());
        return draftPickViewQueue.take();
    }

    public boolean leaveTable(UUID roomId, UUID tableId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new LeaveTableRequest(roomId, tableId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public boolean startMatch(UUID roomId, UUID tableId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new StartMatchRequest(roomId, tableId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public UUID joinGame(UUID gameId) throws Exception {
        uuidQueue.clear();
        ctx.writeAndFlush(new JoinGameRequest(gameId)).addListener(WriteListener.getInstance());
        return uuidQueue.take();
    }

    public boolean submitDeck(UUID tableId, DeckCardLists deckCardLists) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new SubmitDeckRequest(tableId, deckCardLists)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public void updateDeck(UUID tableId, DeckCardLists deckCardLists) throws Exception {
        ctx.writeAndFlush(new UpdateDeckRequest(tableId, deckCardLists)).addListener(WriteListener.getInstance());
    }

    public void markCard(UUID draftId, UUID cardId) {
        ctx.writeAndFlush(new MarkCardRequest(draftId, cardId)).addListener(WriteListener.getInstance());
    }
    
    public void setBoosterLoaded(UUID draftId) {
        ctx.writeAndFlush(new SetBoosterLoadedRequest(draftId)).addListener(WriteListener.getInstance());
    }

    public void quitDraft(UUID draftId) {
        ctx.writeAndFlush(new QuitDraftRequest(draftId)).addListener(WriteListener.getInstance());
    }

    public void sendBroadcastMessage(String message) {
        ctx.writeAndFlush(new SendBroadcastMessageRequest(message)).addListener(WriteListener.getInstance());
    }

    public void disconnectUser(String string) {
        ctx.writeAndFlush(new DisconnectUserRequest(string)).addListener(WriteListener.getInstance());
    }

    public void removeTable(UUID uuid) {
        ctx.writeAndFlush(new RemoveTableRequest(uuid)).addListener(WriteListener.getInstance());
    }

    public void endUserSession(String string) {
        ctx.writeAndFlush(new EndUserSessionRequest(string)).addListener(WriteListener.getInstance());
    }
    
    public List<UserView> getUsers() throws Exception {
        userViewListQueue.clear();
        ctx.writeAndFlush(new GetUsersRequest()).addListener(WriteListener.getInstance());
        return userViewListQueue.take();
    }
    

    public void sendFeedback(String title, String type, String message, String email) {
        ctx.writeAndFlush(new SendFeedbackRequest(title, type, message, email)).addListener(WriteListener.getInstance());
    }

    public void joinChat(UUID chatId) {
        ctx.writeAndFlush(new JoinChatRequest(chatId)).addListener(WriteListener.getInstance());
    }

    public void leaveChat(UUID chatId) {
        ctx.writeAndFlush(new LeaveChatRequest(chatId)).addListener(WriteListener.getInstance());
    }

    public void sendMessage(UUID chatId, String message) {
        ctx.writeAndFlush(new ChatMessageRequest(chatId, message)).addListener(WriteListener.getInstance());
    }

    public void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws Exception {
        ctx.writeAndFlush(new SwapSeatRequest(roomId, tableId, seatNum1, seatNum2)).addListener(WriteListener.getInstance());
    }

    public MageClient getClient() {
        return client;
    }

    public void receiveServerState(Optional<ServerState> serverState) {
        serverStateQueue.offer(serverState);
    }

    public void receiveBoolean(boolean b) {
        booleanQueue.offer(b);
    }

    public void receiveId(UUID id) {
        uuidQueue.offer(id);
    }

    public void receiveRoomView(RoomView view) {
        roomViewQueue.offer(view);
    }

    public void receiveTableView(TableView view) {
        if (view == null) {
            tableViewQueue.offer(TableView.emptyTableView);
        } else {
            tableViewQueue.offer(view);
        }
    }

    public void receiveTournamentView(TournamentView view) {
        tournamentViewQueue.offer(view);
    }

    public void receiveDraftPickView(DraftPickView view) {
        draftPickViewQueue.offer(view);
    }

    public void receiveStringList(List<String> list) {
        stringListQueue.offer(list);
    }

    public void receiveCardInfoList(List<CardInfo> list) {
        cardInfoListQueue.offer(list);
    }

    public void receiveExpansionInfoList(List<ExpansionInfo> list) {
        expansionInfoListQueue.offer(list);
    }

    public void receiveUserViewList(List<UserView> list) {
        userViewListQueue.offer(list);
    }
    
    public void receiveTableViewList(List<TableView> list) {
        tableViewListQueue.offer(list);
    }
    
    public void receiveObject(Object object) {
        objectQueue.offer(object);
    }

    public void sendPlayerUUID(UUID gameId, UUID id) {
        ctx.writeAndFlush(new SendPlayerUUIDRequest(gameId, id)).addListener(WriteListener.getInstance());
    }

    public void sendPlayerBoolean(UUID gameId, boolean b) {
        ctx.writeAndFlush(new SendPlayerBooleanRequest(gameId, b)).addListener(WriteListener.getInstance());
    }

    public void sendPlayerInteger(UUID gameId, int i) {
        ctx.writeAndFlush(new SendPlayerIntegerRequest(gameId, i)).addListener(WriteListener.getInstance());
    }

    public void sendPlayerString(UUID gameId, String string) {
        ctx.writeAndFlush(new SendPlayerStringRequest(gameId, string)).addListener(WriteListener.getInstance());
    }

    public void sendPlayerManaType(UUID gameId, UUID playerId, ManaType manaType) {
        ctx.writeAndFlush(new SendPlayerManaTypeRequest(gameId, playerId, manaType)).addListener(WriteListener.getInstance());
    }

    public void sendPlayerAction(PlayerAction action, UUID gameId, Serializable data) {
        ctx.writeAndFlush(new PlayerActionRequest(action, gameId, data)).addListener(WriteListener.getInstance());
    }

    public void setPreferences(UserData userData) {
        ctx.writeAndFlush(new SetPreferencesRequest(userData)).addListener(WriteListener.getInstance());
    }

    public TableView createTournamentTable(UUID roomId, TournamentOptions options) throws Exception {
        tableViewQueue.clear();
        ctx.writeAndFlush(new CreateTournamentRequest(roomId, options)).addListener(WriteListener.getInstance());
        return tableViewQueue.take();
    }

    public boolean startTournament(UUID roomId, UUID tableId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new StartTournamentRequest(roomId, tableId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public void joinTournament(UUID tournamentId) throws Exception {
        ctx.writeAndFlush(new JoinTournamentRequest(tournamentId)).addListener(WriteListener.getInstance());
    }

    public void quitTournament(UUID tournamentId) {
        ctx.writeAndFlush(new QuitTournamentRequest(tournamentId)).addListener(WriteListener.getInstance());
    }

    public UUID getTournamentChatId(UUID tournamentId) throws Exception {
        uuidQueue.clear();
        ctx.writeAndFlush(new GetTournamentChatIdRequest(tournamentId)).addListener(WriteListener.getInstance());
        return uuidQueue.take();
    }

    public void quitMatch(UUID gameId) {
        ctx.writeAndFlush(new QuitMatchRequest(gameId)).addListener(WriteListener.getInstance());
    }

    public TournamentView getTournament(UUID tournamentId) throws Exception {
        tournamentViewQueue.clear();
        ctx.writeAndFlush(new GetTournamentRequest(tournamentId)).addListener(WriteListener.getInstance());
        return tournamentViewQueue.take();
    }

    public void watchTournamentTable(UUID tableId) {
        ctx.writeAndFlush(new WatchTournamentTableRequest(tableId)).addListener(WriteListener.getInstance());
    }

    public void watchTable(UUID roomId, UUID tableId) {
        ctx.writeAndFlush(new WatchTableRequest(roomId, tableId)).addListener(WriteListener.getInstance());
    }

    public void startReplay(UUID gameId) {
        ctx.writeAndFlush(new StartReplayRequest(gameId)).addListener(WriteListener.getInstance());
    }
    
    public void stopWatching(UUID gameId) {
        ctx.writeAndFlush(new StopWatchingRequest(gameId)).addListener(WriteListener.getInstance());
    }
    
    public void stopReplay(UUID gameId) {
        ctx.writeAndFlush(new StopReplayRequest(gameId)).addListener(WriteListener.getInstance());
    }
    
    public void nextPlay(UUID gameId) {
        ctx.writeAndFlush(new NextPlayRequest(gameId)).addListener(WriteListener.getInstance());
    }
    
    public void previousPlay(UUID gameId) {
        ctx.writeAndFlush(new PreviousPlayRequest(gameId)).addListener(WriteListener.getInstance());
    }
    
    public void skipForward(UUID gameId, int i) {
        ctx.writeAndFlush(new SkipForwardRequest(gameId, i)).addListener(WriteListener.getInstance());
    }

    public void cheat(UUID gameId, UUID playerId, DeckCardLists importDeck) {
        ctx.writeAndFlush(new CheatRequest(gameId, playerId, importDeck)).addListener(WriteListener.getInstance());
    }
    
    public Optional<ServerState> connectClient() throws InterruptedException {
        return serverStateQueue.take();
    }
    
    public void setConnection(Connection connection) {
        this.connection = connection;
    }
    
    public void setVersion(MageVersion version) {
        this.version = version;
    }
    
    private final class ListenerImpl implements ChannelFutureListener {
        @Override
        public void operationComplete(ChannelFuture future) throws Exception {
            if (!future.isSuccess()) {
                logger.error("Communication error", future.cause());
                serverStateQueue.offer(null);
            }
        }
    }
    
    public boolean registerUser() throws InterruptedException {
        return booleanQueue.take();
    }
    
    public boolean emailAuthToken(Connection connection) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new EmailAuthTokenRequest(connection)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }
    
    public boolean resetPassword(Connection connection) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new ResetPasswordRequest(connection)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }
    
    public UUID getMainRoomId() throws Exception {
        uuidQueue.clear();
        ctx.writeAndFlush(new GetMainRoomIdRequest()).addListener(WriteListener.getInstance());
        return uuidQueue.take();
    }
    
     public void muteUser(String userName, long durationMinutes) throws Exception{
         ctx.writeAndFlush(new MuteUserRequest(userName,durationMinutes)).addListener(WriteListener.getInstance());
     }
     
     public void setActivation(String userName, boolean active) throws Exception{
         ctx.writeAndFlush(new SetActivationRequest(userName,active)).addListener(WriteListener.getInstance());
     }
     
     public void toggleActivation(String userName) throws Exception{
         ctx.writeAndFlush(new ToggleActivationRequest(userName)).addListener(WriteListener.getInstance());
     }
     
     public void lockUser(String userName, long durationMinutes) throws Exception{
         ctx.writeAndFlush(new LockUserRequest(userName, durationMinutes)).addListener(WriteListener.getInstance());
     }
     
    public List<TableView> getTables(UUID roomId) throws Exception {
        tableViewListQueue.clear();
        ctx.writeAndFlush(new GetTablesRequest(roomId)).addListener(WriteListener.getInstance());
        return tableViewListQueue.take();
    }
    
    public boolean ping(Connection connection) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new PingRequest(connection)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }
    
    public boolean watchGame(UUID gameId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new WatchGameRequest(gameId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }
    
    public void replayGame(UUID gameId) {
        ctx.writeAndFlush(new ReplayGameRequest(gameId)).addListener(WriteListener.getInstance());
    }

}
