package mage.collectors.services;

import mage.collectors.DataCollector;
import mage.game.Game;
import mage.game.Table;
import mage.players.Player;

import java.util.UUID;

/**
 * Base implementation of Data Collector, do nothing. Use it to implement own or simple collectors, e.g. chats only collectors
 *
 * @author JayDi85
 */
public abstract class EmptyDataCollector implements DataCollector {

    @Override
    public String getInitInfo() {
        return "";
    }

    @Override
    public void onServerStart() {
        // nothing
    }

    @Override
    public void onTableStart(Table table) {
        // nothing
    }

    @Override
    public void onTableEnd(Table table) {
        // nothing
    }

    @Override
    public void onGameStart(Game game) {
        // nothing
    }

    @Override
    public void onGameLog(Game game, String message) {
        // nothing
    }

    @Override
    public void onGameEnd(Game game) {
        // nothing
    }

    @Override
    public void onChatRoom(UUID roomId, String userName, String message) {
        // nothing
    }

    @Override
    public void onChatTourney(UUID tourneyId, String userName, String message) {
        // nothing
    }

    @Override
    public void onChatTable(UUID tableId, String userName, String message) {
        // nothing
    }

    @Override
    public void onChatGame(UUID gameId, String userName, String message) {
        // nothing
    }

    @Override
    public void onTestsChoiceUse(Game game, Player player, String usingChoice, String reason) {
        // nothing
    }

    @Override
    public void onTestsTargetUse(Game game, Player player, String usingTarget, String reason) {
        // nothing
    }

    @Override
    public void onTestsStackPush(Game game) {
        // nothing
    }

    @Override
    public void onTestsStackResolve(Game game) {
        // nothing
    }
}
