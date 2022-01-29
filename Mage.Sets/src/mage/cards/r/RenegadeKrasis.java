
package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersAllEffect;
import mage.abilities.keyword.EvolveAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;

public final class RenegadeKrasis extends CardImpl {

    public RenegadeKrasis(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{1}{G}{G}");
        this.subtype.add(SubType.BEAST);
        this.subtype.add(SubType.MUTANT);

        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // Evolve
        this.addAbility(new EvolveAbility());

        // Whenever Renegade Krasis evolves, put a +1/+1 counter on each other creature you control with a +1/+1 counter on it.
        this.addAbility(new RenegadeKrasisTriggeredAbility());
    }

    private RenegadeKrasis(final RenegadeKrasis card) {
        super(card);
    }

    @Override
    public RenegadeKrasis copy() {
        return new RenegadeKrasis(this);
    }

}

class RenegadeKrasisTriggeredAbility extends TriggeredAbilityImpl {

    public RenegadeKrasisTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersAllEffect(CounterType.P1P1.createInstance(1), StaticFilters.FILTER_OTHER_CONTROLLED_CREATURE_P1P1), false);
    }

    public RenegadeKrasisTriggeredAbility(final RenegadeKrasisTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RenegadeKrasisTriggeredAbility copy() {
        return new RenegadeKrasisTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EVOLVED_CREATURE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(getSourceId());
    }

    @Override
    public String getRule() {
        return "Whenever {this} evolves, put a +1/+1 counter on each other creature you control with a +1/+1 counter on it.";
    }
}
