package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.DamagedBatchEvent;
import mage.game.events.DamagedEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author weirddan455
 */
public final class RisonaAsariCommander extends CardImpl {

    public RisonaAsariCommander(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{R}{W}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.SAMURAI);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Risona, Asari Commander deals combat damage to a player, if it doesn't have an indestructible counter on it, put an indestructible counter on it.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new DealsCombatDamageToAPlayerTriggeredAbility(new AddCountersSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()), false),
                RisonaAsariCommanderCondition.instance,
                "Whenever {this} deals combat damage to a player, if it doesn't have an indestructible counter on it, put an indestructible counter on it."
        ));

        // Whenever combat damage is dealt to you, remove an indestructible counter from Risona.
        this.addAbility(new RisonaAsariCommanderTriggeredAbility());
    }

    private RisonaAsariCommander(final RisonaAsariCommander card) {
        super(card);
    }

    @Override
    public RisonaAsariCommander copy() {
        return new RisonaAsariCommander(this);
    }
}

class RisonaAsariCommanderTriggeredAbility extends TriggeredAbilityImpl {

    public RisonaAsariCommanderTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.INDESTRUCTIBLE.createInstance()));
    }

    private RisonaAsariCommanderTriggeredAbility(final RisonaAsariCommanderTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public RisonaAsariCommanderTriggeredAbility copy() {
        return new RisonaAsariCommanderTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DAMAGED_PLAYER_BATCH;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (controllerId == null) {
            return false;
        }
        if (!(event instanceof DamagedBatchEvent)) {
            return false;
        }
        DamagedBatchEvent batchEvent = (DamagedBatchEvent) event;
        for (DamagedEvent damageEvent : batchEvent.getEvents()) {
            if (damageEvent.isCombatDamage() && controllerId.equals(damageEvent.getTargetId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever combat damage is dealt to you, ";
    }
}

enum RisonaAsariCommanderCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        return permanent != null && permanent.getCounters(game).getCount(CounterType.INDESTRUCTIBLE) == 0;
    }
}
