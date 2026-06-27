package mage.cards.v;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class VoidHelix extends CardImpl {

    public VoidHelix(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{B}{B}");

        // Void Helix deals 5 damage to any target and you gain 5 life.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addEffect(new GainLifeEffect(5).concatBy("and"));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private VoidHelix(final VoidHelix card) {
        super(card);
    }

    @Override
    public VoidHelix copy() {
        return new VoidHelix(this);
    }
}
