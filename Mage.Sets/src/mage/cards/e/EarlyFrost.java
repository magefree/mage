package mage.cards.e;

import java.util.UUID;
import mage.abilities.effects.common.TapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.common.TargetLandPermanent;

/**
 *
 * @author Plopman
 */
public final class EarlyFrost extends CardImpl {

    public EarlyFrost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Tap up to three target lands.
        this.getSpellAbility().addEffect(new TapTargetEffect());
        this.getSpellAbility().addTarget(new TargetLandPermanent(0, 3));
    }

    private EarlyFrost(final EarlyFrost card) {
        super(card);
    }

    @Override
    public EarlyFrost copy() {
        return new EarlyFrost(this);
    }
}
