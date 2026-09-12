package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.keyword.EmpowerJaceEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.StaticFilters;

/**
 *
 * @author muz
 */
public final class RepurposedEnforcer extends CardImpl {

    public RepurposedEnforcer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Whenever this creature attacks, empower Jace X, where X is the number of creatures you control.
        this.addAbility(new AttacksTriggeredAbility(new EmpowerJaceEffect(
            new PermanentsOnBattlefieldCount(StaticFilters.FILTER_PERMANENT_CREATURE_CONTROLLED)
        )));
    }

    private RepurposedEnforcer(final RepurposedEnforcer card) {
        super(card);
    }

    @Override
    public RepurposedEnforcer copy() {
        return new RepurposedEnforcer(this);
    }
}
