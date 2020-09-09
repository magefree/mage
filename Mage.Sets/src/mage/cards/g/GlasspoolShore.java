package mage.cards.g;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class GlasspoolShore extends CardImpl {

    public GlasspoolShore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Glasspool Shore enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private GlasspoolShore(final GlasspoolShore card) {
        super(card);
    }

    @Override
    public GlasspoolShore copy() {
        return new GlasspoolShore(this);
    }
}
