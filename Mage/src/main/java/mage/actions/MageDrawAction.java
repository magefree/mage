package mage.actions;

import mage.actions.impl.MageAction;
import mage.actions.score.ArtificialScoringSystem;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Action for drawing cards.
 *
 * @author ayrat
 */
public class MageDrawAction extends MageAction {

    private static final int NEGATIVE_VALUE = -1000000;

    private final Player player;
    private final List<UUID> appliedEffects;
    private final List<Card> drawnCards;

    private int amount;

    public MageDrawAction(Player player, int amount, List<UUID> appliedEffects) {
        this.player = player;
        this.amount = amount;
        this.appliedEffects = appliedEffects;
        this.drawnCards = new ArrayList<>();
    }

    /**
     * Draw and set action score.
     *
     * @param sourceId
     * @param game     Game context.
     * @return
     */
    @Override
    public int doAction(UUID sourceId, Game game) {
        int numDrawn = 0;
        int score = 0;
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.DRAW_CARDS, player.getId(), null, player.getId(), null, amount);
        event.addAppliedEffects(appliedEffects);
        if (amount < 2 || !game.replaceEvent(event)) {
            amount = event.getAmount();
            for (int i = 0; i < amount; i++) {
                int value = drawCard(sourceId, game);
                if (value == NEGATIVE_VALUE) {
                    continue;
                }
                numDrawn++;
                score += value;
            }
            if (!player.isTopCardRevealed() && numDrawn > 0) {
                game.fireInformEvent(player.getLogName() + " draws " + CardUtil.numberToText(numDrawn, "a") + " card" + (numDrawn > 1 ? "s" : ""));
            }
            if (player.isEmptyDraw()) {
                event = GameEvent.getEvent(GameEvent.EventType.EMPTY_DRAW, player.getId(), player.getId());
                if (!game.replaceEvent(event)) {
                    game.doAction(new MageLoseGameAction(player, MageLoseGameAction.DRAW_REASON), sourceId);
                }
            }

            setScore(player, score);
            game.setStateCheckRequired();
        }
        return numDrawn;
    }

    /**
     * Draw a card if possible (there is no replacement effect that prevent us
     * from drawing). Fire event about card drawn.
     *
     * @param sourceId
     * @param game
     * @return
     */
    protected int drawCard(UUID sourceId, Game game) {
        GameEvent event = GameEvent.getEvent(GameEvent.EventType.DRAW_CARD, player.getId(), sourceId, player.getId());
        event.addAppliedEffects(appliedEffects);
        if (!game.replaceEvent(event)) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                drawnCards.add(card);
                card.moveToZone(Zone.HAND, null, game, false);
                if (player.isTopCardRevealed()) {
                    game.fireInformEvent(player.getLogName() + " draws a revealed card  (" + card.getLogName() + ')');
                }

                game.fireEvent(GameEvent.getEvent(GameEvent.EventType.DREW_CARD, card.getId(), player.getId()));
                return ArtificialScoringSystem.inst.getCardScore(card);
            }
        }
        return NEGATIVE_VALUE;
    }

    /**
     * Return a card back to top.
     *
     * @param game Game context
     */
    @Override
    public void undoAction(Game game) {
        for (int index = drawnCards.size() - 1; index >= 0; index--) {
            Card card = drawnCards.get(index);
            player.getHand().remove(card);
            player.getLibrary().putOnTop(card, game);
        }
    }
}
