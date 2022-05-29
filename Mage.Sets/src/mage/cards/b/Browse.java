
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author Quercitron
 */
public final class Browse extends CardImpl {

    public Browse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}{U}");

        // {2}{U}{U}: Look at the top five cards of your library, put one of them into your hand, and exile the rest.
        SimpleActivatedAbility ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BrowseEffect(), new ManaCostsImpl<>("{2}{U}{U}"));
        this.addAbility(ability);
    }

    private Browse(final Browse card) {
        super(card);
    }

    @Override
    public Browse copy() {
        return new Browse(this);
    }
}

class BrowseEffect extends OneShotEffect {

    public BrowseEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of your library, put one of them into your hand, and exile the rest";
    }

    public BrowseEffect(final BrowseEffect effect) {
        super(effect);
    }

    @Override
    public BrowseEffect copy() {
        return new BrowseEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
            if (!cards.isEmpty()) {
                controller.lookAtCards(source, null, cards, game);
                TargetCard target = new TargetCard(Zone.LIBRARY, new FilterCard("card to put in your hand"));
                if (controller.choose(Outcome.Benefit, cards, target, game)) {
                    Card card = cards.get(target.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        cards.remove(card);
                    }
                }
                controller.moveCards(cards, Zone.EXILED, source, game);
            }
            return true;
        }
        return false;
    }
}
