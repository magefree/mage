

package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.common.ReturnToHandChosenControlledPermanentEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.common.FilterControlledArtifactPermanent;

/**
 *
 * @author Loki
 */
public final class Esperzoa extends CardImpl {

    
    public Esperzoa (UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT,CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.JELLYFISH);

        this.power = new MageInt(4);
        this.toughness = new MageInt(3);
        
        //Flying
        this.addAbility(FlyingAbility.getInstance());

        //At the beginning of your upkeep, return an artifact you control to its owner's hand.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new ReturnToHandChosenControlledPermanentEffect(new FilterControlledArtifactPermanent()), TargetController.YOU, false));
    }

    private Esperzoa(final Esperzoa card) {
        super(card);
    }

    @Override
    public Esperzoa copy() {
        return new Esperzoa(this);
    }

}
