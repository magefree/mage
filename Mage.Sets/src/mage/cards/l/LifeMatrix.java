package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.IsStepCondition;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.common.ActivateIfConditionActivatedAbility;
import mage.abilities.effects.common.RegenerateSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
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
        Ability ability = new ActivateIfConditionActivatedAbility(
                new AddCountersTargetEffect(CounterType.MATRIX.createInstance()),
                new GenericManaCost(4), IsStepCondition.getMyUpkeep()
        );
        ability.addEffect(new GainAbilityTargetEffect(
                new SimpleActivatedAbility(
                        new RegenerateSourceEffect(),
                        new RemoveCountersSourceCost(CounterType.MATRIX.createInstance())
                ), Duration.Custom
        ).setText("and that creature gains \"Remove a matrix counter from this creature: Regenerate this creature.\""));
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
