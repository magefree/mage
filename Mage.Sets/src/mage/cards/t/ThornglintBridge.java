package mage.cards.t;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ThornglintBridge extends CardImpl {

    public ThornglintBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Thornglint Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {G} or {W}.
        this.addAbility(new GreenManaAbility());
        this.addAbility(new WhiteManaAbility());
    }

    private ThornglintBridge(final ThornglintBridge card) {
        super(card);
    }

    @Override
    public ThornglintBridge copy() {
        return new ThornglintBridge(this);
    }
}
