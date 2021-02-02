
package mage.cards.w;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class WeaveFate extends CardImpl {

    public WeaveFate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");


        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private WeaveFate(final WeaveFate card) {
        super(card);
    }

    @Override
    public WeaveFate copy() {
        return new WeaveFate(this);
    }
}
