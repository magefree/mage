
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class CoordinatedAssault extends CardImpl {

    public CoordinatedAssault(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Up to two target creatures each get +1/+0 and gain first strike until end of turn.
        Effect effect = new BoostTargetEffect(1,0, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn, "and gain first strike until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    private CoordinatedAssault(final CoordinatedAssault card) {
        super(card);
    }

    @Override
    public CoordinatedAssault copy() {
        return new CoordinatedAssault(this);
    }
}
