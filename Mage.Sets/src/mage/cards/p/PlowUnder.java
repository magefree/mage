package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class PlowUnder extends CardImpl {

    public PlowUnder(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}{G}");


        // Put two target lands on top of their owners' libraries.
        this.getSpellAbility().addEffect(new PutOnLibraryTargetEffect(true));
        this.getSpellAbility().addTarget(new TargetLandPermanent(2));
    }

    private PlowUnder(final PlowUnder card) {
        super(card);
    }

    @Override
    public PlowUnder copy() {
        return new PlowUnder(this);
    }
}
