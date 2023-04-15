package mage.cards.s;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.SimpleManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author nick.myers
 */
public final class ScorchedRuins extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("untapped lands");
    static {
        filter.add(TappedPredicate.UNTAPPED);
    }
    
    public ScorchedRuins(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.LAND},"");
        
        // If Scorched Ruins would enter the battlefield, sacrifice two untapped lands instead.
        // If you do, put Scorched Ruins onto the battlefield. If you don't, put it into its
        // owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(2,2,filter,false)))));
        // {tap}: Add {C}{C}{C}{C}
        this.addAbility(new SimpleManaAbility(Zone.BATTLEFIELD, Mana.ColorlessMana(4), new TapSourceCost()));
        
    }
    
    private ScorchedRuins(final ScorchedRuins card) {
        super(card);
    }
    
    @Override
    public ScorchedRuins copy() {
        return new ScorchedRuins(this);
    }
    
}
