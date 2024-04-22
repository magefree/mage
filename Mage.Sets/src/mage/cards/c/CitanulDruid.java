
package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.SpellCastOpponentTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

/**
 *
 * @author ilcartographer
 */
public final class CitanulDruid extends CardImpl {

    public CitanulDruid(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.DRUID);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Whenever an opponent casts an artifact spell, put a +1/+1 counter on Citanul Druid.
        this.addAbility(new SpellCastOpponentTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), StaticFilters.FILTER_SPELL_AN_ARTIFACT, false));
    }

    private CitanulDruid(final CitanulDruid card) {
        super(card);
    }

    @Override
    public CitanulDruid copy() {
        return new CitanulDruid(this);
    }
}
