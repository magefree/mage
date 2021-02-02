
package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class ShowOfValor extends CardImpl {

    public ShowOfValor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Target creature gets +2/+4 until end of turn.
        this.getSpellAbility().addEffect(new BoostTargetEffect(2, 4, Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
    }

    private ShowOfValor(final ShowOfValor card) {
        super(card);
    }

    @Override
    public ShowOfValor copy() {
        return new ShowOfValor(this);
    }
}
