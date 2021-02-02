
package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Tundra extends CardImpl {

    public Tundra(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);
        
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());
    }

    private Tundra(final Tundra card) {
        super(card);
    }

    @Override
    public Tundra copy() {
        return new Tundra(this);
    }
}
