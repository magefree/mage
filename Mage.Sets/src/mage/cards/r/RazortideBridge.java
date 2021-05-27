package mage.cards.r;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RazortideBridge extends CardImpl {

    public RazortideBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Razortide Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {W} or {U}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private RazortideBridge(final RazortideBridge card) {
        super(card);
    }

    @Override
    public RazortideBridge copy() {
        return new RazortideBridge(this);
    }
}
