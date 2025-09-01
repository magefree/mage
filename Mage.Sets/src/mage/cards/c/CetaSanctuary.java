
package mage.cards.c;

import mage.ObjectColor;
import mage.abilities.common.SanctuaryTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CetaSanctuary extends CardImpl {

    public CetaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card.
        this.addAbility(new SanctuaryTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1),
                new DrawDiscardControllerEffect(2, 1),
                ObjectColor.GREEN, ObjectColor.RED, "draw a card, then discard a card. " +
                "If you control a red permanent and a green permanent, instead draw two cards, then discard a card."
        ));
    }

    private CetaSanctuary(final CetaSanctuary card) {
        super(card);
    }

    @Override
    public CetaSanctuary copy() {
        return new CetaSanctuary(this);
    }
}
