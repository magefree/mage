
package mage.cards.m;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.ExchangeLifeControllerTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Styxo
 */
public final class MirrorUniverse extends CardImpl {

    public MirrorUniverse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{6}");

        // {tap}, Sacrifice Mirror Universe: Exchange life totals with target opponent. Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new ExchangeLifeControllerTargetEffect(),
                new TapSourceCost(),
                new IsStepCondition(PhaseStep.UPKEEP)
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addTarget(new TargetOpponent());
        this.addAbility(ability);
    }

    private MirrorUniverse(final MirrorUniverse card) {
        super(card);
    }

    @Override
    public MirrorUniverse copy() {
        return new MirrorUniverse(this);
    }
}
