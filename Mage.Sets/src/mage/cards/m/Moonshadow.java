package mage.cards.m;

import mage.MageInt;
import mage.abilities.BatchTriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldWithCountersAbility;
import mage.abilities.effects.common.counter.RemoveCounterSourceEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeBatchEvent;
import mage.game.events.ZoneChangeEvent;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Moonshadow extends CardImpl {

    public Moonshadow(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{B}");

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(7);
        this.toughness = new MageInt(7);

        // Menace
        this.addAbility(new MenaceAbility());

        // This creature enters with six -1/-1 counters on it.
        this.addAbility(new EntersBattlefieldWithCountersAbility(CounterType.M1M1.createInstance(6)));

        // Whenever one or more permanent cards are put into your graveyard from anywhere while this creature has a -1/-1 counter on it, remove a -1/-1 counter from this creature.
        this.addAbility(new MoonshadowTriggeredAbility());
    }

    private Moonshadow(final Moonshadow card) {
        super(card);
    }

    @Override
    public Moonshadow copy() {
        return new Moonshadow(this);
    }
}

class MoonshadowTriggeredAbility extends TriggeredAbilityImpl implements BatchTriggeredAbility<ZoneChangeEvent> {

    MoonshadowTriggeredAbility() {
        super(Zone.BATTLEFIELD, new RemoveCounterSourceEffect(CounterType.M1M1.createInstance()));
        setTriggerPhrase("Whenever one or more permanent cards are put into your graveyard " +
                "from anywhere while this creature has a -1/-1 counter on it, ");
    }

    private MoonshadowTriggeredAbility(final MoonshadowTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public MoonshadowTriggeredAbility copy() {
        return new MoonshadowTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE_BATCH;
    }

    @Override
    public boolean checkEvent(ZoneChangeEvent event, Game game) {
        if (!Zone.GRAVEYARD.match(event.getToZone())) {
            return false;
        }
        Card card = game.getCard(event.getTargetId());
        return card != null
                && isControlledBy(card.getOwnerId())
                && card.isPermanent(game);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return !getFilteredEvents((ZoneChangeBatchEvent) event, game).isEmpty()
                && Optional
                .ofNullable(getSourcePermanentIfItStillExists(game))
                .filter(permanent -> permanent.getCounters(game).containsKey(CounterType.M1M1))
                .isPresent();
    }
}
