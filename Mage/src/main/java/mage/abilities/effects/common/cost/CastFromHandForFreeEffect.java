package mage.abilities.effects.common.cost;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author fireshoes - Original Code
 * @author JRHerlehy - Implement as seperate class
 * <p>
 * Allows player to choose to cast as card from hand without paying its mana
 * cost.
 * </p>
 */
public class CastFromHandForFreeEffect extends OneShotEffect {

    private final FilterCard filter;

    public CastFromHandForFreeEffect(FilterCard filter) {
        super(Outcome.PlayForFree);
        this.filter = filter;
        this.staticText = "you may cast " + filter.getMessage() + " from your hand without paying its mana cost";
    }

    public CastFromHandForFreeEffect(final CastFromHandForFreeEffect effect) {
        super(effect);
        this.filter = effect.filter;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        return CardUtil.castSpellWithAttributesForFree(controller, source, game, new CardsImpl(controller.getHand()), filter);
    }

    @Override
    public CastFromHandForFreeEffect copy() {
        return new CastFromHandForFreeEffect(this);
    }
}
