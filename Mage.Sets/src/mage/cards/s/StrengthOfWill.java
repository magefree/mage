package mage.cards.s;

import mage.abilities.common.DealtDamageToSourceTriggeredAbility;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class StrengthOfWill extends CardImpl {

    public StrengthOfWill(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}");
        

        // Until end of turn, target creature you control gains indestructible and "Whenever this creature is dealt damage, put that many +1/+1 counters on it."
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(IndestructibleAbility.getInstance())
                .setText("Until end of turn, target creature you control gains indestructible"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                new DealtDamageToSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(),SavedDamageValue.MANY), false))
                .setText("\"Whenever this creature is dealt damage, put that many +1/+1 counters on it.\"")
                .concatBy("and")
        );
        this.getSpellAbility().addTarget(new TargetControlledCreaturePermanent());
    }

    private StrengthOfWill(final StrengthOfWill card) {
        super(card);
    }

    @Override
    public StrengthOfWill copy() {
        return new StrengthOfWill(this);
    }
}
