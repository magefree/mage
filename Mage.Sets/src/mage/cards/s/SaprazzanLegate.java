
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class SaprazzanLegate extends CardImpl {

    private static final FilterPermanent filterMountain = new FilterPermanent();
    private static final FilterPermanent filterIsland = new FilterPermanent();

    static {
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
        filterIsland.add(SubType.ISLAND.getPredicate());
    }

    public SaprazzanLegate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.MERFOLK, SubType.SOLDIER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // If an opponent controls a Mountain and you control an Island, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Mountain and you control an Island",
                new OpponentControlsPermanentCondition(filterMountain),
                new PermanentsOnTheBattlefieldCondition(filterIsland));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
    }

    private SaprazzanLegate(final SaprazzanLegate card) {
        super(card);
    }

    @Override
    public SaprazzanLegate copy() {
        return new SaprazzanLegate(this);
    }
}
