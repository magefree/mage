package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.game.permanent.token.CadetToken;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class HeartstringPuller extends CardImpl {

    public HeartstringPuller(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");

        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When this creature enters, create a 2/2 colorless Wizard Soldier creature token named Cadet.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new CadetToken())));
    }

    private HeartstringPuller(final HeartstringPuller card) {
        super(card);
    }

    @Override
    public HeartstringPuller copy() {
        return new HeartstringPuller(this);
    }
}
