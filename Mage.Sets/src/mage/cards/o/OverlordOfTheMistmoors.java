package mage.cards.o;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldOrAttacksSourceTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.ImpendingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.permanent.token.InsectWhiteToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OverlordOfTheMistmoors extends CardImpl {

    public OverlordOfTheMistmoors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{5}{W}{W}");

        this.subtype.add(SubType.AVATAR);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Impending 4--{2}{W}{W}
        this.addAbility(new ImpendingAbility("{2}{W}{W}"));

        // Whenever Overlord of the Mistmoors enters or attacks, create two 2/1 white Insect creature tokens with flying.
        this.addAbility(new EntersBattlefieldOrAttacksSourceTriggeredAbility(new CreateTokenEffect(new InsectWhiteToken(), 2)));
    }

    private OverlordOfTheMistmoors(final OverlordOfTheMistmoors card) {
        super(card);
    }

    @Override
    public OverlordOfTheMistmoors copy() {
        return new OverlordOfTheMistmoors(this);
    }
}
