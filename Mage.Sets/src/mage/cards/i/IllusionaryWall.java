
package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

/**
 *
 * @author LoneFox
 */
public final class IllusionaryWall extends CardImpl {

    public IllusionaryWall(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{U}");
        this.subtype.add(SubType.ILLUSION);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Cumulative upkeep {U}
        this.addAbility(new CumulativeUpkeepAbility(new ManaCostsImpl<>("{U}")));
    }

    private IllusionaryWall(final IllusionaryWall card) {
        super(card);
    }

    @Override
    public IllusionaryWall copy() {
        return new IllusionaryWall(this);
    }
}
