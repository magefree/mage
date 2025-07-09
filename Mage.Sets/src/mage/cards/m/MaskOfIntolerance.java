package mage.cards.m;

import mage.abilities.Ability;
import mage.abilities.condition.Condition;
import mage.abilities.dynamicvalue.common.DomainValue;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.hint.common.DomainHint;
import mage.abilities.triggers.BeginningOfUpkeepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.game.Game;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class MaskOfIntolerance extends CardImpl {

    public MaskOfIntolerance(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");

        // At the beginning of each player's upkeep, if there are four or more basic land types among lands that player controls, Mask of Intolerance deals 3 damage to that player.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(
                TargetController.EACH_PLAYER, new DamageTargetEffect(3, true, "that player"), false
        ).withInterveningIf(MaskOfIntoleranceCondition.instance).addHint(DomainHint.instance));
    }

    private MaskOfIntolerance(final MaskOfIntolerance card) {
        super(card);
    }

    @Override
    public MaskOfIntolerance copy() {
        return new MaskOfIntolerance(this);
    }
}

enum MaskOfIntoleranceCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        return DomainValue.ACTIVE.calculate(game, source, null) >= 4;
    }

    @Override
    public String toString() {
        return "there are four or more basic land types among lands that player controls";
    }
}
