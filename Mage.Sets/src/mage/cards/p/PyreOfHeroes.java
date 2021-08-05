package mage.cards.p;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author weirddan455
 */
public final class PyreOfHeroes extends CardImpl {

    public PyreOfHeroes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {2}, {T}, Sacrifice a creature: Search your library for a creature card that shares a creature type with the sacrificed creature and has converted mana cost equal to 1 plus that creature's converted mana cost.
        // Put that card onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                Zone.BATTLEFIELD, new PyreOfHeroesEffect(), new GenericManaCost(2)
        );
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(
                StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT
        )));
        this.addAbility(ability);
    }

    private PyreOfHeroes(final PyreOfHeroes card) {
        super(card);
    }

    @Override
    public PyreOfHeroes copy() {
        return new PyreOfHeroes(this);
    }
}

class PyreOfHeroesEffect extends OneShotEffect {

    PyreOfHeroesEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card that shares a creature type with the sacrificed creature and has mana value equal to 1 " +
                "plus that creature's mana value. Put that card " +
                "onto the battlefield, then shuffle";
    }

    private PyreOfHeroesEffect(final PyreOfHeroesEffect effect) {
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
        filter.add(new SharesCreatureTypePredicate(sacrificedPermanent));
        TargetCardInLibrary target = new TargetCardInLibrary(filter);
        if (controller.searchLibrary(target, source, game)) {
            Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
            controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }

    @Override
    public PyreOfHeroesEffect copy() {
        return new PyreOfHeroesEffect(this);
    }
}
