
package mage.cards.d;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class DosansOldestChant extends CardImpl {

    public DosansOldestChant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{4}{G}");

        // You gain 6 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(6));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private DosansOldestChant(final DosansOldestChant card) {
        super(card);
    }

    @Override
    public DosansOldestChant copy() {
        return new DosansOldestChant(this);
    }
}
