
package mage.cards.h;

import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class HowToKeepAnIzzetMageBusy extends CardImpl {

    public HowToKeepAnIzzetMageBusy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{U/R}");

        // Return How to Keep an Izzet Mage Busy to its owner's hand.
        this.getSpellAbility().addEffect(new ReturnToHandSourceEffect());
    }

    private HowToKeepAnIzzetMageBusy(final HowToKeepAnIzzetMageBusy card) {
        super(card);
    }

    @Override
    public HowToKeepAnIzzetMageBusy copy() {
        return new HowToKeepAnIzzetMageBusy(this);
    }
}
