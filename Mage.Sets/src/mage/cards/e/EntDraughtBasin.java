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
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerPredicate;
import mage.game.Game;
import mage.target.TargetPermanent;
import mage.target.targetadjustment.TargetAdjuster;

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
        ability.setTargetAdjuster(EntDraughtBasinAdjuster.instance);
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

enum EntDraughtBasinAdjuster implements TargetAdjuster {
    instance;

    @Override
    public void adjustTargets(Ability ability, Game game) {
        int xValue = ability.getManaCostsToPay().getX();
        FilterPermanent filter = new FilterCreaturePermanent("creature with power " + xValue);
        filter.add(new PowerPredicate(ComparisonType.EQUAL_TO, xValue));
        ability.getTargets().clear();
        ability.addTarget(new TargetPermanent(filter));
    }
}
