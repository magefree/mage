
package mage.cards.v;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class VisionSkeins extends CardImpl {

    public VisionSkeins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{U}");


        // Each player draws two cards.
        this.getSpellAbility().addEffect(new DrawCardAllEffect(2));
    }

    private VisionSkeins(final VisionSkeins card) {
        super(card);
    }

    @Override
    public VisionSkeins copy() {
        return new VisionSkeins(this);
    }
}
