
package mage.cards.o;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author emerald000
 */
public final class ObNixilisUnshackled extends CardImpl {

    public ObNixilisUnshackled(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{B}{B}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Whenever an opponent searches their library, that player sacrifices a creature and loses 10 life.
        this.addAbility(new ObNixilisUnshackledTriggeredAbility());

        // Whenever another creature dies, put at +1/+1 counter on Ob Nixilis, Unshackled.
        this.addAbility(new DiesCreatureTriggeredAbility(new AddCountersSourceEffect(CounterType.P1P1.createInstance()), false, true));
    }

    private ObNixilisUnshackled(final ObNixilisUnshackled card) {
        super(card);
    }

    @Override
    public ObNixilisUnshackled copy() {
        return new ObNixilisUnshackled(this);
    }
}

class ObNixilisUnshackledTriggeredAbility extends TriggeredAbilityImpl {

    public ObNixilisUnshackledTriggeredAbility() {
        super(Zone.BATTLEFIELD, new ObNixilisUnshackledEffect(), false);
    }

    public ObNixilisUnshackledTriggeredAbility(final ObNixilisUnshackledTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ObNixilisUnshackledTriggeredAbility copy() {
        return new ObNixilisUnshackledTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.LIBRARY_SEARCHED;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Player controller = game.getPlayer(this.getControllerId());
        if (controller != null && game.isOpponent(controller, event.getTargetId())
                && event.getTargetId().equals(event.getPlayerId())) {
            getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent searches their library, that player sacrifices a creature and loses 10 life.";
    }
}

class ObNixilisUnshackledEffect extends SacrificeEffect {

    static private final FilterPermanent filter = new FilterControlledCreaturePermanent("creature");

    public ObNixilisUnshackledEffect() {
        super(filter, 1, "that player");
        this.staticText = "that player sacrifices a creature and loses 10 life";
    }

    public ObNixilisUnshackledEffect(final ObNixilisUnshackledEffect effect) {
        super(effect);
    }

    @Override
    public ObNixilisUnshackledEffect copy() {
        return new ObNixilisUnshackledEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(this.getTargetPointer().getFirst(game, source));
        if (player != null) {
            player.loseLife(10, game, source, false);
        }
        return super.apply(game, source);
    }
}
