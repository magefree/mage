
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class Gallowbraid extends CardImpl {

    public Gallowbraid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Cumulative upkeep-Pay 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(1)));
    }

    private Gallowbraid(final Gallowbraid card) {
        super(card);
    }

    @Override
    public Gallowbraid copy() {
        return new Gallowbraid(this);
    }
}
