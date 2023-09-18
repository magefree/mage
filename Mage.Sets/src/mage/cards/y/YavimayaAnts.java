
package mage.cards.y;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox

 */
public final class YavimayaAnts extends CardImpl {

    public YavimayaAnts(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{G}{G}");
        this.subtype.add(SubType.INSECT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(1);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        // Haste
        this.addAbility(HasteAbility.getInstance());
        // Cumulative upkeep {G}{G}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{G}{G}")));
    }

    private YavimayaAnts(final YavimayaAnts card) {
        super(card);
    }

    @Override
    public YavimayaAnts copy() {
        return new YavimayaAnts(this);
    }
}
