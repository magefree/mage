
package mage.cards.t;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LoneFox
 */
public final class TouchOfBrilliance extends CardImpl {

    public TouchOfBrilliance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{U}");

        // Draw two cards.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(2));
    }

    private TouchOfBrilliance(final TouchOfBrilliance card) {
        super(card);
    }

    @Override
    public TouchOfBrilliance copy() {
        return new TouchOfBrilliance(this);
    }
}
