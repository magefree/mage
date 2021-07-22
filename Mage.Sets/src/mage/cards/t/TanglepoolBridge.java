package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TanglepoolBridge extends CardImpl {

    public TanglepoolBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Tanglepool Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {G} or {U}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private TanglepoolBridge(final TanglepoolBridge card) {
        super(card);
    }

    @Override
    public TanglepoolBridge copy() {
        return new TanglepoolBridge(this);
    }
}
