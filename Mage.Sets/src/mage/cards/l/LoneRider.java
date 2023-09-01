package mage.cards.l;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.watchers.common.PlayerGainedLifeWatcher;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class LoneRider extends TransformingDoubleFacedCard {

    private static final Condition condition = new YouGainedLifeCondition(ComparisonType.MORE_THAN, 2);
    private static final Hint hint = new ConditionHint(condition, "You gained 3 or more life this turn");

    public LoneRider(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{W}",
                "It That Rides as One",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HORROR}, ""
        );
        this.getLeftHalfCard().setPT(1, 1);
        this.getRightHalfCard().setPT(4, 4);

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        this.getLeftHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new TransformSourceEffect(), TargetController.NEXT, condition, false
        ).addHint(hint), new PlayerGainedLifeWatcher());

        // It That Rides as One
        // First strike
        this.getRightHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Trample
        this.getRightHalfCard().addAbility(TrampleAbility.getInstance());

        // Lifelink
        this.getRightHalfCard().addAbility(LifelinkAbility.getInstance());
    }

    private LoneRider(final LoneRider card) {
        super(card);
    }

    @Override
    public LoneRider copy() {
        return new LoneRider(this);
    }
}
