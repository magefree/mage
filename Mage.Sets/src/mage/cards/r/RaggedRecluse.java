package mage.cards.r;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.ControllerDiscardedHint;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaggedRecluse extends CardImpl {

    public RaggedRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PEASANT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);
        this.secondSideCardClazz = mage.cards.o.OdiousWitch.class;

        // At the beginning of your end step, if you discarded a card this turn, transform Ragged Recluse.
        this.addAbility(new TransformAbility());
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                Zone.BATTLEFIELD, new TransformSourceEffect(), TargetController.YOU,
                ControllerDiscardedThisTurnCondition.instance, false
        ).addHint(ControllerDiscardedHint.instance), new DiscardedCardWatcher());
    }

    private RaggedRecluse(final RaggedRecluse card) {
        super(card);
    }

    @Override
    public RaggedRecluse copy() {
        return new RaggedRecluse(this);
    }
}
