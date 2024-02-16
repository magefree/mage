

package mage.cards.d;

import java.util.UUID;
import mage.abilities.keyword.IndestructibleAbility;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author Loki
 */
public final class DarksteelCitadel extends CardImpl {

    public DarksteelCitadel (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.LAND},null);
        
        // Indestructible (Effects that say "destroy" don't destroy this land.)
        this.addAbility(IndestructibleAbility.getInstance());
        
        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private DarksteelCitadel(final DarksteelCitadel card) {
        super(card);
    }

    @Override
    public DarksteelCitadel copy() {
        return new DarksteelCitadel(this);
    }

}
