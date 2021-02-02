
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.CrewsVehicleSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class GearshiftAce extends CardImpl {

    public GearshiftAce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // First Strike
        this.addAbility(FirstStrikeAbility.getInstance());
        // Whenever Gearshift Ace crews a Vehicle, that Vehicle gains first strike until the end of turn.
        this.addAbility(new CrewsVehicleSourceTriggeredAbility(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance(), Duration.EndOfTurn,
                "that Vehicle gains first strike until end of turn")));
    }

    private GearshiftAce(final GearshiftAce card) {
        super(card);
    }

    @Override
    public GearshiftAce copy() {
        return new GearshiftAce(this);
    }
}
