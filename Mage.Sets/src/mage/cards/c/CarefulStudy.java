
package mage.cards.c;

import java.util.UUID;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author magenoxx_at_gmail.com
 */
public final class CarefulStudy extends CardImpl {

    public CarefulStudy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{U}");


        // Draw two cards, then discard two cards.
        this.getSpellAbility().addEffect(new DrawDiscardControllerEffect(2,2));
    }

    private CarefulStudy(final CarefulStudy card) {
        super(card);
    }

    @Override
    public CarefulStudy copy() {
        return new CarefulStudy(this);
    }
}
