
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageToTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Quercitron
 */
public final class Redeem extends CardImpl {

    public Redeem(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");


        // Prevent all damage that would be dealt this turn to up to two target creatures.
        this.getSpellAbility().addEffect(new PreventDamageToTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private Redeem(final Redeem card) {
        super(card);
    }

    @Override
    public Redeem copy() {
        return new Redeem(this);
    }
}
