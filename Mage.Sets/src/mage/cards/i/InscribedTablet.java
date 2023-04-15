package mage.cards.i;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author weirddan455
 */
public final class InscribedTablet extends CardImpl {

    public InscribedTablet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}");

        // {1}, {T}, Sacrifice Inscribed Tablet: Reveal the top five cards of your library.
        // Put a land card from among them into your hand and the rest on the bottom of your library in a random order.
        // If you didn't put a card into your hand this way, draw a card.
        Ability ability = new SimpleActivatedAbility(new InscribedTabletEffect(), new GenericManaCost(1));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeSourceCost());
        this.addAbility(ability);
    }

    private InscribedTablet(final InscribedTablet card) {
        super(card);
    }

    @Override
    public InscribedTablet copy() {
        return new InscribedTablet(this);
    }
}

class InscribedTabletEffect extends OneShotEffect {

    public InscribedTabletEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Reveal the top five cards of your library. " +
                "Put a land card from among them into your hand and the rest on the bottom of your library in a random order. " +
                "If you didn't put a card into your hand this way, draw a card.";
    }

    private InscribedTabletEffect(final InscribedTabletEffect effect) {
        super(effect);
    }

    @Override
    public InscribedTabletEffect copy() {
        return new InscribedTabletEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 5));
        controller.revealCards(source, cards, game);
        boolean landToHand = false;
        if (cards.count(StaticFilters.FILTER_CARD_LAND, controller.getId(), source, game) > 0) {
            TargetCard target = new TargetCard(Zone.LIBRARY, StaticFilters.FILTER_CARD_LAND);
            controller.chooseTarget(outcome, cards, target, source, game);
            Card land = game.getCard(target.getFirstTarget());
            if (land != null) {
                cards.remove(land);
                landToHand = controller.moveCardToHandWithInfo(land, source, game, true);
            }
        }
        controller.putCardsOnBottomOfLibrary(cards, game, source, false);
        if (!landToHand) {
            controller.drawCards(1, source, game);
        }
        return true;
    }
}
