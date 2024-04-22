package mage.cards.s;

import mage.abilities.effects.common.PreventDamageByTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author nantuko
 */
public final class SoulParry extends CardImpl {

    public SoulParry (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");
        
        // Prevent all damage one or two target creatures would deal this turn
        this.getSpellAbility().addEffect(new PreventDamageByTargetEffect(Duration.EndOfTurn, false)
                .withTextOptions(false, true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(1, 2));
    }

    private SoulParry(final SoulParry card) {
        super(card);
    }

    @Override
    public SoulParry copy() {
        return new SoulParry(this);
    }

}
