package mage.cards.e;

import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
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
public final class EssenceInfusion extends CardImpl {

    public EssenceInfusion(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}");

        // Put two +1/+1 counters on target creature. It gains lifelink until end of turn.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(2)));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                LifelinkAbility.getInstance(), Duration.EndOfTurn
        ).setText("It gains lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private EssenceInfusion(final EssenceInfusion card) {
        super(card);
    }

    @Override
    public EssenceInfusion copy() {
        return new EssenceInfusion(this);
    }
}
