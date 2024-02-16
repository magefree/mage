
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class IllusionaryForces extends CardImpl {

    public IllusionaryForces(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cumulative upkeep {U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}")));
    }

    private IllusionaryForces(final IllusionaryForces card) {
        super(card);
    }

    @Override
    public IllusionaryForces copy() {
        return new IllusionaryForces(this);
    }
}
