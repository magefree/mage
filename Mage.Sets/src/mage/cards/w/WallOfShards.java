
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.GainLifeOpponentCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;

/**
 *
 * @author klayhamn
 */
public final class WallOfShards extends CardImpl {

    public WallOfShards(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.supertype.add(SuperType.SNOW);
        this.subtype.add(SubType.WALL);
        this.power = new MageInt(1);
        this.toughness = new MageInt(8);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // Cumulative upkeep-An opponent gains 1 life.
        this.addAbility(new CumulativeUpkeepAbility(new GainLifeOpponentCost(1) ));
    }

    private WallOfShards(final WallOfShards card) {
        super(card);
    }

    @Override
    public WallOfShards copy() {
        return new WallOfShards(this);
    }
}
