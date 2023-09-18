
package mage.cards.l;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ReplicateAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class LeapOfFlame extends CardImpl {

    public LeapOfFlame(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}{R}");


        // Replicate {U}{R}
        this.addAbility(new ReplicateAbility("{U}{R}"));
        // Target creature gets +1/+0 and gains flying and first strike until end of turn.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        Effect effect = new BoostTargetEffect(1,0,Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+0");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains flying");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and first strike until end of turn");
        this.getSpellAbility().addEffect(effect);


    }

    private LeapOfFlame(final LeapOfFlame card) {
        super(card);
    }

    @Override
    public LeapOfFlame copy() {
        return new LeapOfFlame(this);
    }
}
