
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class Waterwhirl extends CardImpl {

    public Waterwhirl(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{4}{U}{U}");


        // Return up to two target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private Waterwhirl(final Waterwhirl card) {
        super(card);
    }

    @Override
    public Waterwhirl copy() {
        return new Waterwhirl(this);
    }
}
