package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.DrawDiscardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class StadiumTidalmage extends CardImpl {

    public StadiumTidalmage(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}{R}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever this creature enters or attacks, you may draw a card. If you do, discard a card.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(
                new DrawDiscardControllerEffect(1, 1, true)
        ));
    }

    private StadiumTidalmage(final StadiumTidalmage card) {
        super(card);
    }

    @Override
    public StadiumTidalmage copy() {
        return new StadiumTidalmage(this);
    }
}
