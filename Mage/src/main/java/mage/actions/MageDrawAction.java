package mage.actions;

import mage.abilities.Ability;
import mage.actions.impl.MageAction;
import mage.cards.Card;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.DrawCardEvent;
import mage.game.events.DrawCardsEvent;
import mage.game.events.DrewCardEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * Action for drawing cards.
 *
 * @author ayrat
 */
public class MageDrawAction extends MageAction {

    private final Player player;
    private final GameEvent originalDrawEvent; // for replace effects

    private int amount;

    public MageDrawAction(Player player, int amount, GameEvent originalDrawEvent) {
        this.player = player;
        this.amount = amount;
        this.originalDrawEvent = originalDrawEvent;
    }

    /**
     * Draw and set action score.
     *
     * @param source
     * @param game     Game context.
     * @return Number of cards drawn
     */
    @Override
    public int doAction(Ability source, Game game) {
        int numDrawn = 0;
        GameEvent event = new DrawCardsEvent(this.player.getId(), source, this.originalDrawEvent, this.amount);
        // TODO: This needs a better description of how it works. Why "amount < 2"?
        if (amount < 2 || !game.replaceEvent(event)) {
            amount = event.getAmount();
            for (int i = 0; i < amount; i++) {
                boolean value = drawCard(source, this.originalDrawEvent, game);
                if (!value) {
                    continue;
                }
                numDrawn++;
            }
            if (!player.isTopCardRevealed() && numDrawn > 0) {
                game.fireInformEvent(player.getLogName() + " draws " + CardUtil.numberToText(numDrawn, "a") + " card" + (numDrawn > 1 ? "s" : ""));
            }
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
    protected boolean drawCard(Ability source, GameEvent originalDrawEvent, Game game) {
        GameEvent event = new DrawCardEvent(this.player.getId(), source, originalDrawEvent);
        if (!game.replaceEvent(event)) {
            Card card = player.getLibrary().removeFromTop(game);
            if (card != null) {
                card.moveToZone(Zone.HAND, source, game, false); // if you want to use event.getSourceId() here then thinks x10 times
                if (player.isTopCardRevealed()) {
                    game.fireInformEvent(player.getLogName() + " draws a revealed card  (" + card.getLogName() + ')');
                }

                game.fireEvent(new DrewCardEvent(card.getId(), player.getId(), source, originalDrawEvent));
                return true;
            }
        }
        return false;
    }

}
