package mage.cards.v;

import java.util.UUID;

import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;
import mage.target.targetpointer.SecondTargetPointer;

/**
 *
 * @author muz
 */
public final class VibrantOutburst extends CardImpl {

    public VibrantOutburst(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{U}{R}");

        // Vibrant Outburst deals 3 damage to any target. Tap up to one target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget().withChooseHint("To deal damage"));
        Effect effect = new TapTargetEffect().setTargetPointer(new SecondTargetPointer());
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 1).withChooseHint("To tap"));


    }

    private VibrantOutburst(final VibrantOutburst card) {
        super(card);
    }

    @Override
    public VibrantOutburst copy() {
        return new VibrantOutburst(this);
    }
}
