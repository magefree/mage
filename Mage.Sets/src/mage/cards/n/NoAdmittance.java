package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

/**
 *
 * @author muz
 */
public final class NoAdmittance extends CardImpl {

    public NoAdmittance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // No Admittance deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Empower Jace 1.
        this.getSpellAbility().addEffect(new EmpowerJaceEffect(1));
    }

    private NoAdmittance(final NoAdmittance card) {
        super(card);
    }

    @Override
    public NoAdmittance copy() {
        return new NoAdmittance(this);
    }
}
