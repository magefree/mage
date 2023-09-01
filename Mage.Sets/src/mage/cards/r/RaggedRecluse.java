package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.common.ControllerDiscardedThisTurnCondition;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.common.ControllerDiscardedHint;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SetTargetPointer;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.DiscardedCardWatcher;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaggedRecluse extends TransformingDoubleFacedCard {

    public RaggedRecluse(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.PEASANT}, "{1}{B}",
                "Odious Witch",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.WARLOCK}, "B"
        );
        this.getLeftHalfCard().setPT(2, 1);
        this.getRightHalfCard().setPT(3, 3);

        // At the beginning of your end step, if you discarded a card this turn, transform Ragged Recluse.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new TransformSourceEffect(), TargetController.YOU,
                ControllerDiscardedThisTurnCondition.instance, false
        ).addHint(ControllerDiscardedHint.instance), new DiscardedCardWatcher());

        // Odious Witch
        // Whenever Odious Witch attacks, defending player loses 1 life and you gain 1 life.
        Ability ability = new AttacksTriggeredAbility(
                new LoseLifeTargetEffect(1)
                        .setText("defending player loses 1 life"),
                false, null, SetTargetPointer.PLAYER
        );
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.getRightHalfCard().addAbility(ability);
    }

    private RaggedRecluse(final RaggedRecluse card) {
        super(card);
    }

    @Override
    public RaggedRecluse copy() {
        return new RaggedRecluse(this);
    }
}
