
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CrewsVehicleSourceTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author LevelX2
 */
public final class VeteranMotorist extends CardImpl {

    public VeteranMotorist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{R}{W}");
        this.subtype.add(SubType.DWARF);
        this.subtype.add(SubType.PILOT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // When Veteran Motorist enters the battlefield, scry 2.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new ScryEffect(2)));

        // Whenever Veteran Motorist crews a Vehicle, that Vehicle gets +1/+1 until end of turn.
        Effect effect = new BoostTargetEffect(1, 1, Duration.EndOfTurn);
        effect.setText("that Vehicle gets +1/+1 until end of turn");
        this.addAbility(new CrewsVehicleSourceTriggeredAbility(effect));
    }

    private VeteranMotorist(final VeteranMotorist card) {
        super(card);
    }

    @Override
    public VeteranMotorist copy() {
        return new VeteranMotorist(this);
    }
}
