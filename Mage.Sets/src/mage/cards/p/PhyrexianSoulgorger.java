
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.keyword.CumulativeUpkeepAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import static mage.filter.StaticFilters.FILTER_CONTROLLED_CREATURE_SHORT_TEXT;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class PhyrexianSoulgorger extends CardImpl {

    public PhyrexianSoulgorger(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        addSuperType(SuperType.SNOW);
        this.subtype.add(SubType.PHYREXIAN);
        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(8);
        this.toughness = new MageInt(8);

        // Cumulative upkeep-Sacrifice a creature.
        this.addAbility(new CumulativeUpkeepAbility(
                new SacrificeTargetCost(new TargetControlledCreaturePermanent(FILTER_CONTROLLED_CREATURE_SHORT_TEXT))));
    }

    private PhyrexianSoulgorger(final PhyrexianSoulgorger card) {
        super(card);
    }

    @Override
    public PhyrexianSoulgorger copy() {
        return new PhyrexianSoulgorger(this);
    }
}
