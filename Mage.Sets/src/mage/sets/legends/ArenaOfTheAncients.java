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
import mage.abilities.condition.common.SourceOnBattelfieldCondition;
import mage.abilities.decorator.ConditionalContinuousRuleModifyingEffect;
import mage.abilities.effects.ContinuousRuleModifiyingEffect;
import mage.abilities.effects.ContinuousRuleModifiyingEffectImpl;
import mage.abilities.effects.common.DontUntapInControllersUntapStepAllEffect;
import mage.abilities.effects.common.DontUntapInControllersUntapStepTargetEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.mageobject.SupertypePredicate;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author nickmyers
 */
public class ArenaOfTheAncients extends CardImpl {
    
    final static FilterCreaturePermanent legendaryFilter = new FilterCreaturePermanent("all legendary creatures");
    static {
        legendaryFilter.add(new SupertypePredicate("Legendary"));
    }
    
    public ArenaOfTheAncients(UUID ownerId) {
        super(ownerId, 215, "Arena Of The Ancients", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
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
