package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KhalniTerritory extends CardImpl {

    public KhalniTerritory(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Khalni Territory enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {G}.
        this.addAbility(new GreenManaAbility());
    }

    private KhalniTerritory(final KhalniTerritory card) {
        super(card);
    }

    @Override
    public KhalniTerritory copy() {
        return new KhalniTerritory(this);
    }
}
