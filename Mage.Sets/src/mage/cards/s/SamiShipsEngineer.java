package mage.cards.s;

import mage.MageInt;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.PermanentsOnTheBattlefieldCondition;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.hint.Hint;
import mage.abilities.hint.ValueHint;
import mage.abilities.triggers.BeginningOfEndStepTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.game.permanent.token.RobotToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SamiShipsEngineer extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("you control two or more tapped creatures");

    static {
        filter.add(TappedPredicate.TAPPED);
    }

    private static final Condition condition = new PermanentsOnTheBattlefieldCondition(filter);
    private static final Hint hint = new ValueHint(
            "Tapped creatures you control", new PermanentsOnBattlefieldCount(filter)
    );

    public SamiShipsEngineer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ARTIFICER);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // At the beginning of your end step, if you control two or more tapped creatures, create a tapped 2/2 colorless Robot artifact creature token.
        this.addAbility(new BeginningOfEndStepTriggeredAbility(
                new CreateTokenEffect(new RobotToken(), 1, true)
        ).withInterveningIf(condition).addHint(hint));
    }

    private SamiShipsEngineer(final SamiShipsEngineer card) {
        super(card);
    }

    @Override
    public SamiShipsEngineer copy() {
        return new SamiShipsEngineer(this);
    }
}
