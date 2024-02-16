package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BlocksCreatureTriggeredAbility;
import mage.abilities.effects.common.DontUntapInControllersNextUntapStepTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author TheElk801
 */
public final class LabyrinthMinotaur extends CardImpl {

    public LabyrinthMinotaur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{U}");

        this.subtype.add(SubType.MINOTAUR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Whenever Labyrinth Minotaur blocks a creature, that creature doesn't untap during its controller's next untap step.
        this.addAbility(new BlocksCreatureTriggeredAbility(
                new DontUntapInControllersNextUntapStepTargetEffect("that creature")
        ));
    }

    private LabyrinthMinotaur(final LabyrinthMinotaur card) {
        super(card);
    }

    @Override
    public LabyrinthMinotaur copy() {
        return new LabyrinthMinotaur(this);
    }
}
