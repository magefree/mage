

package mage.cards.s;

import java.util.UUID;
import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author nantuko
 */
public final class SoulParry extends CardImpl {

    public SoulParry (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        
        // Prevent all damage one or two target creatures would deal this turn
        Target target = new TargetCreaturePermanent(1,2);
        target.setTargetName("one or two creatures");
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn));
        this.getSpellAbility().addTarget(target);
    }

    private SoulParry(final SoulParry card) {
        super(card);
    }

    @Override
    public SoulParry copy() {
        return new SoulParry(this);
    }

}
