package mage.cards.p;

import mage.abilities.effects.common.DamageWithExcessEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class PigmentStorm extends CardImpl {

    public PigmentStorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}{R}");

        // Pigment Storm deals 5 damage to target creature. Excess damage is dealt to that creature's controller instead.
        this.getSpellAbility().addEffect(new DamageWithExcessEffect(5));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private PigmentStorm(final PigmentStorm card) {
        super(card);
    }

    @Override
    public PigmentStorm copy() {
        return new PigmentStorm(this);
    }
}
