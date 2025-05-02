package mage.cards.a;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class AerialManeuver extends CardImpl {

    public AerialManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Target creature gets +1/+1 and gains flying and first strike until end of turn.
        getSpellAbility().addEffect(new BoostTargetEffect(1,1, Duration.EndOfTurn)
                .setText("target creature gets +1/+1"));
        getSpellAbility().addEffect(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn)
                .setText("and gains flying"));
        getSpellAbility().addEffect(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn)
                .setText("and first strike until end of turn"));
        getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private AerialManeuver(final AerialManeuver card) {
        super(card);
    }

    @Override
    public AerialManeuver copy() {
        return new AerialManeuver(this);
    }
}
