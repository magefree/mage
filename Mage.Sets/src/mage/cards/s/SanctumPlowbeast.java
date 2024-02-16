
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.IslandcyclingAbility;
import mage.abilities.keyword.PlainscyclingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author Plopman
 */
public final class SanctumPlowbeast extends CardImpl {

    public SanctumPlowbeast(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{4}{W}{U}");
        this.subtype.add(SubType.BEAST);



        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Plainscycling {2}, islandcycling {2}
        this.addAbility(new PlainscyclingAbility(new ManaCostsImpl<>("{2}")));
        this.addAbility(new IslandcyclingAbility(new ManaCostsImpl<>("{2}")));
    }

    private SanctumPlowbeast(final SanctumPlowbeast card) {
        super(card);
    }

    @Override
    public SanctumPlowbeast copy() {
        return new SanctumPlowbeast(this);
    }
}
