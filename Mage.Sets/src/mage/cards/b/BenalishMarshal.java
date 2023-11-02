package mage.cards.b;

import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BoostControlledEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

public final class BenalishMarshal extends CardImpl {

    public BenalishMarshal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{W}{W}");
        subtype.add(SubType.HUMAN);
        subtype.add(SubType.KNIGHT);
        power = new MageInt(3);
        toughness = new MageInt(3);

        // Other creatures you control get +1/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(1, 1, Duration.WhileOnBattlefield, true)));

    }


    private BenalishMarshal(final BenalishMarshal benalishMarshall) {
        super(benalishMarshall);
    }

    @Override
    public BenalishMarshal copy() {
        return new BenalishMarshal(this);
    }
}
