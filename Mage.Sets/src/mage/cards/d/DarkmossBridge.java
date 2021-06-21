package mage.cards.d;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class DarkmossBridge extends CardImpl {

    public DarkmossBridge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.LAND}, "");

        // Darkmoss Bridge enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // Indestructible
        this.addAbility(IndestructibleAbility.getInstance());

        // {T}: Add {B} or {G}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private DarkmossBridge(final DarkmossBridge card) {
        super(card);
    }

    @Override
    public DarkmossBridge copy() {
        return new DarkmossBridge(this);
    }
}
