package mage.cards.t;

import mage.MageInt;
import mage.abilities.common.CommanderChooseColorAbility;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThePrismaticPiper extends CardImpl {

    public ThePrismaticPiper(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SHAPESHIFTER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // If The Prismatic Piper is your commander, choose a color before the game begins. The Prismatic Piper is the chosen color.
        this.addAbility(new CommanderChooseColorAbility());

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ThePrismaticPiper(final ThePrismaticPiper card) {
        super(card);
    }

    @Override
    public ThePrismaticPiper copy() {
        return new ThePrismaticPiper(this);
    }
}
