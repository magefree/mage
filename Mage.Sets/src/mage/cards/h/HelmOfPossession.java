
package mage.cards.h;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SkipUntapOptionalAbility;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetControlledPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HelmOfPossession extends CardImpl {

    public HelmOfPossession(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // You may choose not to untap Helm of Possession during your untap step.
        this.addAbility(new SkipUntapOptionalAbility());

        // {2}, {tap}, Sacrifice a creature: Gain control of target creature for as long as you control Helm of Possession and Helm of Possession remains tapped.
        Ability ability = new SimpleActivatedAbility(new ConditionalContinuousEffect(
                new GainControlTargetEffect(Duration.WhileControlled), SourceTappedCondition.TAPPED,
                "gain control of target creature for as long as you control {this} and {this} remains tapped"
        ), new GenericManaCost(2));
        ability.addCost(new TapSourceCost());
        ability.addCost(new SacrificeTargetCost(StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private HelmOfPossession(final HelmOfPossession card) {
        super(card);
    }

    @Override
    public HelmOfPossession copy() {
        return new HelmOfPossession(this);
    }
}
