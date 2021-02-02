
package mage.cards.b;

import java.util.UUID;
import mage.abilities.mana.BlackManaAbility;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author jonubuu
 */
public final class Bayou extends CardImpl {

    public Bayou(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.subtype.add(SubType.SWAMP, SubType.FOREST);
        this.addAbility(new BlackManaAbility());
        this.addAbility(new GreenManaAbility());
    }

    private Bayou(final Bayou card) {
        super(card);
    }

    @Override
    public Bayou copy() {
        return new Bayou(this);
    }
}
