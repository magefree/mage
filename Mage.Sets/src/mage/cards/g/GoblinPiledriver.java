
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author jonubuu
 */
public final class GoblinPiledriver extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Goblin");

    static {
        filter.add(SubType.GOBLIN.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    private static final DynamicValue xValue = new PermanentsOnBattlefieldCount(filter, 2);

    public GoblinPiledriver(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(1);
        this.toughness = new MageInt(2);

        // Protection from blue
        this.addAbility(ProtectionAbility.from(ObjectColor.BLUE));
        // Whenever Goblin Piledriver attacks, it gets +2/+0 until end of turn for each other attacking Goblin.
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(xValue, StaticValue.get(0), Duration.EndOfTurn, true, "it"), false));
    }

    private GoblinPiledriver(final GoblinPiledriver card) {
        super(card);
    }

    @Override
    public GoblinPiledriver copy() {
        return new GoblinPiledriver(this);
    }
}
