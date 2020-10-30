package mage.cards.k;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KabiraPlateau extends CardImpl {

    public KabiraPlateau(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Kabira Plateau enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private KabiraPlateau(final KabiraPlateau card) {
        super(card);
    }

    @Override
    public KabiraPlateau copy() {
        return new KabiraPlateau(this);
    }
}
