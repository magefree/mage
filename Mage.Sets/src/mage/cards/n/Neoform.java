package mage.cards.n;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.counters.CounterType;
import mage.counters.Counters;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Neoform extends CardImpl {

    public Neoform(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}{U}");

        // As an additional cost to cast this spell, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_CREATURE));

        // Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost,
        // put that card onto the battlefield with an additional +1/+1 counter on it, then shuffle your library.
        this.getSpellAbility().addEffect(new NeoformEffect());
    }

    private Neoform(final Neoform card) {
        super(card);
    }

    @Override
    public Neoform copy() {
        return new Neoform(this);
    }
}

class NeoformEffect extends OneShotEffect {

    NeoformEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card with mana value equal to " +
                "1 plus the sacrificed creature's mana value, " +
                "put that card onto the battlefield with an additional +1/+1 counter on it, then shuffle.";
    }

    private NeoformEffect(final NeoformEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent sacrificedPermanent = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost) {
                SacrificeTargetCost sacrificeCost = (SacrificeTargetCost) cost;
                if (!sacrificeCost.getPermanents().isEmpty()) {
                    sacrificedPermanent = sacrificeCost.getPermanents().get(0);
                }
                break;
            }
        }
        Player controller = game.getPlayer(source.getControllerId());
        if (sacrificedPermanent == null || controller == null) {
            return false;
        }
        int newConvertedCost = sacrificedPermanent.getManaValue() + 1;
        FilterCard filter = new FilterCard("creature card with mana value " + newConvertedCost);
        filter.add(new ManaValuePredicate(ComparisonType.EQUAL_TO, newConvertedCost));
        filter.add(CardType.CREATURE.getPredicate());
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            if (card != null) {
                game.setEnterWithCounters(card.getId(), new Counters(CounterType.P1P1.createInstance()));
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public NeoformEffect copy() {
        return new NeoformEffect(this);
    }
}
