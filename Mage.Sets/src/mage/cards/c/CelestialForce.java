
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;

/**
 *
 * @author fireshoes
 */
public final class CelestialForce extends CardImpl {

    public CelestialForce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{W}{W}{W}");
        this.subtype.add(SubType.ELEMENTAL);

        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // At the beginning of each upkeep, you gain 3 life.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new GainLifeEffect(3), TargetController.ANY, false));
    }

    private CelestialForce(final CelestialForce card) {
        super(card);
    }

    @Override
    public CelestialForce copy() {
        return new CelestialForce(this);
    }
}
