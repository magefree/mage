package mage.network.protocol.change.compator.impl;

import mage.network.protocol.change.ChangePart;
import mage.network.protocol.change.ChangeType;
import mage.network.protocol.change.compator.Comparator;
import mage.network.protocol.change.compator.CompareResult;
import mage.view.ExileView;
import mage.view.GameView;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * {@link Comparator} for {@link GameView}
 *
 * Compares various parts independently and returns only parts that differ.
 *
 * @author magenoxx
 */
public class GameViewComparator implements Comparator<GameView> {

    private static final Logger logger = Logger.getLogger(GameViewComparator.class);

    @Override
    public CompareResult compare(final GameView newGameView, final GameView prevGameView) {
        final CompareResult result = new CompareResult();

        if (newGameView == null) {
            throw new IllegalArgumentException("New GameView can't be null");
        }
        if (prevGameView == null) {
            throw new IllegalArgumentException("Prev GameView can't be null");
        }

        // Priority Player Name
        if (!Objects.equals(newGameView.getPriorityPlayerName(), prevGameView.getPriorityPlayerName())) {
            logger.debug("Priority Player Name has changed");
            result.put(ChangeType.PRIORITY_PLAYER_NAME ,new ChangePart(ChangeType.PRIORITY_PLAYER_NAME, newGameView
                .getPriorityPlayerName()));
        }

        // Active player
        if (!Objects.equals(newGameView.getActivePlayerId(), prevGameView.getActivePlayerId())) {
            logger.debug("Active player has changed");
            result.put(ChangeType.ACTIVE_PLAYER_ID ,new ChangePart(ChangeType.ACTIVE_PLAYER_ID, newGameView.getActivePlayerId()));
            result.put(ChangeType.ACTIVE_PLAYER_NAME ,new ChangePart(ChangeType.ACTIVE_PLAYER_NAME, newGameView.getActivePlayerName()));
        }

        if (!Objects.equals(newGameView.getExile(), prevGameView.getExile())) {
            logger.debug("Exile has changed");
            result.put(ChangeType.EXILE ,new ChangePart(ChangeType.EXILE, newGameView.getExile()));
        }

        // Hand
        if (!Objects.equals(newGameView.getHand(), prevGameView.getHand())) {
            logger.debug("Hand has changed");
            result.put(ChangeType.HAND, new ChangePart(ChangeType.HAND, newGameView.getHand()));
        }

        // Can play in hand
        if (result.containsKey(ChangeType.HAND) || !Objects.equals(newGameView.getCanPlayInHand(), prevGameView.getCanPlayInHand())) {
            // TODO: improve - separate from HAND
            logger.debug("Can play in hand has changed");
            result.put(ChangeType.CAN_PLAY_IN_HAND ,new ChangePart(ChangeType.CAN_PLAY_IN_HAND, newGameView.getCanPlayInHand()));
        }


        // isSpecial
        if (newGameView.getSpecial() != prevGameView.getSpecial()) {
            logger.debug("Special has changed");
            result.put(ChangeType.SPECIAL ,new ChangePart(ChangeType.SPECIAL, newGameView.getSpecial()));
        }

        // LookedAt
        if (!Objects.equals(newGameView.getLookedAt(), prevGameView.getLookedAt())) {
            logger.debug("LookedAt has changed");
            result.put(ChangeType.LOOKED_AT ,new ChangePart(ChangeType.LOOKED_AT, newGameView.getLookedAt()));
        }

        // Phase
        if (!Objects.equals(newGameView.getPhase(), prevGameView.getPhase())) {
            logger.debug("Phase has changed");
            result.put(ChangeType.PHASE ,new ChangePart(ChangeType.PHASE, newGameView.getPhase()));
        }
        // Step
        if (!Objects.equals(newGameView.getStep(), prevGameView.getStep())) {
            logger.debug("Step has changed");
            result.put(ChangeType.PHASE ,new ChangePart(ChangeType.STEP, newGameView.getStep()));
        }

        // Revealed
        if (!Objects.equals(newGameView.getRevealed(), prevGameView.getRevealed())) {
            logger.debug("Revealed has changed");
            result.put(ChangeType.REVEALED ,new ChangePart(ChangeType.REVEALED, newGameView.getRevealed()));
        }

        if (!Objects.equals(newGameView.getOpponentHands(), prevGameView.getOpponentHands())) {
            logger.debug("Opponent Hands has changed");
            result.put(ChangeType.OPPONENT_HANDS ,new ChangePart(ChangeType.OPPONENT_HANDS, newGameView.getOpponentHands()));
        }

        if (newGameView.getSpellsCastCurrentTurn() != prevGameView.getSpellsCastCurrentTurn()) {
            logger.debug("Spells Cast Current Turn has changed");
            result.put(ChangeType.SPELLS_CAST_THIS_TURN ,new ChangePart(ChangeType.SPELLS_CAST_THIS_TURN, newGameView.getSpellsCastCurrentTurn()));
        }

        if (newGameView.getTurn() != prevGameView.getTurn()) {
            logger.debug("Turn has changed");
            result.put(ChangeType.TURN ,new ChangePart(ChangeType.TURN, newGameView.getTurn()));
        }

        if (!Objects.equals(newGameView.getWatchedHands(), prevGameView.getWatchedHands())) {
            logger.debug("Wathed hands have changed");
            result.put(ChangeType.WATCHED_HANDS ,new ChangePart(ChangeType.WATCHED_HANDS, newGameView.getWatchedHands()));
        }

        // Combat
        if (!Objects.equals(newGameView.getCombat(), prevGameView.getCombat())) {
            logger.debug("Combat has changed");
            result.put(ChangeType.COMBAT ,new ChangePart(ChangeType.COMBAT, newGameView.getCombat()));
        }

        // Stack
        if (!Objects.equals(newGameView.getStack(), prevGameView.getStack())) {
            logger.debug("Stack has changed");
            result.put(ChangeType.STACK ,new ChangePart(ChangeType.STACK, newGameView.getStack()));
        }

        // Players with Battlefield/Graveyard/Exile/etc.
        // TODO: place to improve (and split)
        if (!Objects.equals(newGameView.getPlayers(), prevGameView.getPlayers())) {
            logger.debug("Players has changed");
            result.put(ChangeType.PLAYERS ,new ChangePart(ChangeType.PLAYERS, newGameView.getPlayers()));
        }

        if (result.isEmpty()) {
            logger.debug("GameViews are equal");
            result.setEqual(true);
        } else {
            // priority time is changed every second so we don't want to send it on every update
            // instead of doing so we spend priorityTime update only if previous result is not empty
            //TODO: probably we should send it also if we haven't updated too long (e.g. 10 seconds)
            result.put(ChangeType.PRIORITY_TIME ,new ChangePart(ChangeType.PRIORITY_TIME, newGameView.getPriorityTime()));
        }

        return result;
    }

    private boolean equalsWithNull(final Object objectA, final Object objectB) {
        if (objectA == null || objectB == null) {
            return objectA == null && objectB == null;
        }

        return objectA.equals(objectB);
    }

    private void compare(final ChangeType type, final List<ExileView> exile1, final
    List<ExileView> exile2, final CompareResult result) {

        boolean changed = false;

        try {
            if (exile1.size() != exile2.size()) {
                changed = true;
                return;
            }

            for (final ExileView exileView : exile1) {
               if (!exileView.equals(exile2)) {
                   changed = true;
                   return;
               }
            }

        } finally {
            if (changed) {
                logger.debug("Exile has changed");
                result.put(type, new ChangePart(type, exile2));
            }
        }

    }
}
