package mage.cards.h;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.effects.common.continuous.SetBasePowerToughnessSourceEffect;
import mage.abilities.hint.common.CreaturesYouControlHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.permanent.token.HumanClericToken;

import java.util.UUID;

/**
 * @author fireshoes
 */
public final class HanweirMilitiaCaptain extends TransformingDoubleFacedCard {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control four or more creatures"),
            ComparisonType.MORE_THAN, 3
    );

    public HanweirMilitiaCaptain(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.SOLDIER}, "{1}{W}",
                "Westvale Cult Leader",
                new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.CLERIC}, "W"
        );

        // Hanweir Militia Captain
        this.getLeftHalfCard().setPT(2, 2);

        // At the beginning of your upkeep, if you control four or more creatures, transform Hanweir Militia Captain.
        this.getLeftHalfCard().addAbility(new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect())
                .withInterveningIf(condition).addHint(CreaturesYouControlHint.instance));

        // Westvale Cult Leader
        // Westvale Cult Leader's power and toughness are each equal to the number of creatures you control.
        this.getRightHalfCard().addAbility(new mage.abilities.common.SimpleStaticAbility(Zone.ALL,
                new SetBasePowerToughnessSourceEffect(mage.abilities.dynamicvalue.common.CreaturesYouControlCount.PLURAL))
                .addHint(CreaturesYouControlHint.instance));

        // At the beginning of your end step, create a 1/1 white and black Human Cleric creature token.
        this.getRightHalfCard().addAbility(new BeginningOfEndStepTriggeredAbility(new CreateTokenEffect(new HumanClericToken())));
    }

    private HanweirMilitiaCaptain(final HanweirMilitiaCaptain card) {
        super(card);
    }

    @Override
    public HanweirMilitiaCaptain copy() {
        return new HanweirMilitiaCaptain(this);
    }
}
