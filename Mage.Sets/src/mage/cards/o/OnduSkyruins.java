package mage.cards.o;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class OnduSkyruins extends CardImpl {

    public OnduSkyruins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Ondu Skyruins enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private OnduSkyruins(final OnduSkyruins card) {
        super(card);
    }

    @Override
    public OnduSkyruins copy() {
        return new OnduSkyruins(this);
    }
}
