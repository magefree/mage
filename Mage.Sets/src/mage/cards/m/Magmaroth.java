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
import mage.filter.FilterSpell;
import mage.filter.predicate.Predicates;

public final class Magmaroth extends CardImpl{

    private static final FilterSpell filterNonCreature = new FilterSpell("a noncreature spell");

    static {
        filterNonCreature.add(Predicates.not(CardType.CREATURE.getPredicate()));
    }

    public Magmaroth(UUID ownerId, CardSetInfo cardSetInfo){
        super(ownerId, cardSetInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        subtype.add(SubType.ELEMENTAL);
        power = new MageInt(5);
        toughness = new MageInt(5);

        // At the beginning of your upkeep, put a -1/-1 counter on Magmaroth
        addAbility(new BeginningOfUpkeepTriggeredAbility(new AddCountersSourceEffect(CounterType.M1M1.createInstance()), TargetController.YOU, false));

        // Whenever you cast a noncreature spell, remove a -1/-1 counter from Magmaroth
        addAbility(new SpellCastControllerTriggeredAbility(new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()), filterNonCreature, false));

    }

    public Magmaroth(final Magmaroth magmaroth){
        super(magmaroth);
    }

    public Magmaroth copy(){
        return new Magmaroth(this);
    }
}
