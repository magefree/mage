
package mage.cards.k;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.condition.CompoundCondition;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsPermanentCondition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.costs.AlternativeCostSourceAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.FilterPermanent;

/**
 *
 * @author fireshoes
 */
public final class KyrenLegate extends CardImpl {

    private static final FilterPermanent filterPlains = new FilterPermanent();
    private static final FilterPermanent filterMountain = new FilterPermanent();

    static {
        filterPlains.add(SubType.PLAINS.getPredicate());
        filterMountain.add(SubType.MOUNTAIN.getPredicate());
    }

    public KyrenLegate(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // If an opponent controls a Plains and you control a Mountain, you may cast this spell without paying its mana cost.
        Condition condition = new CompoundCondition("If an opponent controls a Plains and you control a Mountain",
                new OpponentControlsPermanentCondition(filterPlains),
                new PermanentsOnTheBattlefieldCondition(filterMountain));
        this.addAbility(new AlternativeCostSourceAbility(null, condition));
    }

    private KyrenLegate(final KyrenLegate card) {
        super(card);
    }

    @Override
    public KyrenLegate copy() {
        return new KyrenLegate(this);
    }
}
