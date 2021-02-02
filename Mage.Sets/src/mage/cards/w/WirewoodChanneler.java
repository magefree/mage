
package mage.cards.w;

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
import mage.filter.FilterPermanent;

/**
 *
 * @author Plopman
 */
public final class WirewoodChanneler extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("Elves");

    static {
        filter.add(SubType.ELF.getPredicate());
    }

    public WirewoodChanneler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{G}");
        this.subtype.add(SubType.ELF);
        this.subtype.add(SubType.DRUID);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {T}: Add X mana of any one color, where X is the number of Elves on the battlefield.
        DynamicManaAbility ability = new DynamicManaAbility(new Mana(0, 0, 0, 0,0, 0,1, 0), new PermanentsOnBattlefieldCount(filter), new TapSourceCost(),
                "Add X mana of any one color, where X is the number of Elves on the battlefield", true);
        this.addAbility(ability);
    }

    private WirewoodChanneler(final WirewoodChanneler card) {
        super(card);
    }

    @Override
    public WirewoodChanneler copy() {
        return new WirewoodChanneler(this);
    }
}
