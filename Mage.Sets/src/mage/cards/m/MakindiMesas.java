package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MakindiMesas extends CardImpl {

    public MakindiMesas(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.modalDFC = true;
        this.nightCard = true;

        // Makindi Mesas enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {W}.
        this.addAbility(new WhiteManaAbility());
    }

    private MakindiMesas(final MakindiMesas card) {
        super(card);
    }

    @Override
    public MakindiMesas copy() {
        return new MakindiMesas(this);
    }
}
