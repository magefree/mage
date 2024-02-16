
package mage.cards.w;

import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.PutCountersSourceCost;
import mage.abilities.effects.mana.BasicManaEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.mana.LimitedTimesPerTurnActivatedManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;

import java.util.UUID;

/**
 *
 * @author North
 */
public final class WallOfRoots extends CardImpl {

    public WallOfRoots(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(5);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // Put a -0/-1 counter on Wall of Roots: Add {G}. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedManaAbility(
                Zone.BATTLEFIELD, new BasicManaEffect(Mana.GreenMana(1)),
                new PutCountersSourceCost(CounterType.M0M1.createInstance())
        ));
    }

    private WallOfRoots(final WallOfRoots card) {
        super(card);
    }

    @Override
    public WallOfRoots copy() {
        return new WallOfRoots(this);
    }
}
