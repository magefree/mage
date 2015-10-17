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

    public PlanswalkerEntersWithLoyalityCountersAbility(int loyality) {
        super(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(loyality)));
        setRuleVisible(false);
    }

    public PlanswalkerEntersWithLoyalityCountersAbility(final PlanswalkerEntersWithLoyalityCountersAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldAbility copy() {
        return new PlanswalkerEntersWithLoyalityCountersAbility(this);
    }
}
