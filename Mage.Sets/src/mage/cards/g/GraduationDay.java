package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.abilityword.ReparteeAbility;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GraduationDay extends CardImpl {

    public GraduationDay(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");

        // Repartee -- Whenever you cast an instant or sorcery spell that targets a creature, put a +1/+1 counter on target creature you control.
        Ability ability = new ReparteeAbility(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        ability.addTarget(new TargetControlledCreaturePermanent());
        this.addAbility(ability);
    }

    private GraduationDay(final GraduationDay card) {
        super(card);
    }

    @Override
    public GraduationDay copy() {
        return new GraduationDay(this);
    }
}
