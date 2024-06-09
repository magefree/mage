package mage.cards.w;

import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.counter.TimeTravelEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class WibblyWobblyTimeyWimey extends CardImpl {

    public WibblyWobblyTimeyWimey(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{U}");

        // Time travel. (For each suspended card you own and each permanent you control with a time counter on it, you may add or remove a time counter.)
        this.getSpellAbility().addEffect(new TimeTravelEffect());

        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private WibblyWobblyTimeyWimey(final WibblyWobblyTimeyWimey card) {
        super(card);
    }

    @Override
    public WibblyWobblyTimeyWimey copy() {
        return new WibblyWobblyTimeyWimey(this);
    }
}
