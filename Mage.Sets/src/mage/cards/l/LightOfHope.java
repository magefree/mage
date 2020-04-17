package mage.cards.l;

import mage.abilities.Mode;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetEnchantmentPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LightOfHope extends CardImpl {

    public LightOfHope(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one —
        // • You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));

        // • Destroy target enchantment.
        Mode mode = new Mode(new DestroyTargetEffect());
        mode.addTarget(new TargetEnchantmentPermanent());
        this.getSpellAbility().addMode(mode);

        // • Put a +1/+1 counter on target creature.
        mode = new Mode(new AddCountersTargetEffect(CounterType.P1P1.createInstance()));
        mode.addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addMode(mode);
    }

    private LightOfHope(final LightOfHope card) {
        super(card);
    }

    @Override
    public LightOfHope copy() {
        return new LightOfHope(this);
    }
}
