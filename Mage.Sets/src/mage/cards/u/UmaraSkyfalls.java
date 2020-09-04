package mage.cards.u;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class UmaraSkyfalls extends CardImpl {

    public UmaraSkyfalls(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Umara Skyfalls enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {U}.
        this.addAbility(new BlueManaAbility());
    }

    private UmaraSkyfalls(final UmaraSkyfalls card) {
        super(card);
    }

    @Override
    public UmaraSkyfalls copy() {
        return new UmaraSkyfalls(this);
    }
}
