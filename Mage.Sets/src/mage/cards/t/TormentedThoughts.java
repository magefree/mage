
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TormentedThoughts extends CardImpl {

    public TormentedThoughts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}");


        // As an additional cost to cast Tormented Thoughts, sacrifice a creature.
        this.getSpellAbility().addCost(new SacrificeTargetCost(new TargetControlledCreaturePermanent(1,1,StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT, false)));

        // Target player discards a number of cards equal to the sacrificed creature's power.
        this.getSpellAbility().addEffect(new TormentedThoughtsDiscardEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    private TormentedThoughts(final TormentedThoughts card) {
        super(card);
    }

    @Override
    public TormentedThoughts copy() {
        return new TormentedThoughts(this);
    }
}

class TormentedThoughtsDiscardEffect extends OneShotEffect {

    public TormentedThoughtsDiscardEffect() {
        super(Outcome.Discard);
        this.staticText = "Target player discards a number of cards equal to the sacrificed creature's power";
    }

    private TormentedThoughtsDiscardEffect(final TormentedThoughtsDiscardEffect effect) {
        super(effect);
    }

    @Override
    public TormentedThoughtsDiscardEffect copy() {
        return new TormentedThoughtsDiscardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
        if (controller != null && targetPlayer != null) {
            int power = 0;
            COSTS: for (Cost cost :source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    SacrificeTargetCost sacCost = (SacrificeTargetCost) cost;
                    for(Permanent permanent : sacCost.getPermanents()) {
                        power = permanent.getPower().getValue();
                        break COSTS;
                    }
                }
            }
            if (power > 0) {
                targetPlayer.discard(power, false, false, source, game);
            }
            return true;
        }
        return false;
    }
}
