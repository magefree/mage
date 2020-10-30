package mage.cards.z;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ZofBloodbog extends CardImpl {

    public ZofBloodbog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Zof Bloodbog enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private ZofBloodbog(final ZofBloodbog card) {
        super(card);
    }

    @Override
    public ZofBloodbog copy() {
        return new ZofBloodbog(this);
    }
}
