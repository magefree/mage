package mage.cards.t;

import mage.abilities.common.EntersBattlefieldControlledTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;
import mage.game.permanent.token.Soldier22Token;

import java.util.UUID;

/**
 * @author PurpleCrowbar
 */
public final class TeemingDragonstorm extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent(SubType.DRAGON, "a Dragon");

    public TeemingDragonstorm(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        // When this enchantment enters, create two 2/2 white Soldier creature tokens.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new Soldier22Token(), 2), false));

        // When a Dragon you control enters, return this enchantment to its owner's hand.
        this.addAbility(new EntersBattlefieldControlledTriggeredAbility(new ReturnToHandSourceEffect(), filter));
    }

    private TeemingDragonstorm (final TeemingDragonstorm card) {
        super(card);
    }

    @Override
    public TeemingDragonstorm copy() {
        return new TeemingDragonstorm(this);
    }
}
