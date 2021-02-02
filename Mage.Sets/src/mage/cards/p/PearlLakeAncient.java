
package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.CantBeCounteredSourceAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ReturnToHandChosenControlledPermanentCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author emerald000
 */
public final class PearlLakeAncient extends CardImpl {

    public PearlLakeAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{5}{U}{U}");
        this.subtype.add(SubType.LEVIATHAN);

        this.power = new MageInt(6);
        this.toughness = new MageInt(7);

        // Flash
        this.addAbility(FlashAbility.getInstance());
        
        // Pearl Lake Ancient can't be countered.
        this.addAbility(new CantBeCounteredSourceAbility());
        
        // Prowess
        this.addAbility(new ProwessAbility());
        
        // Return three lands you control to their owner's hand: Return Pearl Lake Ancient to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandSourceEffect(true), 
                new ReturnToHandChosenControlledPermanentCost(new TargetControlledPermanent(3, 3, new FilterControlledLandPermanent("lands"), true))));
    }

    private PearlLakeAncient(final PearlLakeAncient card) {
        super(card);
    }

    @Override
    public PearlLakeAncient copy() {
        return new PearlLakeAncient(this);
    }
}
