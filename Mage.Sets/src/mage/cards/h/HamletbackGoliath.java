package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.counters.CounterType;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author jeffwadsworth
 */
public final class HamletbackGoliath extends CardImpl {

    public HamletbackGoliath(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{6}{R}");
        this.subtype.add(SubType.GIANT);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        // Whenever another creature enters the battlefield, you may put X +1/+1 counters on Hamletback Goliath, where X is that creature's power.
        this.addAbility(new HamletbackGoliathTriggeredAbility());
    }

    private HamletbackGoliath(final HamletbackGoliath card) {
        super(card);
    }

    @Override
    public HamletbackGoliath copy() {
        return new HamletbackGoliath(this);
    }
}

class HamletbackGoliathTriggeredAbility extends TriggeredAbilityImpl {

    HamletbackGoliathTriggeredAbility() {
        super(Zone.BATTLEFIELD, new HamletbackGoliathEffect(), true);
    }

    private HamletbackGoliathTriggeredAbility(final HamletbackGoliathTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public HamletbackGoliathTriggeredAbility copy() {
        return new HamletbackGoliathTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        UUID targetId = event.getTargetId();
        Permanent permanent = game.getPermanent(targetId);
        if (permanent != null && permanent.isCreature(game)
                && !(targetId.equals(this.getSourceId()))) {
            for (Effect effect : this.getEffects()) {
                effect.setTargetPointer(new FixedTarget(permanent, game));
            }
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever another creature enters the battlefield, you may put X +1/+1 counters on {this}, where X is that creature's power.";
    }
}

class HamletbackGoliathEffect extends OneShotEffect {

    HamletbackGoliathEffect() {
        super(Outcome.BoostCreature);
    }

    private HamletbackGoliathEffect(final HamletbackGoliathEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = getTargetPointer().getFirstTargetPermanentOrLKI(game, source);
        Permanent sourceObject = game.getPermanent(source.getSourceId());
        if (creature != null && sourceObject != null) {
            sourceObject.addCounters(CounterType.P1P1.createInstance(creature.getPower().getValue()), source.getControllerId(), source, game);
        }
        return true;
    }

    @Override
    public HamletbackGoliathEffect copy() {
        return new HamletbackGoliathEffect(this);
    }
}
