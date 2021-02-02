
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.common.SanctuaryInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class CetaSanctuary extends CardImpl {

    public CetaSanctuary(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. If you control a red permanent and a green permanent, instead draw two cards, then discard a card.
        Ability ability = new SanctuaryInterveningIfTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1), new DrawDiscardControllerEffect(2, 1), ObjectColor.GREEN, ObjectColor.RED,
                "At the beginning of your upkeep, if you control a red or green permanent, draw a card, then discard a card. "
                + "If you control a red permanent and a green permanent, instead draw two cards, then discard a card."
        );
        this.addAbility(ability);
    }

    private CetaSanctuary(final CetaSanctuary card) {
        super(card);
    }

    @Override
    public CetaSanctuary copy() {
        return new CetaSanctuary(this);
    }
}
