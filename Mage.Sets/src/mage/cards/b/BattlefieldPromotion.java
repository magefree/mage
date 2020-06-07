package mage.cards.b;

import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BattlefieldPromotion extends CardImpl {

    public BattlefieldPromotion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Put a +1/+1 counter on target creature. That creature gains first strike until end of turn. You gain 2 life.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                FirstStrikeAbility.getInstance(), Duration.EndOfTurn
        ).setText("That creature gains first strike until end of turn"));
        this.getSpellAbility().addEffect(new GainLifeEffect(2));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private BattlefieldPromotion(final BattlefieldPromotion card) {
        super(card);
    }

    @Override
    public BattlefieldPromotion copy() {
        return new BattlefieldPromotion(this);
    }
}
