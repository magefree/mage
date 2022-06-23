
package mage.cards.u;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class UktabiEfreet extends CardImpl {

    public UktabiEfreet(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.EFREET);

        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Cumulative upkeep {G}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{G}")));
    }

    private UktabiEfreet(final UktabiEfreet card) {
        super(card);
    }

    @Override
    public UktabiEfreet copy() {
        return new UktabiEfreet(this);
    }
}
