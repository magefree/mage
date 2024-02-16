
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.MountaincyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class ValleyRannet extends CardImpl {

    public ValleyRannet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{R}{G}");
        this.subtype.add(SubType.BEAST);



        this.power = new MageInt(6);
        this.toughness = new MageInt(3);

        // Mountaincycling {2}
        this.addAbility(new MountaincyclingAbility(new ManaCostsImpl<>("{2}")));
        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private ValleyRannet(final ValleyRannet card) {
        super(card);
    }

    @Override
    public ValleyRannet copy() {
        return new ValleyRannet(this);
    }
}
