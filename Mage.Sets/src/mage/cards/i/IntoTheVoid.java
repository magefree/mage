
package mage.cards.i;

import java.util.UUID;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author noxx
 */
public final class IntoTheVoid extends CardImpl {

    public IntoTheVoid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");


        // Return up to two target creatures to their owners' hands.
        this.getSpellAbility().addEffect(new ReturnToHandTargetEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(0, 2));
    }

    private IntoTheVoid(final IntoTheVoid card) {
        super(card);
    }

    @Override
    public IntoTheVoid copy() {
        return new IntoTheVoid(this);
    }
}
