package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsDamageToACreatureTriggeredAbility;
import mage.abilities.common.PutCounterOnPermanentTriggeredAbility;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeOpponentsEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.ReachAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 *
 * @author fireshoes
 */
public final class ObeliskSpider extends CardImpl {

    public ObeliskSpider(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.subtype.add(SubType.SPIDER);
        this.power = new MageInt(1);
        this.toughness = new MageInt(4);

        // Reach
        this.addAbility(ReachAbility.getInstance());

        // Whenever Obelisk Spider deals combat damage to a creature, put a -1/-1 counter on that creature.
        this.addAbility(new DealsDamageToACreatureTriggeredAbility(new AddCountersTargetEffect(CounterType.M1M1.createInstance(1)), true, false, true));

        // Whenever you put one or more -1/-1 counters on a creature, each opponent loses 1 life and you gain 1 life.
        Ability ability = new PutCounterOnPermanentTriggeredAbility(new LoseLifeOpponentsEffect(1),
                CounterType.M1M1, StaticFilters.FILTER_PERMANENT_CREATURE);
        ability.addEffect(new GainLifeEffect(1).concatBy("and"));
        this.addAbility(ability);
    }

    private ObeliskSpider(final ObeliskSpider card) {
        super(card);
    }

    @Override
    public ObeliskSpider copy() {
        return new ObeliskSpider(this);
    }
}
