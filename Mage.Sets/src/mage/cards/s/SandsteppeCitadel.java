
package mage.cards.s;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class SandsteppeCitadel extends CardImpl {

    public SandsteppeCitadel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Sandsteppe Citadel enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {W}, {B}, or {G}.
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private SandsteppeCitadel(final SandsteppeCitadel card) {
        super(card);
    }

    @Override
    public SandsteppeCitadel copy() {
        return new SandsteppeCitadel(this);
    }
}
