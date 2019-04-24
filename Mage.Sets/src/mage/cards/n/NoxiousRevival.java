

package mage.cards.n;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

/**
 *
 * @author Loki
 */
public final class NoxiousRevival extends CardImpl {

    public NoxiousRevival (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{G/P}");

        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
    }

    public NoxiousRevival (final NoxiousRevival card) {
        super(card);
    }

    @Override
    public NoxiousRevival copy() {
        return new NoxiousRevival(this);
    }

}
