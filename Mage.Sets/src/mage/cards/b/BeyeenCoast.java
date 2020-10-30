package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BeyeenCoast extends CardImpl {

    public BeyeenCoast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Beyeen Coast enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private BeyeenCoast(final BeyeenCoast card) {
        super(card);
    }

    @Override
    public BeyeenCoast copy() {
        return new BeyeenCoast(this);
    }
}
