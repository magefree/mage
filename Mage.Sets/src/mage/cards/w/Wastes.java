
package mage.cards.w;

import java.util.UUID;
import mage.abilities.mana.ColorlessManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SuperType;

/**
 *
 * @author fireshoes
 */
public final class Wastes extends CardImpl {

    public Wastes(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        this.supertype.add(SuperType.BASIC);

        // {T}: Add {C}.
        this.addAbility(new ColorlessManaAbility());
    }

    private Wastes(final Wastes card) {
        super(card);
    }

    @Override
    public Wastes copy() {
        return new Wastes(this);
    }
}
