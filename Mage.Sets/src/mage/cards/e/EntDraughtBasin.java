package mage.cards.e;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetadjustment.PowerTargetAdjuster;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EntDraughtBasin extends CardImpl {

    public EntDraughtBasin(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // {X}, {T}: Put a +1/+1 counter on target creature with power X. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance())
                        .setText("put a +1/+1 counter on target creature with power X"),
                new ManaCostsImpl<>("{X}")
        );
        ability.addCost(new TapSourceCost());
        ability.setTargetAdjuster(new PowerTargetAdjuster(ComparisonType.EQUAL_TO));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private EntDraughtBasin(final EntDraughtBasin card) {
        super(card);
    }

    @Override
    public EntDraughtBasin copy() {
        return new EntDraughtBasin(this);
    }
}
