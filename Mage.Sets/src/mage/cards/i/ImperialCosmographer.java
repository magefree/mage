package mage.cards.i;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class ImperialCosmographer extends CardImpl {

    public ImperialCosmographer(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.KREE);
        this.subtype.add(SubType.SCIENTIST);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Whenever another creature you control leaves the battlefield without dying, put two +1/+1 counters on this creature.
        this.addAbility(new ImperialCosmographerTriggeredAbility());
    }

    private ImperialCosmographer(final ImperialCosmographer card) {
        super(card);
    }

    @Override
    public ImperialCosmographer copy() {
        return new ImperialCosmographer(this);
    }
}

class ImperialCosmographerTriggeredAbility extends TriggeredAbilityImpl {

    ImperialCosmographerTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance(2)));
        this.setTriggerPhrase("Whenever another creature you control leaves the battlefield without dying, ");
        setLeavesTheBattlefieldTrigger(true);
    }

    private ImperialCosmographerTriggeredAbility(final ImperialCosmographerTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ImperialCosmographerTriggeredAbility copy() {
        return new ImperialCosmographerTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (!Zone.BATTLEFIELD.match(zEvent.getFromZone())
                || Zone.GRAVEYARD.match(zEvent.getToZone())) {
            return false;
        }
        Permanent permanent = zEvent.getTarget();
        if (permanent == null || permanent.getId().equals(getSourceId())) {
            return false;
        }
        return permanent.isCreature(game) && permanent.isControlledBy(getControllerId());
    }
}
