
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class TitansStrength extends CardImpl {

    public TitansStrength(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{R}");


        // Target creature gets +3/+1 until end of turn. Scry 1.
        this.getSpellAbility().addEffect(new BoostTargetEffect(3,1, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private TitansStrength(final TitansStrength card) {
        super(card);
    }

    @Override
    public TitansStrength copy() {
        return new TitansStrength(this);
    }
}
