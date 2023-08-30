
package mage.cards.l;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class LifeChisel extends CardImpl {

    public LifeChisel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // Sacrifice a creature: You gain life equal to the sacrificed creature's toughness. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new LifeChiselEffect(),
                new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT),
                new IsStepCondition(PhaseStep.UPKEEP),
                null
        );
        this.addAbility(ability);
    }

    private LifeChisel(final LifeChisel card) {
        super(card);
    }

    @Override
    public LifeChisel copy() {
        return new LifeChisel(this);
    }
}

class LifeChiselEffect extends OneShotEffect {

    public LifeChiselEffect() {
        super(Outcome.GainLife);
        this.staticText = "You gain life equal to the sacrificed creature's toughness";
    }

    public LifeChiselEffect(final LifeChiselEffect effect) {
        super(effect);
    }

    @Override
    public LifeChiselEffect copy() {
        return new LifeChiselEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Cost cost : source.getCosts()) {
                if (cost instanceof SacrificeTargetCost) {
                    int amount = ((SacrificeTargetCost) cost).getPermanents().get(0).getToughness().getValue();
                    if (amount > 0) {
                        controller.gainLife(amount, game, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
