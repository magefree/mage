package mage.cards.s;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Mode;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.counters.CounterType;
import mage.target.TargetPermanent;
import mage.target.common.TargetAttackingCreature;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class SetPhasersTo extends CardImpl {

    public SetPhasersTo(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{W}");

        // Choose one --
        // * Stun -- Tap target creature and put a stun counter on it.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addEffect(
            new AddCountersTargetEffect(CounterType.STUN.createInstance())
                .withTargetDescription("it").concatBy("and")
        );
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().withFlavorWord("Stun");

        // * Kill -- This spell deals 3 damage to target attacking creature.
        Mode mode = new Mode(new DamageTargetEffect(3)).withFlavorWord("Kill");
        mode.addTarget(new TargetAttackingCreature());
        this.getSpellAbility().addMode(mode);
    }

    private SetPhasersTo(final SetPhasersTo card) {
        super(card);
    }

    @Override
    public SetPhasersTo copy() {
        return new SetPhasersTo(this);
    }
}
