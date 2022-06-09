
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.ForestcyclingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class PaleRecluse extends CardImpl {

    public PaleRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}{W}");
        this.subtype.add(SubType.SPIDER);



        this.power = new MageInt(4);
        this.toughness = new MageInt(5);

        // Reach
        this.addAbility(ReachAbility.getInstance());
        // Forestcycling {2}
        this.addAbility(new ForestcyclingAbility(new ManaCostsImpl<>("{2}")));
        // plainscycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private PaleRecluse(final PaleRecluse card) {
        super(card);
    }

    @Override
    public PaleRecluse copy() {
        return new PaleRecluse(this);
    }
}
