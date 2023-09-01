package mage.cards.h;

import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.CreaturesYouControlCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.StaticFilters;
import mage.game.permanent.token.HumanClericToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HanweirMilitiaCaptain extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            StaticFilters.FILTER_CONTROLLED_CREATURE, ComparisonType.MORE_THAN, 3
    );

    public HanweirMilitiaCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{1}{W}",
                "Westvale Cult Leader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "W"
        );
        this.getLeftHalfCard().setPT(2, 2);
        this.getRightHalfCard().setPT(0, 0);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        this.getLeftHalfCard().addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(), TargetController.YOU, false),
                condition, "At the beginning of your upkeep, if you control four or more creatures, transform {this}"
        ));

        // Westvale Cult Leader
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                Zone.ALL, new SetBasePowerToughnessSourceEffect(CreaturesYouControlCount.instance)
        ).addHint(CreaturesYouControlHint.instance));

        // At the beginning of your end step, create a 1/1 white and black Human Cleric creature token.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new HumanClericToken()), TargetController.YOU, false
        ));
    }

    private HanweirMilitiaCaptain(final HanweirMilitiaCaptain card) {
        super(card);
    }

    @Override
    public HanweirMilitiaCaptain copy() {
        return new HanweirMilitiaCaptain(this);
    }
}
