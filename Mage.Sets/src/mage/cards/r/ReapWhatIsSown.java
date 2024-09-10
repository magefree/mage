
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ReapWhatIsSown extends CardImpl {

    public ReapWhatIsSown(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{G}{W}");

        // Put a +1/+1 counter on each of up to three target creatures.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Put a +1/+1 counter on each of up to three target creatures");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private ReapWhatIsSown(final ReapWhatIsSown card) {
        super(card);
    }

    @Override
    public ReapWhatIsSown copy() {
        return new ReapWhatIsSown(this);
    }
}
