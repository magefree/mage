
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

/**
 * @author Loki
 */
public final class TemporalSpring extends CardImpl {

    public TemporalSpring(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}{U}");



        // Put target permanent on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private TemporalSpring(final TemporalSpring card) {
        super(card);
    }

    @Override
    public TemporalSpring copy() {
        return new TemporalSpring(this);
    }
}
