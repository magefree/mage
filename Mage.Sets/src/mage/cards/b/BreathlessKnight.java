package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.constants.SubType;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.watchers.common.CastFromGraveyardWatcher;

/**
 *
 * @author weirddan455
 */
public final class BreathlessKnight extends CardImpl {

    public BreathlessKnight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{W}{B}");

        this.subtype.add(SubType.SPIRIT);
        this.subtype.add(SubType.KNIGHT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // Whenever Breathless Knight or another creature enters the battlefield under your control, if that creature entered from a graveyard or you cast it from a graveyard, put a +1/+1 counter on Breathless Knight.
        this.addAbility(new BreathlessKnightTriggeredAbility(), new CastFromGraveyardWatcher());
    }

    private BreathlessKnight(final BreathlessKnight card) {
        super(card);
    }

    @Override
    public BreathlessKnight copy() {
        return new BreathlessKnight(this);
    }
}

class BreathlessKnightTriggeredAbility extends TriggeredAbilityImpl {

    public BreathlessKnightTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.P1P1.createInstance()));
        setTriggerPhrase("Whenever {this} or another creature enters the battlefield under your control, if that creature entered from a graveyard or you cast it from a graveyard, ");
    }

    private BreathlessKnightTriggeredAbility(final BreathlessKnightTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public BreathlessKnightTriggeredAbility copy() {
        return new BreathlessKnightTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event instanceof EntersTheBattlefieldEvent) {
            EntersTheBattlefieldEvent entersEvent = (EntersTheBattlefieldEvent) event;
            Permanent permanent = entersEvent.getTarget();
            if (permanent != null && permanent.isCreature(game) && permanent.isControlledBy(this.getControllerId())) {
                if (entersEvent.getFromZone() == Zone.GRAVEYARD) {
                    return true;
                }
                CastFromGraveyardWatcher watcher = game.getState().getWatcher(CastFromGraveyardWatcher.class);
                int zcc = game.getState().getZoneChangeCounter(entersEvent.getSourceId());
                return watcher != null && watcher.spellWasCastFromGraveyard(entersEvent.getSourceId(), zcc - 1);
            }
        }
        return false;
    }
}
