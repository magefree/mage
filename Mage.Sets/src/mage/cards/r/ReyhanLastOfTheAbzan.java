package mage.cards.r;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.EntersBattlefieldAbility;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.effects.common.counter.AddCountersTargetEffect;
import mage.abilities.keyword.PartnerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ReyhanLastOfTheAbzan extends CardImpl {

    public ReyhanLastOfTheAbzan(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{G}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(0);
        this.toughness = new MageInt(0);

        // Reyhan, Last of the Abzan enters the battlefield with three +1/+1 counters on it.
        this.addAbility(new EntersBattlefieldAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance(3)), "with three +1/+1 counters on it"));

        // Whenever a creature you control dies or is put into the command zone, if it had one or more +1/+1 counters on it, you may put that may +1/+1 counters on target creature.
        Ability ability = new ReyhanLastOfTheAbzanTriggeredAbility();
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Partner
        this.addAbility(PartnerAbility.getInstance());
    }

    private ReyhanLastOfTheAbzan(final ReyhanLastOfTheAbzan card) {
        super(card);
    }

    @Override
    public ReyhanLastOfTheAbzan copy() {
        return new ReyhanLastOfTheAbzan(this);
    }
}

class ReyhanLastOfTheAbzanTriggeredAbility extends TriggeredAbilityImpl {

    public ReyhanLastOfTheAbzanTriggeredAbility() {
        super(Zone.BATTLEFIELD, null, true);
    }

    public ReyhanLastOfTheAbzanTriggeredAbility(final ReyhanLastOfTheAbzanTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ReyhanLastOfTheAbzanTriggeredAbility copy() {
        return new ReyhanLastOfTheAbzanTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zcEvent = (ZoneChangeEvent) event;
        // Dies or is put in the command zone
        if (zcEvent.getFromZone() != Zone.BATTLEFIELD) {
            return false;
        }
        if (zcEvent.getToZone() != Zone.GRAVEYARD && zcEvent.getToZone() != Zone.COMMAND) {
            return false;
        }

        // A creature
        Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
        if (permanent == null || !permanent.isCreature(game)) {
            return false;
        }

        // You control
        Player player = game.getPlayer(this.getControllerId());
        if (player == null || !permanent.isControlledBy(getControllerId())) {
            return false;
        }

        // If it had one or more +1/+1 counters on it
        int countersOn = permanent.getCounters(game).getCount(CounterType.P1P1);
        if (countersOn == 0) {
            return false;
        }

        // You may put that may +1/+1 counters on target creature
        this.getEffects().clear();
        this.addEffect(new AddCountersTargetEffect(CounterType.P1P1.createInstance(countersOn)));
        return true;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control dies or is put into the command zone, if it had one or more +1/+1 counters on it, you may put that many +1/+1 counters on target creature.";
    }
}
