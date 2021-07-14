package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DrossforgeBridge extends CardImpl {

    public DrossforgeBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Drossforge Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {B} or {R}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private DrossforgeBridge(final DrossforgeBridge card) {
        super(card);
    }

    @Override
    public DrossforgeBridge copy() {
        return new DrossforgeBridge(this);
    }
}
