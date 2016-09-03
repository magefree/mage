/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mage.abilities.common;

import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;

/**
 *
 * @author LevelX2
 */
public class PlanswalkerEntersWithLoyalityCountersAbility extends EntersBattlefieldAbility {

    private final int startingLoyalty; 
    
    public PlanswalkerEntersWithLoyalityCountersAbility(int loyalty) {
        super(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(loyalty)));
        startingLoyalty = loyalty;
        setRuleVisible(false);
    }

    public PlanswalkerEntersWithLoyalityCountersAbility(final PlanswalkerEntersWithLoyalityCountersAbility ability) {
        super(ability);
        startingLoyalty = ability.startingLoyalty;
    }
    
    public int getStartingLoyalty() {
        return startingLoyalty;
    }

    @Override
    public PlanswalkerEntersWithLoyalityCountersAbility copy() {
        return new PlanswalkerEntersWithLoyalityCountersAbility(this);
    }
}
