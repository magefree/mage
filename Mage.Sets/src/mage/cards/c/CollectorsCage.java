package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.condition.common.CovenCondition;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.decorator.ConditionalOneShotEffect;
import mage.abilities.effects.common.HideawayPlayEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.hint.common.CovenHint;
import mage.abilities.keyword.HideawayAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CollectorsCage extends CardImpl {

    public CollectorsCage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");

        // Hideaway 5
        this.addAbility(new HideawayAbility(5));

        // {1}, {T}: Put a +1/+1 counter on target creature you control. Then if you control three or more creatures with different powers, you may play the exiled card without paying its mana cost.
        Ability ability = new SimpleActivatedAbility(
                new AddCountersTargetEffect(CounterType.P1P1.createInstance()), new GenericManaCost(1)
        );
        ability.addCost(new TapSourceCost());
        ability.addEffect(new ConditionalOneShotEffect(
                new HideawayPlayEffect(), CovenCondition.instance, "then if you control three or more " +
                "creatures with different powers, you may play the exiled card without paying its mana cost"
        ));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability.addHint(CovenHint.instance));
    }

    private CollectorsCage(final CollectorsCage card) {
        super(card);
    }

    @Override
    public CollectorsCage copy() {
        return new CollectorsCage(this);
    }
}
