/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;

import java.util.UUID;

/**
 *
 * @author nickmyers
 */
public class ArenaOfTheAncients extends CardImpl {
    
    private final static FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("legendary creatures");
    static {
        legendaryFilter.add(new SupertypePredicate(SuperType.LEGENDARY));
    }
    
    public ArenaOfTheAncients(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{3}");
        
        // Legendary creatures don't untap during their controllers' untap steps
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new DontUntapInControllersUntapStepAllEffect(Duration.WhileOnBattlefield, TargetController.ANY, legendaryFilter)));

        // When Arena of the Ancients enters the battlefield, tap all Legendary creatures
        Ability tapAllLegendsAbility = new EntersBattlefieldTriggeredAbility(new TapAllEffect(legendaryFilter));
        this.addAbility(tapAllLegendsAbility);
    }
    
    public ArenaOfTheAncients(final ArenaOfTheAncients card) {
        super(card);
    }

    @Override
    public ArenaOfTheAncients copy() {
        return new ArenaOfTheAncients(this);
    }
    
}
