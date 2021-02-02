

package mage.cards.n;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author Loki
 */
public final class NoxiousRevival extends CardImpl {

    public NoxiousRevival(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{G/P}");

        // Put target card from a graveyard on top of its owner’s library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetCardInGraveyard());
    }

    private NoxiousRevival(final NoxiousRevival card) {
        super(card);
    }

    @Override
    public NoxiousRevival copy() {
        return new NoxiousRevival(this);
    }

}
