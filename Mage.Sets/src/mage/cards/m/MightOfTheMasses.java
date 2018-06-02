
package mage.cards.m;

import java.util.UUID;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class MightOfTheMasses extends CardImpl {

    public MightOfTheMasses(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G}");


        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new BoostTargetEffect(value, value, Duration.EndOfTurn, true));
    }

    public MightOfTheMasses(final MightOfTheMasses card) {
        super(card);
    }

    @Override
    public MightOfTheMasses copy() {
        return new MightOfTheMasses(this);
    }
}
