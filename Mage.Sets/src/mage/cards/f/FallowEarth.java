
package mage.cards.f;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Quercitron
 */
public final class FallowEarth extends CardImpl {

    public FallowEarth(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{G}");


        // Put target land on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private FallowEarth(final FallowEarth card) {
        super(card);
    }

    @Override
    public FallowEarth copy() {
        return new FallowEarth(this);
    }
}
