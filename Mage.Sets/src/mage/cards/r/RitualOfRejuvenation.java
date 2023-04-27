
package mage.cards.r;

import java.util.UUID;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class RitualOfRejuvenation extends CardImpl {

    public RitualOfRejuvenation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{W}");

        // You gain 4 life.
        this.getSpellAbility().addEffect(new GainLifeEffect(4));
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private RitualOfRejuvenation(final RitualOfRejuvenation card) {
        super(card);
    }

    @Override
    public RitualOfRejuvenation copy() {
        return new RitualOfRejuvenation(this);
    }
}
