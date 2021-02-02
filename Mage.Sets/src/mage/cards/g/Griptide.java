
package mage.cards.g;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public final class Griptide extends CardImpl {

    public Griptide(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Put target creature on top of its owner's library.
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
    }

    private Griptide(final Griptide card) {
        super(card);
    }

    @Override
    public Griptide copy() {
        return new Griptide(this);
    }
}
