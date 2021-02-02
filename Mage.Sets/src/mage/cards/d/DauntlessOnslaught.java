
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class DauntlessOnslaught extends CardImpl {

    public DauntlessOnslaught(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{2}{W}");


        // Up to two target creatures each get +2/+2 until end of turn.
        Effect effect = new BoostTargetEffect(2,2, Duration.EndOfTurn);
        effect.setText("Up to two target creatures each get +2/+2 until end of turn");
        this.getSpellAbility().addEffect(effect);
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0,2));
    }

    private DauntlessOnslaught(final DauntlessOnslaught card) {
        super(card);
    }

    @Override
    public DauntlessOnslaught copy() {
        return new DauntlessOnslaught(this);
    }
}
