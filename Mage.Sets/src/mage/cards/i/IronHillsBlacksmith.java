package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.AxeToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class IronHillsBlacksmith extends CardImpl {

    public IronHillsBlacksmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Double strike
        this.addAbility(DoubleStrikeAbility.getInstance());

        // When this creature enters, create a colorless Equipment artifact token named Axe with "Equipped creature gets +1/+0" and equip {2}.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new AxeToken())));
    }

    private IronHillsBlacksmith(final IronHillsBlacksmith card) {
        super(card);
    }

    @Override
    public IronHillsBlacksmith copy() {
        return new IronHillsBlacksmith(this);
    }
}
