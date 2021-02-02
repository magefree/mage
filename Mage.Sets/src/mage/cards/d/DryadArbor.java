
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.mana.GreenManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Loki
 */
public final class DryadArbor extends CardImpl {

    public DryadArbor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND,CardType.CREATURE},"");
        this.subtype.add(SubType.FOREST);
        this.subtype.add(SubType.DRYAD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        this.color.setGreen(true);

        // <i>(Dryad Arbor isn't a spell, it's affected by summoning sickness, and it has "{tap}: Add {G}.")</i>
        this.addAbility(new GreenManaAbility());
    }

    private DryadArbor(final DryadArbor card) {
        super(card);
    }

    @Override
    public DryadArbor copy() {
        return new DryadArbor(this);
    }
}
