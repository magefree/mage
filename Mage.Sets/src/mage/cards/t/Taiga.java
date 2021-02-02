
package mage.cards.t;

import java.util.UUID;
import mage.abilities.mana.GreenManaAbility;
import mage.abilities.mana.RedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class Taiga extends CardImpl {

    public Taiga(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.MOUNTAIN);

        this.addAbility(new GreenManaAbility());
        this.addAbility(new RedManaAbility());
        
    }

    private Taiga(final Taiga card) {
        super(card);
    }

    @Override
    public Taiga copy() {
        return new Taiga(this);
    }
}
