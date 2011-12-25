package mage.actions;

import mage.Constants;
import mage.actions.impl.MageAction;
import mage.actions.score.ArtificialScoringSystem;
import mage.cards.Card;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

import java.util.List;

/**
 * Action for drawing cards.
 *
 * @author ayrat
 */
public class MageDrawAction extends MageAction {

    private final Player player;
    private final int amount;
    private List<Card> drawnCards;

    private static final int NEGATIVE_VALUE = -1000000;

    public MageDrawAction(Player player, int amount) {
        this.player = player;
        this.amount = amount;
    }

    /**
     * Draw and set action score.
     *
     * @param game Game context.
     */
    @Override
    public int doAction(Game game) {
        int numDrawn = 0;
        int score = 0;
        for (int i = 0; i < amount; i++) {
            int value = drawCard(game);
            if (value == -1) {
                break;
            }
            score += value;
        }
        game.fireInformEvent(player.getName() + " draws " + Integer.toString(numDrawn) + " card" + (numDrawn > 1 ? "s" : ""));
        if (player.isEmptyDraw()) {
            game.doAction(new MageLoseGameAction(player, MageLoseGameAction.DRAW_REASON));
        }

        setScore(player, score);
        game.setStateCheckRequired();

        return numDrawn;
    }

    /**
     * Draw a card if possible (there is no replacement effect that prevent us from drawing).
     * Fire event about card drawn.
     *
     * @param game
     * @return
     */
    protected int drawCard(Game game) {
        if (!game.replaceEvent(GameEvent.getEvent(GameEvent.EventType.DRAW_CARD, player.getId(), player.getId()))) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToZone(Constants.Zone.HAND, null, game, false);
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
