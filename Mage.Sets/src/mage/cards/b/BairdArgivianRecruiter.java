package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
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
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.token.SoldierToken;

/**
 *
 * @author awjackson
 */
public final class BairdArgivianRecruiter extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("you control a creature with power greater than its base power");

    static {
        filter.add(BairdArgivianRecruiterPredicate.instance);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ConditionHint(condition, "You control a creature with power greater than its base power");

    public BairdArgivianRecruiter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.addSuperType(SuperType.LEGENDARY);
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

enum BairdArgivianRecruiterPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        MageInt power = input.getPower();
        return power.getValue() > power.getModifiedBaseValue();
    }
}
