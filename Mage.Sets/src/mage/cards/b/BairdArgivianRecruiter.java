package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.PowerGreaterThanBasePowerPredicate;
import mage.game.permanent.token.SoldierToken;

import java.util.UUID;

/**
 *
 * @author awjackson
 */
public final class BairdArgivianRecruiter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("you control a creature with power greater than its base power");

    static {
        filter.add(PowerGreaterThanBasePowerPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a creature with power greater than its base power");

    public BairdArgivianRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // At the beginning of your end step, if you control a creature with power greater than its base power,
        // create a 1/1 white Soldier creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new SoldierToken()), TargetController.YOU, condition, false
        ).addHint(hint));
    }

    private BairdArgivianRecruiter(final BairdArgivianRecruiter card) {
        super(card);
    }

    @Override
    public BairdArgivianRecruiter copy() {
        return new BairdArgivianRecruiter(this);
    }
}
