package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.common.OpponentControlsMoreCondition;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.permanent.token.LanderToken;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class SunstarExpansionist extends CardImpl {

    private static final Condition condition = new OpponentControlsMoreCondition(StaticFilters.FILTER_LANDS);

    public SunstarExpansionist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // When this creature enters, if an opponent controls more lands than you, create a Lander token.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new LanderToken())).withInterveningIf(condition));

        // Landfall -- Whenever a land you control enters, this creature gets +1/+0 until end of turn.
        this.addAbility(new LandfallAbility(new BoostSourceEffect(1, 0, Duration.EndOfTurn)));
    }

    private SunstarExpansionist(final SunstarExpansionist card) {
        super(card);
    }

    @Override
    public SunstarExpansionist copy() {
        return new SunstarExpansionist(this);
    }
}
