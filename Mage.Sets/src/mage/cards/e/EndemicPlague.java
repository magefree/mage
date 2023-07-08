package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SharesCreatureTypePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

import mage.filter.StaticFilters;

/**
 * @author TheElk801
 */
public final class EndemicPlague extends CardImpl {

    public EndemicPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // As an additional cost to cast Endemic Plague, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));

        // Destroy all creatures that share a creature type with the sacrificed creature. They can't be regenerated.
        this.getSpellAbility().addEffect(new EndemicPlagueEffect());
    }

    private EndemicPlague(final EndemicPlague card) {
        super(card);
    }

    @Override
    public EndemicPlague copy() {
        return new EndemicPlague(this);
    }
}

class EndemicPlagueEffect extends OneShotEffect {

    EndemicPlagueEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all creatures that share a creature type with the sacrificed creature. They can't be regenerated";
    }

    private EndemicPlagueEffect(final EndemicPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Permanent permanent = null;
        for (Cost cost : source.getCosts()) {
            if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                permanent = ((SacrificeTargetCost) cost).getPermanents().get(0);
                break;
            }
        }
        if (permanent == null) {
            return false;
        }
        FilterCreaturePermanent filter = new FilterCreaturePermanent();
        filter.add(new SharesCreatureTypePredicate(permanent));
        return new DestroyAllEffect(filter, true).apply(game, source);
    }

    @Override
    public EndemicPlagueEffect copy() {
        return new EndemicPlagueEffect(this);
    }
}
