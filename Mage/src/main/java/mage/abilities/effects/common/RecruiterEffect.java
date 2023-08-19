
package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 * @author LoneFox
 */
public class RecruiterEffect extends OneShotEffect {

    private final FilterCard filter;

    public RecruiterEffect(FilterCard filter) {
        super(Outcome.Benefit);
        this.filter = filter;
    }

    protected RecruiterEffect(final RecruiterEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public RecruiterEffect copy() {
        return new RecruiterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary targetCards = new TargetCardInLibrary(0, Integer.MAX_VALUE, filter);
            Cards cards = new CardsImpl();
            if (controller.searchLibrary(targetCards, source, game)) {
                cards.addAll(targetCards.getTargets());
            }
            controller.revealCards(staticText, cards, game);
            controller.shuffleLibrary(source, game);

            if (!cards.isEmpty()) {
                controller.putCardsOnTopOfLibrary(cards, game, source, true);
            }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "search your library for any number of "
                + filter.getMessage() + ", reveal them, then shuffle and put those cards on top in any order";
    }
}
