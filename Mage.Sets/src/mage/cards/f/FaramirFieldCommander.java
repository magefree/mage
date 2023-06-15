package mage.cards.f;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.TheRingTemptsYouChooseAnotherTriggeredAbility;
import mage.abilities.condition.common.CreatureDiedControlledCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class FaramirFieldCommander extends CardImpl {

    public FaramirFieldCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SOLDIER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // At the beginning of your end step, if a creature died under your control this turn, draw a card.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new DrawCardSourceControllerEffect(1), TargetController.YOU,
                CreatureDiedControlledCondition.instance, false
        ).addHint(CreatureDiedControlledCondition.getHint()));

        // Whenever the Ring tempts you, if you chose a creature other than Faramir, Field Commander as your Ring-bearer, create a 1/1 white Human Soldier creature token.
        this.addAbility(new TheRingTemptsYouChooseAnotherTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())));
    }

    private FaramirFieldCommander(final FaramirFieldCommander card) {
        super(card);
    }

    @Override
    public FaramirFieldCommander copy() {
        return new FaramirFieldCommander(this);
    }
}

