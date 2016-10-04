/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.sets.urzasdestiny;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterEnchantmentPermanent;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author nick.myers
 */

public class AuraThief extends CardImpl {
    
    public AuraThief(UUID ownerId) {
        super(ownerId, 26, "Aura Thief", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{3}{U}");
        this.expansionSetCode = "UDS";
        this.subtype.add("Illusion");
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);
        
        // Flying
        this.addAbility(FlyingAbility.getInstance());
        
        // When Aura Thief dies, you gain control of all enchantments. You don't get
        // to move Auras.
        this.addAbility(new DiesTriggeredAbility(new AuraThiefDiesTriggeredEffect()));
    }
    
    public AuraThief(final AuraThief card) {
        super(card);
    }
    
    @Override
    public AuraThief copy() {
        return new AuraThief(this);
    }
    
}

class AuraThiefDiesTriggeredEffect extends OneShotEffect {
    
    public AuraThiefDiesTriggeredEffect() {
        super(Outcome.Benefit);
        this.staticText = "gain control of all enchantments. <i>(You don't get to move Auras.)</i>";
    }
    
    public AuraThiefDiesTriggeredEffect(final AuraThiefDiesTriggeredEffect effect) {
        super(effect);
    }
    
    @Override
    public AuraThiefDiesTriggeredEffect copy() {
        return new AuraThiefDiesTriggeredEffect(this);
    }
    
    @Override 
    public boolean apply(Game game, Ability source) {
        boolean ret = false;
        for(Permanent enchantment : game.getBattlefield().getActivePermanents(new FilterEnchantmentPermanent(), source.getControllerId(), source.getControllerId(), game)) {
            ContinuousEffect gainControl = new GainControlTargetEffect(Duration.EndOfGame);
            gainControl.setTargetPointer(new FixedTarget(enchantment.getId()));
            game.addEffect(gainControl, source);
            ret = true;
        }
        return ret;
    }
}
