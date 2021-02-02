
package mage.cards.b;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Badlands extends CardImpl {

    public Badlands(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.SWAMP, SubType.MOUNTAIN);

        
        this.addAbility(new BlackManaAbility());
        this.addAbility(new RedManaAbility());
    }

    private Badlands(final Badlands card) {
        super(card);
    }

    @Override
    public Badlands copy() {
        return new Badlands(this);
    }
}
