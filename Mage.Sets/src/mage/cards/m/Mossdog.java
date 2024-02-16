package mage.cards.m;

import mage.MageInt;
import mage.abilities.common.BecomesTargetSourceTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

import java.util.UUID;

/**
 * @author xenohedron
 */
public final class Mossdog extends CardImpl {

    public Mossdog(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{G}");
        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.DOG);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever Mossdog becomes the target of a spell or ability an opponent controls, put a +1/+1 counter on Mossdog.
        this.addAbility(new BecomesTargetSourceTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()),
                StaticFilters.FILTER_SPELL_OR_ABILITY_OPPONENTS).withRuleTextReplacement(false));
    }

    private Mossdog(final Mossdog card) {
        super(card);
    }

    @Override
    public Mossdog copy() {
        return new Mossdog(this);
    }
}
