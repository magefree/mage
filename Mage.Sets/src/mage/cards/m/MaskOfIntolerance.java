package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.IntCompareCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MaskOfIntolerance extends CardImpl {

    private static final Condition condition = new MaskOfIntoleranceCondition();

    public MaskOfIntolerance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of each player's upkeep, if there are four or more basic land types among lands that player controls, Mask of Intolerance deals 3 damage to that player.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new BeginningOfUpkeepTriggeredAbility(
                new DamageTargetEffect(3), TargetController.ANY, false
        ), condition, "At the beginning of each player's upkeep, if there are four or more basic land types " +
                "among lands that player controls, {this} deals 3 damage to that player.").addHint(DomainHint.instance));
    }

    private MaskOfIntolerance(final MaskOfIntolerance card) {
        super(card);
    }

    @Override
    public MaskOfIntolerance copy() {
        return new MaskOfIntolerance(this);
    }
}

class MaskOfIntoleranceCondition extends IntCompareCondition {

    public MaskOfIntoleranceCondition() {
        super(ComparisonType.MORE_THAN, 3);
    }

    @Override
    protected int getInputValue(Game game, Ability source) {
        return DomainValue.ACTIVE.calculate(game, source, null);
    }
}
