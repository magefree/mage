package mage.cards.c;

import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ClericOfChillDepths extends CardImpl {

    public ClericOfChillDepths(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");

        this.subtype.add(SubType.MERFOLK);
        this.subtype.add(SubType.CLERIC);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Whenever Cleric of Chill Depths blocks a creature, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new BlocksCreatureTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect("that creature")
        ));
    }

    private ClericOfChillDepths(final ClericOfChillDepths card) {
        super(card);
    }

    @Override
    public ClericOfChillDepths copy() {
        return new ClericOfChillDepths(this);
    }
}
