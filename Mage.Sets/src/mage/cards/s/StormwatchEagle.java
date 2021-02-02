
package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.ReturnToHandSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public final class StormwatchEagle extends CardImpl {

    public StormwatchEagle(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.BIRD);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // Sacrifice a land: Return Stormwatch Eagle to its owner's hand.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, 
                new ReturnToHandSourceEffect(), 
                new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))));
    }

    private StormwatchEagle(final StormwatchEagle card) {
        super(card);
    }

    @Override
    public StormwatchEagle copy() {
        return new StormwatchEagle(this);
    }
}
