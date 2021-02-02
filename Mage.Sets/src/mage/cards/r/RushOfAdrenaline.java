
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class RushOfAdrenaline extends CardImpl {

    public RushOfAdrenaline(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");

        // Target creature gets +2/+1 and gains trample until end of turn.
        Effect effect = new BoostTargetEffect(2, 1, Duration.EndOfTurn);
        effect.setText("Target creature gets +2/+1");
        this.getSpellAbility().addEffect(effect);
        effect = new GainAbilityTargetEffect(TrampleAbility.getInstance(), Duration.EndOfTurn);
        effect.setText("and gains trample until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private RushOfAdrenaline(final RushOfAdrenaline card) {
        super(card);
    }

    @Override
    public RushOfAdrenaline copy() {
        return new RushOfAdrenaline(this);
    }
}
