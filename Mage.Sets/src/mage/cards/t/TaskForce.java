

package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BecomesTargetTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author Backfir3
 */
public final class TaskForce extends CardImpl {

    public TaskForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.REBEL);

        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

		// Whenever Task Force becomes the target of a spell or ability, it gets +0/+3 until end of turn.
        this.addAbility(new BecomesTargetTriggeredAbility(new BoostSourceEffect(0, 3, Duration.EndOfTurn)));
    }

    private TaskForce(final TaskForce card) {
        super(card);
    }

    @Override
    public TaskForce copy() {
        return new TaskForce(this);
    }
}
