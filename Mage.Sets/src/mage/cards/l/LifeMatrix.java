package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PhaseStep;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author L_J
 */
public final class LifeMatrix extends CardImpl {

    public LifeMatrix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // {4}, {T}: Put a matrix counter on target creature and that creature gains 
        // “Remove a matrix counter from this creature: Regenerate this creature.” 
        // Activate this ability only during your upkeep.
        Ability ability = new ConditionalActivatedAbility(
                Zone.BATTLEFIELD,
                new AddCountersTargetEffect(CounterType.MATRIX.createInstance()),
                new GenericManaCost(4),
                new IsStepCondition(PhaseStep.UPKEEP), "{4}, {T}: Put a matrix counter on target creature and "
                + "that creature gains \"Remove a matrix counter from this creature: "
                + "Regenerate this creature.\" Activate only during your upkeep.");
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD,
                new RegenerateSourceEffect(),
                new RemoveCountersSourceCost(CounterType.MATRIX.createInstance()));
        ability.addEffect(new GainAbilityTargetEffect(ability2, Duration.Custom));
        ability.addCost(new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private LifeMatrix(final LifeMatrix card) {
        super(card);
    }

    @Override
    public LifeMatrix copy() {
        return new LifeMatrix(this);
    }
}
