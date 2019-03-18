
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.ChangelingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author vereena42 & L_J
 */
public final class DrJuliusJumblemorph extends CardImpl {

    public DrJuliusJumblemorph(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");
        this.addSuperType(SuperType.LEGENDARY);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Dr. Julius Jumblemorph is every creature type (even if this card isn't on the battlefield).
        this.addAbility(ChangelingAbility.getInstance());
        
        // Whenever a host enters the battlefield under your control, you may search your library and/or graveyard for a card with augment and combine it with that host. If you search your library this way, shuffle it.
        // TODO: Host currently isn't implemented, so this ability currently would never trigger
    }

    public DrJuliusJumblemorph(final DrJuliusJumblemorph card) {
        super(card);
    }

    @Override
    public DrJuliusJumblemorph copy() {
        return new DrJuliusJumblemorph(this);
    }
}
