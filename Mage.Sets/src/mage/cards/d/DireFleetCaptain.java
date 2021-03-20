
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterAttackingCreature;
import mage.filter.predicate.mageobject.AnotherPredicate;

/**
 *
 * @author TheElk801
 */
public final class DireFleetCaptain extends CardImpl {

    private static final FilterAttackingCreature filter = new FilterAttackingCreature("other attacking Pirate");

    static {
        filter.add(SubType.PIRATE.getPredicate());
        filter.add(AnotherPredicate.instance);
    }

    public DireFleetCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}{R}");

        this.subtype.add(SubType.ORC);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Dire Fleet Captain attacks, it gets +1/+1 until end of turn for each other attacking Pirate.
        PermanentsOnBattlefieldCount value = new PermanentsOnBattlefieldCount(filter);
        this.addAbility(new AttacksTriggeredAbility(new BoostSourceEffect(value, value, Duration.EndOfTurn, true)
                .setText("it gets +1/+1 until end of turn for each other attacking Pirate"), false));
    }

    private DireFleetCaptain(final DireFleetCaptain card) {
        super(card);
    }

    @Override
    public DireFleetCaptain copy() {
        return new DireFleetCaptain(this);
    }
}
