package mage.cards.u;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.FlashAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UnderseaInvader extends CardImpl {

    public UnderseaInvader(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}{U}");

        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Undersear Invade enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
    }

    private UnderseaInvader(final UnderseaInvader card) {
        super(card);
    }

    @Override
    public UnderseaInvader copy() {
        return new UnderseaInvader(this);
    }
}
