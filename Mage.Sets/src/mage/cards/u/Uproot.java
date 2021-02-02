
package mage.cards.u;

import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetLandPermanent;

import java.util.UUID;

/**
 *
 * @author Loki
 */
public final class Uproot extends CardImpl {

    public Uproot(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");
        this.subtype.add(SubType.ARCANE);

        // Put target land on top of its owner's library.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetLandPermanent());
    }

    private Uproot(final Uproot card) {
        super(card);
    }

    @Override
    public Uproot copy() {
        return new Uproot(this);
    }
}
