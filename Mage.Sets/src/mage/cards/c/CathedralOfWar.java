
package mage.cards.c;

import java.util.UUID;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.keyword.ExaltedAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author jeffwadsworth
 */
public final class CathedralOfWar extends CardImpl {

    public CathedralOfWar(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");

        // Cathedral of War enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());
        
        // Exalted
        this.addAbility(new ExaltedAbility());
        
        // {tap}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private CathedralOfWar(final CathedralOfWar card) {
        super(card);
    }

    @Override
    public CathedralOfWar copy() {
        return new CathedralOfWar(this);
    }
}
