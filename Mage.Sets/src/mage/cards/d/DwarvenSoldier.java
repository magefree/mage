
package mage.cards.d;

import mage.MageInt;
import mage.abilities.common.BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.common.FilterCreaturePermanent;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public final class DwarvenSoldier extends CardImpl {

    public DwarvenSoldier(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Whenever Dwarven Soldier blocks or becomes blocked by one or more Orcs, Dwarven Soldier gets +0/+2 until end of turn.
        this.addAbility(new BlocksOrBecomesBlockedByOneOrMoreTriggeredAbility(new BoostSourceEffect(0, 2, Duration.EndOfTurn), new FilterCreaturePermanent(SubType.ORC, "Orcs"), false));
    }

    private DwarvenSoldier(final DwarvenSoldier card) {
        super(card);
    }

    @Override
    public DwarvenSoldier copy() {
        return new DwarvenSoldier(this);
    }
}
