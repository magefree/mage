
package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.Mana;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.mana.DynamicManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterControlledCreaturePermanent;

/**
 * @author Loki
 */
public final class HarabazDruid extends CardImpl {

    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent("Allies you control");

    static {
        filter.add(SubType.ALLY.getPredicate());
    }

    public HarabazDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.subtype.add(SubType.ALLY);

        this.power = new MageInt(0);
        this.toughness = new MageInt(1);

        // {T}: Add X mana of any one color, where X is the number of Allies you control.
        this.addAbility(new DynamicManaAbility(new Mana(0, 0, 0, 0,0, 0,1, 0), new PermanentsOnBattlefieldCount(filter), new TapSourceCost(),
                "Add X mana of any one color, where X is the number of Allies you control", true));
    }

    private HarabazDruid(final HarabazDruid card) {
        super(card);
    }

    @Override
    public HarabazDruid copy() {
        return new HarabazDruid(this);
    }
}
