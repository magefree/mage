package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfPreCombatMainTriggeredAbility;
import mage.abilities.costs.Costs;
import mage.abilities.costs.CostsImpl;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInGraveyard;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * @author Susucr
 */
public final class RipplesOfUndeath extends CardImpl {

    public RipplesOfUndeath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}");

        // At the beginning of your precombat main phase, mill three cards. Then you may pay {1} and 3 life. If you do, put one of the cards milled this way into your hand.
        this.addAbility(new BeginningOfPreCombatMainTriggeredAbility(
                new RipplesOfUndeathEffect(), TargetController.YOU, false
        ));
    }

    private RipplesOfUndeath(final RipplesOfUndeath card) {
        super(card);
    }

    @Override
    public RipplesOfUndeath copy() {
        return new RipplesOfUndeath(this);
    }
}

class RipplesOfUndeathEffect extends OneShotEffect {

    RipplesOfUndeathEffect() {
        super(Outcome.Benefit);
        staticText = "mill three cards. Then you may pay {1} and 3 life. If you do, put one of the cards milled this way into your hand.";
    }

    private RipplesOfUndeathEffect(final RipplesOfUndeathEffect effect) {
        super(effect);
    }

    @Override
    public RipplesOfUndeathEffect copy() {
        return new RipplesOfUndeathEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Cards milled = controller.millCards(3, source, game);
        Costs cost = new CostsImpl();
        cost.add(new GenericManaCost(1));
        cost.add(new PayLifeCost(3));

        new DoIfCostPaid(new RipplesOfUndeathReturnEffect(milled), cost)
                .apply(game, source);
        return true;
    }
}

class RipplesOfUndeathReturnEffect extends OneShotEffect {

    private final Set<UUID> milledCards;

    RipplesOfUndeathReturnEffect(Set<UUID> milledCards) {
        super(Outcome.DrawCard);
        staticText = "put a card from among those cards into your hand";
        this.milledCards = milledCards;
    }

    private RipplesOfUndeathReturnEffect(final RipplesOfUndeathReturnEffect effect) {
        super(effect);
        this.milledCards = new HashSet<>(effect.milledCards);
    }

    @Override
    public RipplesOfUndeathReturnEffect copy() {
        return new RipplesOfUndeathReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (milledCards.isEmpty() || controller == null) {
            return false;
        }
        Cards cards = new CardsImpl();
        cards.addAll(milledCards);
        TargetCardInGraveyard target = new TargetCardInGraveyard();
        target.withNotTarget(true);
        controller.choose(outcome, cards, target, source, game);
        Set<Card> returned = target
                .getTargets()
                .stream()
                .map(game::getCard)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());
        if (returned.isEmpty()) {
            return true;
        }
        controller.moveCards(returned, Zone.HAND, source, game);
        return true;
    }
}