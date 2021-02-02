package mage.cards.n;

import mage.abilities.abilityword.StriveAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class NaturesPanoply extends CardImpl {

    public NaturesPanoply(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G}");

        // Strive - Nature's Panoply costs {2}{G} more to cast for each target beyond the first.
        this.addAbility(new StriveAbility("{2}{G}"));

        // Choose any number of target creatures. Put a +1/+1 counter on each of them.
        Effect effect = new AddCountersTargetEffect(CounterType.P1P1.createInstance());
        effect.setText("Choose any number of target creatures. Put a +1/+1 counter on each of them.");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, Integer.MAX_VALUE));
    }

    private NaturesPanoply(final NaturesPanoply card) {
        super(card);
    }

    @Override
    public NaturesPanoply copy() {
        return new NaturesPanoply(this);
    }
}
