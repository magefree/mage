package mage.cards.c;

import java.util.UUID;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author muz
 */
public final class ConcentratedFire extends CardImpl {

    public ConcentratedFire(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{3}{R}");

        // Concentrated Fire deals 5 damage to target creature.
        this.getSpellAbility().addEffect(new DamageTargetEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ConcentratedFire(final ConcentratedFire card) {
        super(card);
    }

    @Override
    public ConcentratedFire copy() {
        return new ConcentratedFire(this);
    }
}
