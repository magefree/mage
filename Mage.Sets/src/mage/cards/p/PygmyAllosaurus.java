
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.keyword.SwampwalkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author North
 */
public final class PygmyAllosaurus extends CardImpl {

    public PygmyAllosaurus(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}");
        this.subtype.add(SubType.DINOSAUR);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Swampwalk
        this.addAbility(new SwampwalkAbility());
    }

    private PygmyAllosaurus(final PygmyAllosaurus card) {
        super(card);
    }

    @Override
    public PygmyAllosaurus copy() {
        return new PygmyAllosaurus(this);
    }
}
