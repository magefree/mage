
package mage.cards.e;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DestroyAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SubTypeSet;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.SubtypePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.util.SubTypeList;

/**
 *
 * @author TheElk801
 */
public final class EndemicPlague extends CardImpl {

    public EndemicPlague(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}");

        // As an additional cost to cast Endemic Plague, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT)));

        // Destroy all creatures that share a creature type with the sacrificed creature. They can't be regenerated.
        this.getSpellAbility().addEffect(new EndemicPlagueEffect());
    }

    public EndemicPlague(final EndemicPlague card) {
        super(card);
    }

    @Override
    public EndemicPlague copy() {
        return new EndemicPlague(this);
    }
}

class EndemicPlagueEffect extends OneShotEffect {

    public EndemicPlagueEffect() {
        super(Outcome.DestroyPermanent);
        staticText = "destroy all creatures that share a creature type with the sacrificed creature. They can't be regenerated";
    }

    public EndemicPlagueEffect(final EndemicPlagueEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            SubTypeList subs = null;
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost && !((SacrificeTargetCost) cost).getPermanents().isEmpty()) {
                    subs = ((SacrificeTargetCost) cost).getPermanents().get(0).getSubtype(game);
                    break;
                }
            }
            if (subs == null) {
                return false;
            }
            List<SubtypePredicate> preds = new ArrayList<>();
            for (SubType subType : subs) {
                if (subType.getSubTypeSet() == SubTypeSet.CreatureType) {
                    preds.add(new SubtypePredicate(subType));
                }
            }
            FilterCreaturePermanent filter = new FilterCreaturePermanent();
            filter.add(Predicates.or(preds));
            new DestroyAllEffect(filter, true).apply(game, source);
        }
        return true;
    }

    @Override
    public EndemicPlagueEffect copy() {
        return new EndemicPlagueEffect(this);
    }
}
