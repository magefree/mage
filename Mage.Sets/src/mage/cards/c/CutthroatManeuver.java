
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CutthroatManeuver extends CardImpl {

    public CutthroatManeuver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{B}");


        // Up to two target creatures each get +1/+1 and gain lifelink until end of turn.
        Effect effect = new BoostTargetEffect(1,1, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +1/+1");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn,
                "and gain lifelink until end of turn"));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    private CutthroatManeuver(final CutthroatManeuver card) {
        super(card);
    }

    @Override
    public CutthroatManeuver copy() {
        return new CutthroatManeuver(this);
    }
}
