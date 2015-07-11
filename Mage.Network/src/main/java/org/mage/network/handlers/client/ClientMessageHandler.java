package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.io.Serializable;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.cards.decks.DeckCardLists;
import mage.constants.ManaType;
import mage.constants.PlayerAction;
import mage.game.match.MatchOptions;
import mage.game.tournament.TournamentOptions;
import mage.view.DraftPickView;
import mage.view.RoomView;
import mage.view.TableView;
import mage.view.TournamentView;
import mage.view.UserDataView;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageClient;
import org.mage.network.messages.ClientMessage;
import org.mage.network.messages.requests.ChatMessageRequest;
import org.mage.network.messages.requests.ChatRoomIdRequest;
import org.mage.network.messages.requests.CreateTableRequest;
import org.mage.network.messages.requests.CreateTournamentRequest;
import org.mage.network.messages.requests.GetRoomRequest;
import org.mage.network.messages.requests.GetTournamentChatIdRequest;
import org.mage.network.messages.requests.GetTournamentRequest;
import org.mage.network.messages.requests.JoinChatRequest;
import org.mage.network.messages.requests.JoinDraftRequest;
import org.mage.network.messages.requests.JoinGameRequest;
import org.mage.network.messages.requests.JoinTableRequest;
import org.mage.network.messages.requests.JoinTournamentRequest;
import org.mage.network.messages.requests.JoinTournamentTableRequest;
import org.mage.network.messages.requests.LeaveChatRequest;
import org.mage.network.messages.requests.LeaveTableRequest;
import org.mage.network.messages.requests.MarkCardRequest;
import org.mage.network.messages.requests.PickCardRequest;
import org.mage.network.messages.requests.PlayerActionRequest;
import org.mage.network.messages.requests.QuitDraftRequest;
import org.mage.network.messages.requests.QuitMatchRequest;
import org.mage.network.messages.requests.QuitTournamentRequest;
import org.mage.network.messages.requests.RemoveTableRequest;
import org.mage.network.messages.requests.SendFeedbackRequest;
import org.mage.network.messages.requests.SendPlayerBooleanRequest;
import org.mage.network.messages.requests.SendPlayerIntegerRequest;
import org.mage.network.messages.requests.SendPlayerManaTypeRequest;
import org.mage.network.messages.requests.SendPlayerStringRequest;
import org.mage.network.messages.requests.SendPlayerUUIDRequest;
import org.mage.network.messages.requests.ServerMessagesRequest;
import org.mage.network.messages.requests.SetPreferencesRequest;
import org.mage.network.messages.requests.StartMatchRequest;
import org.mage.network.messages.requests.StartTournamentRequest;
import org.mage.network.messages.requests.StopWatchingRequest;
import org.mage.network.messages.requests.SubmitDeckRequest;
import org.mage.network.messages.requests.SwapSeatRequest;
import org.mage.network.messages.requests.TableWaitingRequest;
import org.mage.network.messages.requests.UpdateDeckRequest;
import org.mage.network.messages.requests.WatchTableRequest;
import org.mage.network.messages.requests.WatchTournamentTableRequest;

/**
 *
 * @author BetaSteward
 */
public class ClientMessageHandler extends SimpleChannelInboundHandler<ClientMessage> {
    
    private final MageClient client;
    private ChannelHandlerContext ctx;
    private final BlockingQueue<Boolean> booleanQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<UUID> uuidQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<RoomView> roomViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<TableView> tableViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<TournamentView> tournamentViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<DraftPickView> draftPickViewQueue = new LinkedBlockingQueue<>();
    private final BlockingQueue<List<String>> stringListQueue = new LinkedBlockingQueue<>();

    public ClientMessageHandler (MageClient client) {
        this.client = client;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctx = ctx;
        super.channelActive(ctx);
    }    
    
    @Override
    public void messageReceived(ChannelHandlerContext ctx, ClientMessage msg) {
        msg.handleMessage(this);
    }
        
    public List<String> getServerMessages() throws Exception {
        stringListQueue.clear();
        ctx.writeAndFlush(new ServerMessagesRequest()).addListener(WriteListener.getInstance());
        return stringListQueue.take();
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
    
    public boolean joinTable(UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinTableRequest(roomId, tableId, name, playerType, skill, deckList, password)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }
    
    public boolean joinTournamentTable(UUID roomId, UUID tableId, String name, String playerType, int skill, DeckCardLists deckList, String password) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinTournamentTableRequest(roomId, tableId, name, playerType, skill, deckList, password)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
    }

    public boolean joinDraft(UUID draftId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinDraftRequest(draftId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
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

    public void quitDraft(UUID draftId) {
        ctx.writeAndFlush(new QuitDraftRequest(draftId)).addListener(WriteListener.getInstance());
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

    public void removeTable(UUID roomId, UUID tableId) throws Exception {
        ctx.writeAndFlush(new RemoveTableRequest(roomId, tableId)).addListener(WriteListener.getInstance());
    }

    public void swapSeats(UUID roomId, UUID tableId, int seatNum1, int seatNum2) throws Exception {
        ctx.writeAndFlush(new SwapSeatRequest(roomId, tableId, seatNum1, seatNum2)).addListener(WriteListener.getInstance());
    }
    
    public MageClient getClient() {
        return client;
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
        tableViewQueue.offer(view);
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

    public void setPreferences(UserDataView view) {
        ctx.writeAndFlush(new SetPreferencesRequest(view)).addListener(WriteListener.getInstance());
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

    public boolean joinTournament(UUID tournamentId) throws Exception {
        booleanQueue.clear();
        ctx.writeAndFlush(new JoinTournamentRequest(tournamentId)).addListener(WriteListener.getInstance());
        return booleanQueue.take();
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

    public void stopWatching(UUID gameId) {
        ctx.writeAndFlush(new StopWatchingRequest(gameId)).addListener(WriteListener.getInstance());
    }

}
