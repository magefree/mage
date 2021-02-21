package mage.abilities.common;

import mage.abilities.effects.EntersBattlefieldEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.counters.CounterType;

/**
 * @author LevelX2
 */
public class PlaneswalkerEntersWithLoyaltyCountersAbility extends EntersBattlefieldAbility {

    private int startingLoyalty;

    public PlaneswalkerEntersWithLoyaltyCountersAbility(int loyalty) {
        super(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(loyalty)));
        startingLoyalty = loyalty;
        setRuleVisible(false);
    }

    public PlaneswalkerEntersWithLoyaltyCountersAbility(final PlaneswalkerEntersWithLoyaltyCountersAbility ability) {
        super(ability);
        startingLoyalty = ability.startingLoyalty;
    }

    public int getStartingLoyalty() {
        return startingLoyalty;
    }

    public void setStartingLoyalty(int startingLoyalty) {
        this.startingLoyalty = startingLoyalty;
        this.getEffects().clear();
        this.addEffect(new EntersBattlefieldEffect(new AddCountersSourceEffect(CounterType.LOYALTY.createInstance(startingLoyalty))));
    }

    @Override
    public PlaneswalkerEntersWithLoyaltyCountersAbility copy() {
        return new PlaneswalkerEntersWithLoyaltyCountersAbility(this);
    }
}
