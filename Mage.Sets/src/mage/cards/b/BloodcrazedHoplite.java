
package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterTargetEffect;
import mage.abilities.keyword.HeroicAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class BloodcrazedHoplite extends CardImpl {

    public BloodcrazedHoplite(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        this.subtype.add(SubType.HUMAN, SubType.SOLDIER);

        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // Heroic - Whenever you cast a spell that targets Bloodcrazed Hoplite, put a +1/+1 counter on it.
        this.addAbility(new HeroicAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(), false)));
        // Whenever a +1/+1 counter is put on Bloodcrazed Hoplite, remove a +1/+1 counter from target creature an opponent controls.
        Ability ability = new BloodcrazedHopliteTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent(StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE));
        this.addAbility(ability);
    }

    private BloodcrazedHoplite(final BloodcrazedHoplite card) {
        super(card);
    }

    @Override
    public BloodcrazedHoplite copy() {
        return new BloodcrazedHoplite(this);
    }
}

class BloodcrazedHopliteTriggeredAbility extends TriggeredAbilityImpl {

    public BloodcrazedHopliteTriggeredAbility() {
        super(Zone.ALL, new RemoveCounterTargetEffect(CounterType.P1P1.createInstance()), false);
    }

    public BloodcrazedHopliteTriggeredAbility(BloodcrazedHopliteTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.COUNTER_ADDED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return event.getTargetId().equals(this.getSourceId()) && event.getData().equals(CounterType.P1P1.getName());
    }

    @Override
    public BloodcrazedHopliteTriggeredAbility copy() {
        return new BloodcrazedHopliteTriggeredAbility(this);
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a +1/+1 counter is put on {this}, " ;
    }
}
