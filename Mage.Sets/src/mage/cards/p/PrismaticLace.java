
package mage.cards.p;

import java.util.UUID;
import mage.abilities.effects.common.continuous.BecomesColorOrColorsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.target.TargetPermanent;

/**
 *
 * @author L_J
 */
public final class PrismaticLace extends CardImpl {

    public PrismaticLace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{U}");

        // Target permanent becomes the color or colors of your choice.
        this.getSpellAbility().addEffect(new BecomesColorOrColorsTargetEffect(Duration.Custom));
        this.getSpellAbility().addTarget(new TargetPermanent());
    }

    private PrismaticLace(final PrismaticLace card) {
        super(card);
    }

    @Override
    public PrismaticLace copy() {
        return new PrismaticLace(this);
    }
}
