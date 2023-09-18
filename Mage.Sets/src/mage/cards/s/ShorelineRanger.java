
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ShorelineRanger extends CardImpl {

    public ShorelineRanger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.RANGER);

        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Islandcycling {2}
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ShorelineRanger(final ShorelineRanger card) {
        super(card);
    }

    @Override
    public ShorelineRanger copy() {
        return new ShorelineRanger(this);
    }
}
