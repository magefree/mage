
package mage.cards.d;

import java.util.UUID;
import mage.abilities.Ability;
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
 * @author fireshoes
 */
public final class DeployTheGatewatch extends CardImpl {

    public DeployTheGatewatch(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{W}{W}");

        // Look at the top seven cards of your library. Put up to two planeswalker cards from among them onto the battlefield.
        // Put the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new DeployTheGatewatchEffect());
    }

    private DeployTheGatewatch(final DeployTheGatewatch card) {
        super(card);
    }

    @Override
    public DeployTheGatewatch copy() {
        return new DeployTheGatewatch(this);
    }
}

class DeployTheGatewatchEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCard("up to two planeswalker cards");

    static {
        filter.add(CardType.PLANESWALKER.getPredicate());
    }

    public DeployTheGatewatchEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top seven cards of your library. Put up to two planeswalker cards from among them onto the battlefield. "
                + "Put the rest on the bottom of your library in a random order";
    }

    public DeployTheGatewatchEffect(final DeployTheGatewatchEffect effect) {
        super(effect);
    }

    @Override
    public DeployTheGatewatchEffect copy() {
        return new DeployTheGatewatchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        // Look at the top seven cards of your library.
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 7));
        controller.lookAtCards(source, null, cards, game);
        // Put up to two planeswalker cards from among them onto the battlefield.
        if (cards.count(filter, game) > 0) {
            TargetCard target = new TargetCard(0, 2, Zone.LIBRARY, filter);
            if (controller.choose(Outcome.DrawCard, cards, target, game)) {
                Cards pickedCards = new CardsImpl(target.getTargets());
                cards.removeAll(pickedCards);
                controller.moveCards(pickedCards.getCards(game), Zone.BATTLEFIELD, source, game);
            }
        }
        // Put the rest on the bottom of your library in a random order
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        return true;
    }
}
