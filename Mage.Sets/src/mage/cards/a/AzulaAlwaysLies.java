package mage.cards.a;

import mage.abilities.Mode;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AzulaAlwaysLies extends CardImpl {

    public AzulaAlwaysLies(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        this.subtype.add(SubType.LESSON);

        // Choose one or both --
        this.getSpellAbility().getModes().setMinModes(1);
        this.getSpellAbility().getModes().setMaxModes(2);

        // * Target creature gets -1/-1 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(-1, -1));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // * Put a +1/+1 counter on target creature.
        this.getSpellAbility().addMode(new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()))
                .addTarget(new TargetCreaturePermanent()));
    }

    private AzulaAlwaysLies(final AzulaAlwaysLies card) {
        super(card);
    }

    @Override
    public AzulaAlwaysLies copy() {
        return new AzulaAlwaysLies(this);
    }
}
