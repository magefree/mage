package mage.cards.t;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.BeamMeUpAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author muz
 */
public final class TransporterMalfunction extends CardImpl {

    public TransporterMalfunction(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{R}");

        // Transporter Malfunction deals 3 damage to any target.
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());

        // Beam me up {3}{R}{R}{R}
        this.addAbility(new BeamMeUpAbility(this, "{3}{R}{R}{R}"));
    }

    private TransporterMalfunction(final TransporterMalfunction card) {
        super(card);
    }

    @Override
    public TransporterMalfunction copy() {
        return new TransporterMalfunction(this);
    }
}
