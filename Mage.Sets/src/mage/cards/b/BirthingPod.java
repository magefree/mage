
package mage.cards.b;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class BirthingPod extends CardImpl {

    public BirthingPod(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{3}{G/P}");

        // {1}{G/P}, {tap}, Sacrifice a creature: Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost,
        // put that card onto the battlefield, then shuffle your library. Activate this ability only any time you could cast a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(Zone.BATTLEFIELD, new BirthingPodEffect(), new ManaCostsImpl("{1}{G/P}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));
        this.addAbility(ability);
    }

    public BirthingPod(final BirthingPod card) {
        super(card);
    }

    @Override
    public BirthingPod copy() {
        return new BirthingPod(this);
    }
}

class BirthingPodEffect extends OneShotEffect {

    BirthingPodEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card with converted mana cost equal to 1 plus the sacrificed creature's converted mana cost, put that card "
                + "onto the battlefield, then shuffle your library";
    }

    BirthingPodEffect(final BirthingPodEffect effect) {
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
        if (sacrificedPermanent != null && controller != null) {
            int newConvertedCost = sacrificedPermanent.getConvertedManaCost() + 1;
            FilterCard filter = new FilterCard("creature card with converted mana cost " + newConvertedCost);
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, newConvertedCost));
            filter.add(new CardTypePredicate(CardType.CREATURE));
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public BirthingPodEffect copy() {
        return new BirthingPodEffect(this);
    }
}
