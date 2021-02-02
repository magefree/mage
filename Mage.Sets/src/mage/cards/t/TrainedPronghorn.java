
package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.DiscardCardCost;
import mage.abilities.effects.common.PreventAllDamageToSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;

/**
 *
 * @author tcontis
 */
public final class TrainedPronghorn extends CardImpl {

    public TrainedPronghorn(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{W}");
        this.subtype.add(SubType.ANTELOPE);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);
        this.toughness = new MageInt(1);

        //Discard a card: Prevent all damage that would be dealt to Trained Pronghorn this turn
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new PreventAllDamageToSourceEffect(Duration.EndOfTurn), new DiscardCardCost()));

    }

    private TrainedPronghorn(final TrainedPronghorn card) {
        super(card);
    }

    @Override
    public TrainedPronghorn copy() {
        return new TrainedPronghorn(this);
    }
}


