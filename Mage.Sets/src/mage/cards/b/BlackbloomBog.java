package mage.cards.b;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BlackbloomBog extends CardImpl {

    public BlackbloomBog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Blackbloom Bog enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {B}.
        this.addAbility(new BlackManaAbility());
    }

    private BlackbloomBog(final BlackbloomBog card) {
        super(card);
    }

    @Override
    public BlackbloomBog copy() {
        return new BlackbloomBog(this);
    }
}
