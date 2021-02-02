
package mage.cards.o;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author LevelX2
 */
public final class OpulentPalace extends CardImpl {

    public OpulentPalace(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Opulent Palace enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        // {T}: Add {B}, {G}, or {U}.
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private OpulentPalace(final OpulentPalace card) {
        super(card);
    }

    @Override
    public OpulentPalace copy() {
        return new OpulentPalace(this);
    }
}
