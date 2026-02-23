package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HeirloomAuntie extends CardImpl {

    public HeirloomAuntie(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.WARLOCK);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // This creature enters with two -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(2)));

        // Whenever another creature you control dies, surveil 1, then remove a -1/-1 counter from this creature.
        Ability ability = new DiesCreatureTriggeredAbility(
                new SurveilEffect(1, false), false,
                StaticFilters.FILTER_ANOTHER_CREATURE_YOU_CONTROL
        );
        ability.addEffect(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()).concatBy(", then"));
        this.addAbility(ability);
    }

    private HeirloomAuntie(final HeirloomAuntie card) {
        super(card);
    }

    @Override
    public HeirloomAuntie copy() {
        return new HeirloomAuntie(this);
    }
}
