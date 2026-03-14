package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.LeavesBattlefieldAllTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.MyTurnCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.AllyToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SukiCourageousRescuer extends CardImpl {

    public SukiCourageousRescuer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.subtype.add(SubType.ALLY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(4);

        // Other creatures you control get +1/+0.
        this.addAbility(new SimpleStaticAbility(new BoostControlledEffect(
                1, 0, Duration.WhileOnBattlefield, true
        )));

        // Whenever another permanent you control leaves the battlefield during your turn, create a 1/1 white Ally creature token. This ability triggers only once each turn.
        this.addAbility(new LeavesBattlefieldAllTriggeredAbility(
                new CreateTokenEffect(new AllyToken()), StaticFilters.FILTER_CONTROLLED_ANOTHER_PERMANENT
        ).withTriggerCondition(MyTurnCondition.instance).setTriggersLimitEachTurn(1));
    }

    private SukiCourageousRescuer(final SukiCourageousRescuer card) {
        super(card);
    }

    @Override
    public SukiCourageousRescuer copy() {
        return new SukiCourageousRescuer(this);
    }
}
