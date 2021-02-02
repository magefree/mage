
package mage.cards.m;

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
public final class MortalsArdor extends CardImpl {

    public MortalsArdor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{W}");


        // Target creature gets +1/+1 and gains lifelink until end of turn.
        Effect effect = new BoostTargetEffect(1,1, Duration.EndOfTurn);
        effect.setText("Target creature gets +1/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(LifelinkAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains lifelink until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        
    }

    private MortalsArdor(final MortalsArdor card) {
        super(card);
    }

    @Override
    public MortalsArdor copy() {
        return new MortalsArdor(this);
    }
}
