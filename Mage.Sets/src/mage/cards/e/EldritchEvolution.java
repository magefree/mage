
package mage.cards.e;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileSpellEffect;
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
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public final class EldritchEvolution extends CardImpl {

    public EldritchEvolution(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{G}");

        // As an additional cost to cast Eldritch Evolution, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1, 1, StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, true)));

        // Search your library for a creature card with converted mana cost X or less, where X is 2 plus the sacrificed creature's converted mana cost.
        // Put that card onto the battlefield, then shuffle your library. Exile Eldritch Evolution.
        this.getSpellAbility().addEffect(new EldritchEvolutionEffect());
        this.getSpellAbility().addEffect(new ExileSpellEffect());
    }

    private EldritchEvolution(final EldritchEvolution card) {
        super(card);
    }

    @Override
    public EldritchEvolution copy() {
        return new EldritchEvolution(this);
    }
}

class EldritchEvolutionEffect extends OneShotEffect {

    EldritchEvolutionEffect() {
        super(Outcome.Benefit);
        staticText = "Search your library for a creature card with mana value X or less, where X is 2 plus the sacrificed creature's mana value. Put that card "
                + "onto the battlefield, then shuffle";
    }

    private EldritchEvolutionEffect(final EldritchEvolutionEffect effect) {
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
            int newConvertedCost = sacrificedPermanent.getManaValue() + 2;
            FilterCard filter = new FilterCard("creature card with mana value " + newConvertedCost + " or less");
            filter.add(new ManaValuePredicate(ComparisonType.FEWER_THAN, newConvertedCost+1));
            filter.add(CardType.CREATURE.getPredicate());
            TargetCardInLibrary target = new TargetCardInLibrary(filter);
            if (controller.searchLibrary(target, source, game)) {
                Card card = controller.getLibrary().getCard(target.getFirstTarget(), game);
                controller.moveCards(card, Zone.BATTLEFIELD, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        return false;
    }

    @Override
    public EldritchEvolutionEffect copy() {
        return new EldritchEvolutionEffect(this);
    }
}
