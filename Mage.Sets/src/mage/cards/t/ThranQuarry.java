package mage.cards.t;

import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.mana.AnyColorManaAbility;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledCreaturePermanent;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class ThranQuarry extends CardImpl {

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(
            new FilterControlledCreaturePermanent("you control no creatures"), ComparisonType.EQUAL_TO, 0
    );

    public ThranQuarry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        // At the beginning of the end step, if you control no creatures, sacrifice Thran Quarry.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                TargetController.NEXT, new SacrificeSourceEffect(), false, condition
        ));

        // {tap}: Add one mana of any color.
        this.addAbility(new AnyColorManaAbility());
    }

    private ThranQuarry(final ThranQuarry card) {
        super(card);
    }

    @Override
    public ThranQuarry copy() {
        return new ThranQuarry(this);
    }
}
