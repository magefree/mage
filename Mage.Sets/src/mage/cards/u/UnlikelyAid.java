package mage.cards.u;

import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.IndestructibleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnlikelyAid extends CardImpl {

    public UnlikelyAid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{B}");

        // Target creature gets +2/+0 and gains indestructible until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(
                2, 0, Duration.EndOfTurn
        ).setText("Target creature gets +2/+0"));
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(
                IndestructibleAbility.getInstance(), Duration.EndOfTurn
        ).setText("and gains indestructible until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private UnlikelyAid(final UnlikelyAid card) {
        super(card);
    }

    @Override
    public UnlikelyAid copy() {
        return new UnlikelyAid(this);
    }
}
