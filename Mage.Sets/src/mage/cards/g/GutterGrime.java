
package mage.cards.g;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.counter.AddCountersSourceEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.game.permanent.PermanentToken;
import mage.game.permanent.token.GutterGrimeToken;

/**
 *
 * @author BetaSteward
 */
public final class GutterGrime extends CardImpl {

    public GutterGrime(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}");

        // Whenever a nontoken creature you control dies, put a slime counter on Gutter Grime, then create a green Ooze creature token with "This creature's power and toughness are each equal to the number of slime counters on Gutter Grime."
        this.addAbility(new GutterGrimeTriggeredAbility());
    }

    private GutterGrime(final GutterGrime card) {
        super(card);
    }

    @Override
    public GutterGrime copy() {
        return new GutterGrime(this);
    }
}

class GutterGrimeTriggeredAbility extends TriggeredAbilityImpl {

    public GutterGrimeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new AddCountersSourceEffect(CounterType.SLIME.createInstance()), false);
        this.addEffect(new GutterGrimeEffect());
    }

    public GutterGrimeTriggeredAbility(GutterGrimeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GutterGrimeTriggeredAbility copy() {
        return new GutterGrimeTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        MageObject card = game.getLastKnownInformation(targetId, Zone.BATTLEFIELD);
        if (card instanceof Permanent && !(card instanceof PermanentToken)) {
            Permanent permanent = (Permanent) card;
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return zEvent.isDiesEvent()
                    && permanent.isControlledBy(this.controllerId)
                    && (targetId.equals(this.getSourceId())
                    || (permanent.isCreature(game)));
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a nontoken creature you control dies, put a slime counter on {this}, then create a green Ooze creature token with \"This creature's power and toughness are each equal to the number of slime counters on {this}.\"";
    }
}

class GutterGrimeEffect extends OneShotEffect {

    public GutterGrimeEffect() {
        super(Outcome.PutCreatureInPlay);
    }

    public GutterGrimeEffect(final GutterGrimeEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        GutterGrimeToken token = new GutterGrimeToken(source.getSourceId());
        token.putOntoBattlefield(1, game, source, source.getControllerId());
        return true;
    }

    @Override
    public GutterGrimeEffect copy() {
        return new GutterGrimeEffect(this);
    }

}
