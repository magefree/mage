package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ImpendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.EverywhereToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverlordOfTheHauntwoods extends CardImpl {

    public OverlordOfTheHauntwoods(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{3}{G}{G}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(5);

        // Impending 4--{1}{G}{G}
        this.addAbility(new ImpendingAbility(4, "{1}{G}{G}"));

        // Whenever Overlord of the Hauntwoods enters or attacks, create a tapped colorless land token named Everywhere that is every basic land type.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new CreateTokenEffect(new EverywhereToken(), 1, true)
        ));
    }

    private OverlordOfTheHauntwoods(final OverlordOfTheHauntwoods card) {
        super(card);
    }

    @Override
    public OverlordOfTheHauntwoods copy() {
        return new OverlordOfTheHauntwoods(this);
    }
}
