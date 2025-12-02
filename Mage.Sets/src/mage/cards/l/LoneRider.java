package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.YouGainedLifeCondition;
import mage.abilities.dynamicvalue.common.ControllerGainedLifeCount;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
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

    public LoneRider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.KNIGHT}, "{1}{W}",
                "It That Rides as One",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.ELDRAZI, SubType.HORROR}, ""
        );

        // Lone Rider
        this.getLeftHalfCard().setPT(1, 1);

        // First strike
        this.getLeftHalfCard().addAbility(FirstStrikeAbility.getInstance());

        // Lifelink
        this.getLeftHalfCard().addAbility(LifelinkAbility.getInstance());

        // At the beginning of the end step, if you gained 3 or more life this turn, transform Lone Rider.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new TransformSourceEffect(), false)
                .withInterveningIf(condition)
                .addHint(ControllerGainedLifeCount.getHint()
                );
        ability.addWatcher(new PlayerGainedLifeWatcher());
        this.getLeftHalfCard().addAbility(ability);

        // It That Rides as One
        this.getRightHalfCard().setPT(4, 4);

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
