package mage.cards.c;

import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeAllEffect;
import mage.abilities.effects.common.continuous.DamageCantBePreventedEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CallInAProfessional extends CardImpl {

    public CallInAProfessional(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{R}");

        // Players can't gain life this turn. Damage can't be prevented this turn. Call In a Professional deals 3 damage to any target.
        this.getSpellAbility().addEffect(new CantGainLifeAllEffect(Duration.EndOfTurn));
        this.getSpellAbility().addEffect(new DamageCantBePreventedEffect(
                Duration.EndOfTurn, "Damage can't be prevented this turn"
        ));
        this.getSpellAbility().addEffect(new DamageTargetEffect(3));
        this.getSpellAbility().addTarget(new TargetAnyTarget());
    }

    private CallInAProfessional(final CallInAProfessional card) {
        super(card);
    }

    @Override
    public CallInAProfessional copy() {
        return new CallInAProfessional(this);
    }
}
