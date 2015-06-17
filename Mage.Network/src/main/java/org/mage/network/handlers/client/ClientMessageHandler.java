package org.mage.network.handlers.client;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import mage.cards.decks.DeckCardLists;
import mage.game.match.MatchOptions;
import mage.view.RoomView;
import mage.view.TableView;
import org.mage.network.handlers.WriteListener;
import org.mage.network.interfaces.MageClient;
import org.mage.network.model.ChatMessageRequest;
import org.mage.network.model.ChatRoomIdRequest;
import org.mage.network.model.ClientMessage;
import org.mage.network.model.CreateTableRequest;
import org.mage.network.model.GetRoomRequest;
import org.mage.network.model.JoinChatRequest;
import org.mage.network.model.JoinGameRequest;
import org.mage.network.model.JoinTableRequest;
import org.mage.network.model.LeaveChatRequest;
import org.mage.network.model.LeaveTableRequest;
import org.mage.network.model.RemoveTableRequest;
import org.mage.network.model.ServerMessagesRequest;
import org.mage.network.model.StartMatchRequest;
import org.mage.network.model.SwapSeatRequest;
import org.mage.network.model.TableWaitingRequest;

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
    
    public void receiveStringList(List<String> list) {
        stringListQueue.offer(list);
    }

}
