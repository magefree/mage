
package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;
import mage.util.CardUtil;

/**
 *
 * @author LevelX2
 */

public class FatesealEffect extends OneShotEffect {

    protected static final FilterCard filter1 = new FilterCard("card to put on the bottom of opponent's library");

    protected int fatesealNumber;

    public FatesealEffect(int fatesealNumber) {
        super(Outcome.Benefit);
        this.fatesealNumber = fatesealNumber;
        this.setText();
    }

    public FatesealEffect(final FatesealEffect effect) {
        super(effect);
        this.fatesealNumber = effect.fatesealNumber;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Target target = new TargetOpponent(true);
            if (controller.choose(outcome, target, source, game)) {
                Player opponent = game.getPlayer(target.getFirstTarget());
                if (opponent == null) {
                    return false;
                }
                boolean revealed = opponent.isTopCardRevealed(); // by looking at the cards with fateseal you have not to reveal the next card
                opponent.setTopCardRevealed(false);
                Cards cards = new CardsImpl();
                int count = Math.min(fatesealNumber, opponent.getLibrary().size());
                if (count == 0) {
                    return true;
                }
                for (int i = 0; i < count; i++) {
                    Card card = opponent.getLibrary().removeFromTop(game);
                    cards.add(card);
                }
                TargetCard target1 = new TargetCard(Zone.LIBRARY, filter1);
                target1.setRequired(false);
                // move cards to the bottom of the library
                while (!cards.isEmpty() && controller.choose(Outcome.Detriment, cards, target1, game)) {
                    if (!controller.canRespond() || !opponent.canRespond()) {
                        return false;
                    }
                    Card card = cards.get(target1.getFirstTarget(), game);
                    if (card != null) {
                        cards.remove(card);
                        controller.moveCardToLibraryWithInfo(card, source, game, Zone.LIBRARY, false, false);
                    }
                    target1.clearChosen();
                }
                // move cards to the top of the library
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
                game.fireEvent(new GameEvent(GameEvent.EventType.FATESEALED, opponent.getId(), source, source.getControllerId()));
                controller.setTopCardRevealed(revealed);
                return true;
            }

        }
        return false;
    }

    @Override
    public FatesealEffect copy() {
        return new FatesealEffect(this);
    }

    private void setText() {
        StringBuilder sb = new StringBuilder("fateseal ").append(fatesealNumber);
        if (fatesealNumber == 1) {
            sb.append(". <i>(To fateseal 1, its controller looks at the top card of an opponent's library, then they may put that card on the bottom of that library.)</i>");
        } else {
            sb.append(". <i>(To fateseal ");
            sb.append(CardUtil.numberToText(fatesealNumber));
            sb.append(", look at the top two cards of an opponent's library, then put any number of them on the bottom of that player's library and the rest on top in any order.)</i>");
        }
        staticText = sb.toString();
    }
}
