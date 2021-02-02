
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author Styxo
 */
public final class ForcePush extends CardImpl {

    public ForcePush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Put target creature on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCreaturePermanent());

        // Scry 1
        this.getSpellAbility().addEffect(new ScryEffect(1));
    }

    private ForcePush(final ForcePush card) {
        super(card);
    }

    @Override
    public ForcePush copy() {
        return new ForcePush(this);
    }
}
