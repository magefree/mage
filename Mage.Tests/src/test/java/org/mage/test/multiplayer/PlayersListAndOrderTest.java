package org.mage.test.multiplayer;

import mage.constants.MultiplayerAttackOption;
import mage.constants.PhaseStep;
import mage.constants.RangeOfInfluence;
import mage.game.FreeForAll;
import mage.game.Game;
import mage.game.GameException;
import mage.game.mulligan.MulliganType;
import mage.players.Player;
import mage.players.PlayerList;
import org.junit.Assert;
import org.junit.Test;
import org.mage.test.serverside.base.CardTestMultiPlayerBase;

import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author JayDi85
 */
public class PlayersListAndOrderTest extends CardTestMultiPlayerBase {

    // problem: some player's list can return random/unstable order (HashSet or HashMap instead linked list)
    // so make sure all relates players list return stable/linked collection, not random
    // also make sure some lists return in turn order
    // TODO: add reverse turn order support from Aeon Engine
    private List<UUID> needByAddedAll = new ArrayList<>();
    private List<UUID> needByApnapAllFromA = new ArrayList<>();
    private List<UUID> needByApnapAllFromB = new ArrayList<>();
    private List<UUID> needByApnapOpponentsFromA = new ArrayList<>();
    private List<UUID> needByApnapOpponentsFromB = new ArrayList<>();

    @Override
    protected Game createNewGameAndPlayers() throws GameException, FileNotFoundException {
        // Start Life = 2
        Game game = new FreeForAll(MultiplayerAttackOption.MULTIPLE, RangeOfInfluence.ALL, MulliganType.GAME_DEFAULT.getMulligan(0), 2, 7);
        // Player order: A -> D -> C -> B
        playerA = createPlayer(game, "PlayerA");
        playerB = createPlayer(game, "PlayerB");
        playerC = createPlayer(game, "PlayerC");
        playerD = createPlayer(game, "PlayerD");

        needByAddedAll.addAll(Arrays.asList(
                playerA.getId(),
                playerB.getId(),
                playerC.getId(),
                playerD.getId()
        ));
        needByApnapAllFromA.addAll(Arrays.asList(
                playerA.getId(),
                playerD.getId(),
                playerC.getId(),
                playerB.getId()
        ));
        needByApnapAllFromB.addAll(Arrays.asList(
                playerB.getId(),
                playerA.getId(),
                playerD.getId(),
                playerC.getId()
        ));
        needByApnapOpponentsFromA.addAll(Arrays.asList(
                playerD.getId(),
                playerC.getId(),
                playerB.getId()
        ));
        needByApnapOpponentsFromB.addAll(Arrays.asList(
                playerA.getId(),
                playerD.getId(),
                playerC.getId()
        ));

        return game;
    }

    private void assertPlayersListFromCollection(String info, List<UUID> need, Collection<UUID> source) {
        List<UUID> byArray = new ArrayList<>(source);
        assertPlayersList("toArray - " + info, need, byArray);

        // warning, do not optimize code here by IDE - it must use stream/iterator, not toArray (toArray called in addAll or constructor);
        List<UUID> byIterator = new ArrayList<>(source).stream().collect(Collectors.toList());
        assertPlayersList("iterator - " + info, need, byIterator);
    }

    private void assertPlayersList(String info, List<UUID> need, List<UUID> current) {
        int maxCheckCount = 100;
        IntStream.rangeClosed(1, maxCheckCount).forEach(i -> {
            if (!need.equals(current)) {
                List<Player> needPlayers = need.stream().map(id -> currentGame.getPlayer(id)).collect(Collectors.toList());
                List<Player> currentPlayers = current.stream().map(id -> currentGame.getPlayer(id)).collect(Collectors.toList());
                Assert.fail(info + "\n" + "need: " + needPlayers + "\n" + "find: " + currentPlayers);
            }
        });
    }

    @Test
    public void test_Game_GetPlayers() {
        // game.getPlayers() - added order, for inner game engine only

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            List<UUID> byValues = currentGame.getPlayers().values()
                    .stream()
                    .map(Player::getId)
                    .collect(Collectors.toList());
            assertPlayersList("game.getPlayers().values() - must return in added order", needByAddedAll, byValues);

            List<UUID> byKeys = new ArrayList<>(currentGame.getPlayers().keySet());
            assertPlayersList("game.getPlayers().keySet() - must return in added order", needByAddedAll, byKeys);
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Game_GetPlayerList() {
        // game.getPlayerList() - APNAP order, for cards usage

        PlayerList list = new PlayerList();
        list.add(playerA.getId());
        list.add(playerB.getId());
        list.add(playerC.getId());
        list.add(playerD.getId());
        list.setCurrent(playerA.getId());
        List<UUID> current = new ArrayList<>(list);
        assertPlayersList("for fast debug", needByApnapAllFromA, current);

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPlayersListFromCollection(
                    "game.getPlayerList() - must return in APNAP order",
                    needByApnapAllFromA,
                    currentGame.getPlayerList()
            );
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_Game_GetOpponents() {
        // game.getOpponents() - APNAP order, for cards usage

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPlayersListFromCollection(
                    "game.getOpponents(from player A) - must return in APNAP order",
                    needByApnapOpponentsFromA,
                    currentGame.getOpponents(playerA.getId())
            );
            assertPlayersListFromCollection(
                    "game.getOpponents(from player B) - must return in APNAP order",
                    needByApnapOpponentsFromB,
                    currentGame.getOpponents(playerB.getId())
            );
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_GameState_GetPlayerList() {
        // game.getState().getPlayerList() - APNAP order, for cards usage

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPlayersListFromCollection(
                    "game.getState().getPlayerList(from current player) - must return in APNAP order",
                    needByApnapAllFromA,
                    currentGame.getState().getPlayerList()
            );
            assertPlayersListFromCollection(
                    "game.getState().getPlayerList(from player B) - must return in APNAP order",
                    needByApnapAllFromB,
                    currentGame.getState().getPlayerList(playerB.getId())
            );
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }

    @Test
    public void test_GameState_GetPlayersInRange() {
        // game.getState().getPlayersInRange(player, game) - APNAP order, for cards usage

        runCode("check", 1, PhaseStep.PRECOMBAT_MAIN, playerA, (info, player, game) -> {
            assertPlayersListFromCollection(
                    "game.getState().getPlayersInRange(from player A) - must return in APNAP order",
                    needByApnapAllFromA,
                    currentGame.getState().getPlayersInRange(playerA.getId(), currentGame)
            );
            assertPlayersListFromCollection(
                    "game.getState().getPlayersInRange(from player B) - must return in APNAP order",
                    needByApnapAllFromB,
                    currentGame.getState().getPlayersInRange(playerB.getId(), currentGame)
            );
        });

        setStrictChooseMode(true);
        setStopAt(1, PhaseStep.END_TURN);
        execute();
    }
}
