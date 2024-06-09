
package mage.cards.n;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.common.CardsInControllerGraveyardCount;
import mage.abilities.effects.common.continuous.SetBasePowerSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;

/**
 *
 * @author fireshoes
 */
public final class NightstalkerEngine extends CardImpl {

    public NightstalkerEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{B}");
        this.subtype.add(SubType.NIGHTSTALKER);
        this.power = new MageInt(0);
        this.toughness = new MageInt(3);

        // Nightstalker Engine's power is equal to the number of creature cards in your graveyard.
        CardsInControllerGraveyardCount count = new CardsInControllerGraveyardCount(new FilterCreatureCard("creature cards"));
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new SetBasePowerSourceEffect(count)));
    }

    private NightstalkerEngine(final NightstalkerEngine card) {
        super(card);
    }

    @Override
    public NightstalkerEngine copy() {
        return new NightstalkerEngine(this);
    }
}
