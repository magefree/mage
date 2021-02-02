
package mage.cards.g;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CycleOrDiscardControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;

/**
 *
 * @author ciaccona007
 */
public final class GrislySurvivor extends CardImpl {

    public GrislySurvivor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");
        
        this.subtype.add(SubType.MINOTAUR);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Whenever you cycle or discard a card, Grisly Survivor gets +2/+0 until end of turn.
        addAbility(new CycleOrDiscardControllerTriggeredAbility(new BoostSourceEffect(2,0,Duration.EndOfTurn)));
    }

    private GrislySurvivor(final GrislySurvivor card) {
        super(card);
    }

    @Override
    public GrislySurvivor copy() {
        return new GrislySurvivor(this);
    }
}
