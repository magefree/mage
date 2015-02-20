/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.weatherlight;

import java.util.UUID;
import mage.Mana;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.BasicManaEffect;
import mage.abilities.effects.common.EnterBattlefieldPayCostOrPutGraveyardEffect;
import mage.abilities.mana.BasicManaAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.TappedPredicate;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author nick.myers
 */
public class ScorchedRuins extends CardImpl {
    
    private static final FilterControlledLandPermanent filter = new FilterControlledLandPermanent("two untapped lands");
    static {
        filter.add(Predicates.not(new TappedPredicate()));
    }
    
    public ScorchedRuins(UUID ownerId) {
        super(ownerId, 166, "Scorched Ruins", Rarity.RARE, new CardType[]{CardType.LAND}, "");
        this.expansionSetCode = "WTH";
        
        // If Scorched Ruins would enter the battlefield, sacrifice two untapped lands instead.
        // If you do, put Scorched Ruins onto the battlefield. If you don't, put it into its
        // owner's graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new EnterBattlefieldPayCostOrPutGraveyardEffect(new SacrificeTargetCost(new TargetControlledPermanent(2,2,filter,false)))));
        // {tap}: Add {4} to your mana pool
        this.addAbility(new ScorchedRuinsManaAbility());
    }
    
    public ScorchedRuins(final ScorchedRuins card) {
        super(card);
    }
    
    @Override
    public ScorchedRuins copy() {
        return new ScorchedRuins(this);
    }
    
}

class ScorchedRuinsManaAbility extends BasicManaAbility {
    
    public ScorchedRuinsManaAbility() {
        super(new BasicManaEffect(new Mana(0,0,0,0,0,4,0)));
        this.netMana.add(new Mana(0,0,0,0,0,4,0));
    }    
    
    public ScorchedRuinsManaAbility(final ScorchedRuinsManaAbility ability) {
        super(ability);
    }
    
    @Override
    public ScorchedRuinsManaAbility copy() {
        return new ScorchedRuinsManaAbility(this);
    }
           
}
