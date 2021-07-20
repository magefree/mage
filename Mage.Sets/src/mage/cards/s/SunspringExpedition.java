package mage.cards.s;

import mage.abilities.ActivatedAbility;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.CompositeCost;
import mage.abilities.costs.common.RemoveCountersSourceCost;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.counters.CounterType;

import java.util.UUID;

/**
 * @author BetaSteward_at_googlemail.com
 */
public final class SunspringExpedition extends CardImpl {

    public SunspringExpedition(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{W}");


        this.addAbility(new LandfallAbility(new AddCountersSourceEffect(CounterType.QUEST.createInstance()), true));
        ActivatedAbility ability = new SimpleActivatedAbility(
                new GainLifeEffect(8), new CompositeCost(
                new RemoveCountersSourceCost(CounterType.QUEST.createInstance(3)),
                new SacrificeSourceCost(), "Remove three quest counters from {this} and sacrifice it"
        ));
        this.addAbility(ability);
    }

    private SunspringExpedition(final SunspringExpedition card) {
        super(card);
    }

    @Override
    public SunspringExpedition copy() {
        return new SunspringExpedition(this);
    }
}
