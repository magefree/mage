
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

/**
 *
 * @author noxx (Angel of Jubilation), cbt33
 */
public final class BlessedOrator extends CardImpl {

    public BlessedOrator(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{W}");
        this.subtype.add(SubType.HUMAN, SubType.CLERIC);

        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Other creatures you control get +0/+1.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostControlledEffect(0, 1, Duration.WhileOnBattlefield, true)));
    }

    private BlessedOrator(final BlessedOrator card) {
        super(card);
    }

    @Override
    public BlessedOrator copy() {
        return new BlessedOrator(this);
    }
}
