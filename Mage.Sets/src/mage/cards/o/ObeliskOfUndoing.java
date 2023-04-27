
package mage.cards.o;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.target.common.TargetControlledPermanent;


/**
 *
 * @author MarcoMarin
 */
public final class ObeliskOfUndoing extends CardImpl {
    
    private static final FilterControlledPermanent filter = new FilterControlledPermanent();
        
    static {
        filter.add(TargetController.YOU.getOwnerPredicate());
    }
    
    public ObeliskOfUndoing(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{1}");

        // {6}, {tap}: Return target permanent you both own and control to your hand.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new ReturnToHandTargetEffect(), new ManaCostsImpl<>("{6}"));
        ability.addCost(new TapSourceCost());        
        ability.addTarget(new TargetControlledPermanent(filter));
        this.addAbility(ability);
    }

    private ObeliskOfUndoing(final ObeliskOfUndoing card) {
        super(card);
    }

    @Override
    public ObeliskOfUndoing copy() {
        return new ObeliskOfUndoing(this);
    }
}
