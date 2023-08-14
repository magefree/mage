package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.counters.CounterType;
import mage.filter.StaticFilters;

public final class Magmaroth extends CardImpl{

    public Magmaroth(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(5);
        toughness = new MageInt(5);

        // At the beginning of your upkeep, put a -1/-1 counter on Magmaroth
        addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance()), TargetController.YOU, false));

        // Whenever you cast a noncreature spell, remove a -1/-1 counter from Magmaroth
        addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false));

    }

    public Magmaroth(final Magmaroth magmaroth){
        super(magmaroth);
    }

    public Magmaroth copy(){
        return new Magmaroth(this);
    }
}
