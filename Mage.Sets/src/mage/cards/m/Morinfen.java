
package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.PayLifeCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author LoneFox
 */
public final class Morinfen extends CardImpl {

    public Morinfen(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{B}{B}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cumulative upkeep-Pay 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new PayLifeCost(1)));
    }

    public Morinfen(final Morinfen card) {
        super(card);
    }

    @Override
    public Morinfen copy() {
        return new Morinfen(this);
    }
}
