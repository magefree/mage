package mage.cards.e;

import mage.MageInt;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.condition.common.ControlsCreatureGreatestPowerCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.game.permanent.token.HumanSoldierToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class EomerOfTheRiddermark extends CardImpl {

    private static final Hint hint = new ConditionHint(
            ControlsCreatureGreatestPowerCondition.instance,
            "You control a creature with the greatest power"
    );

    public EomerOfTheRiddermark(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Eomer of the Riddermark attacks, if you control a creature with the greatest power among creatures on the battlefield, create a 1/1 white Human Soldier creature token.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new CreateTokenEffect(new HumanSoldierToken())),
                ControlsCreatureGreatestPowerCondition.instance, "Whenever {this} attacks, " +
                "if you control a creature with the greatest power among creatures on the battlefield, " +
                "create a 1/1 white Human Soldier creature token."
        ).addHint(hint));
    }

    private EomerOfTheRiddermark(final EomerOfTheRiddermark card) {
        super(card);
    }

    @Override
    public EomerOfTheRiddermark copy() {
        return new EomerOfTheRiddermark(this);
    }
}
