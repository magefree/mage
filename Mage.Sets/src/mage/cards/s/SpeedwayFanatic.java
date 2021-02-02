
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.CrewsVehicleSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class SpeedwayFanatic extends CardImpl {

    public SpeedwayFanatic(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{R}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Speedway Fanatic crews a Vehicle, that Vehicle gains haste until end of turn.
        this.addAbility(new CrewsVehicleSourceTriggeredAbility(new GainAbilityTargetEffect(HasteAbility.getInstance(), Duration.EndOfTurn,
                "that Vehicle gains haste until end of turn")));
    }

    private SpeedwayFanatic(final SpeedwayFanatic card) {
        super(card);
    }

    @Override
    public SpeedwayFanatic copy() {
        return new SpeedwayFanatic(this);
    }
}

