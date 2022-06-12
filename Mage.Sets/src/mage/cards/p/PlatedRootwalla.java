
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;

/**
 *
 * @author nigelzor
 */
public final class PlatedRootwalla extends CardImpl {

    public PlatedRootwalla(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{G}");
        this.subtype.add(SubType.LIZARD);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // {2}{G}: Plated Rootwalla gets +3/+3 until end of turn. Activate this ability only once each turn.
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new BoostSourceEffect(3, 3, Duration.EndOfTurn), new ManaCostsImpl<>("{2}{G}")));
    }

    private PlatedRootwalla(final PlatedRootwalla card) {
        super(card);
    }

    @Override
    public PlatedRootwalla copy() {
        return new PlatedRootwalla(this);
    }
}
