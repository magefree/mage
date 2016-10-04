/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.legends;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

/**
 *
 * @author nickmyers
 */
public class ArenaOfTheAncients extends CardImpl {
    
    final static FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("legendary creatures");
    static {
        legendaryFilter.add(new SupertypePredicate("Legendary"));
    }
    
    public ArenaOfTheAncients(UUID ownerId) {
        super(ownerId, 215, "Arena of the Ancients", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "LEG";
        
        // When Arena of the Ancients enters the battlefield, tap all Legendary creatures
        Ability tapAllLegendsAbility = new EntersBattlefieldTriggeredAbility(new TapAllEffect(legendaryFilter));
        this.addAbility(tapAllLegendsAbility);
        
        // Legendary creatures don't untap during their controllers' untap steps
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, legendaryFilter)));
        
    }
    
    public ArenaOfTheAncients(final ArenaOfTheAncients card) {
        super(card);
    }

    @Override
    public ArenaOfTheAncients copy() {
        return new ArenaOfTheAncients(this);
    }
    
}
