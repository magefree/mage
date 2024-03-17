package mage.cards.v;

import java.util.UUID;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author anonymous
 */
public final class Vault13DwellersJourney extends CardImpl {

    public Vault13DwellersJourney(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");
        
        this.subtype.add(SubType.SAGA);

        // (As this Saga enters and after your draw step, add a lore counter. Sacrifice after III.)
        // I -- For each player, exile up to one other target enchantment or creature that player controls until Vault 13 leaves the battlefield.
        // II -- You gain 2 life and scry 2.
        // III -- Return two cards exiled with Vault 13 to the battlefield under their owners' control and put the rest on the bottom of their owners' libraries.
    }

    private Vault13DwellersJourney(final Vault13DwellersJourney card) {
        super(card);
    }

    @Override
    public Vault13DwellersJourney copy() {
        return new Vault13DwellersJourney(this);
    }
}
