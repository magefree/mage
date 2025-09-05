package mage.cards.i;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class IrohFirebendingInstructor extends CardImpl {

    public IrohFirebendingInstructor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.NOBLE);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Iroh attacks, attacking creatures get +1/+1 until end of turn.
        this.addAbility(new AttacksTriggeredAbility(new BoostAllEffect(
                1, 1, Duration.EndOfTurn, StaticFilters.FILTER_ATTACKING_CREATURES, false
        )));
    }

    private IrohFirebendingInstructor(final IrohFirebendingInstructor card) {
        super(card);
    }

    @Override
    public IrohFirebendingInstructor copy() {
        return new IrohFirebendingInstructor(this);
    }
}
