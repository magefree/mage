package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.SplitCard;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SpellAbilityType;
import mage.target.common.TargetAnyTarget;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author TheElk801
 */
public final class IntegrityIntervention extends SplitCard {

    public IntegrityIntervention(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{R/W}", "{2}{R}{W}", SpellAbilityType.SPLIT);

        // Integrity
        // Target creature gets +2/+2 until end of turn.
        this.getLeftHalfCard().getSpellAbility().addEffect(
                new BoostTargetEffect(2, 2, Duration.EndOfTurn)
        );
        this.getLeftHalfCard().getSpellAbility().addTarget(
                new TargetCreaturePermanent()
        );

        // Intervention
        // Intervention deals 3 damage to any target and you gain 3 life.
        this.getRightHalfCard().getSpellAbility().addEffect(
                new DamageTargetEffect(3).setText("Intervention deals 3 damage to any target")
        );
        this.getRightHalfCard().getSpellAbility().addEffect(
                new GainLifeEffect(3).setText("and you gain 3 life")
        );
        this.getRightHalfCard().getSpellAbility().addTarget(
                new TargetAnyTarget()
        );
    }

    private IntegrityIntervention(final IntegrityIntervention card) {
        super(card);
    }

    @Override
    public IntegrityIntervention copy() {
        return new IntegrityIntervention(this);
    }
}
