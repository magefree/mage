package mage.cards.r;

import java.util.UUID;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledPermanent;

/**
 * @author balazskristof
 */
public final class RoilingDragonstorm extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent(SubType.DRAGON, "a Dragon");

    public RoilingDragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}");

        // When this enchantment enters, draw two cards, then discard a card.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new DrawDiscardControllerEffect(2, 1)));

        // When a Dragon you control enters, return this enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter));
    }

    private RoilingDragonstorm(final RoilingDragonstorm card) {
        super(card);
    }

    @Override
    public RoilingDragonstorm copy() {
        return new RoilingDragonstorm(this);
    }
}
