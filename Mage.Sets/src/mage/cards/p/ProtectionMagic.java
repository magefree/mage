package mage.cards.p;

import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ProtectionMagic extends CardImpl {

    public ProtectionMagic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{W}");

        // Put a shield counter on each of up to three target creatures.
        this.getSpellAbility().addEffect(new AddCountersTargetEffect(CounterType.SHIELD.createInstance()));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 3));
    }

    private ProtectionMagic(final ProtectionMagic card) {
        super(card);
    }

    @Override
    public ProtectionMagic copy() {
        return new ProtectionMagic(this);
    }
}
