package mage.actions;

import mage.abilities.Ability;
import mage.actions.impl.MageAction;
import mage.actions.score.ArtificialScoringSystem;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DrawCardEvent;
import mage.game.events.DrawCardsEvent;
import mage.game.events.DrewCardEvent;
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
    private final List<Card> drawnCards;
    private final GameEvent originalDrawEvent; // for replace effects

    private int amount;

    public MageDrawAction(Player player, int amount, GameEvent originalDrawEvent) {
        this.player = player;
        this.amount = amount;
        this.drawnCards = new ArrayList<>();
        this.originalDrawEvent = originalDrawEvent;
    }

    /**
     * Draw and set action score.
     *
     * @param source
     * @param game     Game context.
     * @return
     */
    @Override
    public int doAction(Ability source, Game game) {
        int numDrawn = 0;
        int score = 0;
        GameEvent event = new DrawCardsEvent(this.player.getId(), source, this.originalDrawEvent, this.amount);
        // TODO: This needs a better description of how it works. Why "amount < 2"?
        if (amount < 2 || !game.replaceEvent(event)) {
            amount = event.getAmount();
            for (int i = 0; i < amount; i++) {
                int value = drawCard(source, this.originalDrawEvent, game);
                if (value == NEGATIVE_VALUE) {
                    continue;
                }
                numDrawn++;
                score += value;
            }
            if (!player.isTopCardRevealed() && numDrawn > 0) {
                game.fireInformEvent(player.getLogName() + " draws " + CardUtil.numberToText(numDrawn, "a") + " card" + (numDrawn > 1 ? "s" : ""));
            }
            setScore(player, score);
        }
        return numDrawn;
    }

    /**
     * Draw a card if possible (there is no replacement effect that prevent us
     * from drawing). Fire event about card drawn.
     *
     * @param source
     * @param originalDrawEvent original draw event for replacement effects, can be null for normal calls
     * @param game
     * @return
     */
    protected int drawCard(Ability source, GameEvent originalDrawEvent, Game game) {
        GameEvent event = new DrawCardEvent(this.player.getId(), source, originalDrawEvent);
        if (!game.replaceEvent(event)) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                drawnCards.add(card);
                card.moveToZone(Zone.HAND, source, game, false); // if you want to use event.getSourceId() here then thinks x10 times
                if (player.isTopCardRevealed()) {
                    game.fireInformEvent(player.getLogName() + " draws a revealed card  (" + card.getLogName() + ')');
                }

                game.fireEvent(new DrewCardEvent(card.getId(), player.getId(), source, originalDrawEvent));
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
