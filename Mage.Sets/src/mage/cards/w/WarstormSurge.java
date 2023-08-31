package mage.cards.w;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.UUID;

/**
 * @author North
 */
public final class WarstormSurge extends CardImpl {

    public WarstormSurge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{5}{R}");

        // Whenever a creature enters the battlefield under your control, it deals damage equal to its power to any target.
        Ability ability = new WarstormSurgeTriggeredAbility();
        ability.addTarget(new TargetAnyTarget());
        this.addAbility(ability);
    }

    private WarstormSurge(final WarstormSurge card) {
        super(card);
    }

    @Override
    public WarstormSurge copy() {
        return new WarstormSurge(this);
    }
}

class WarstormSurgeTriggeredAbility extends TriggeredAbilityImpl {

    public WarstormSurgeTriggeredAbility() {
        super(Zone.BATTLEFIELD, new WarstormSurgeEffect(), false);
    }

    private WarstormSurgeTriggeredAbility(final WarstormSurgeTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        Permanent permanent = game.getPermanent(event.getTargetId());
        if (permanent != null
                && permanent.isCreature(game)
                && permanent.isControlledBy(this.controllerId)) {
            Effect effect = this.getEffects().get(0);
            effect.setValue("damageSource", event.getTargetId());
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield under your control, it deals damage equal to its power to any target.";
    }

    @Override
    public WarstormSurgeTriggeredAbility copy() {
        return new WarstormSurgeTriggeredAbility(this);
    }
}

class WarstormSurgeEffect extends OneShotEffect {

    public WarstormSurgeEffect() {
        super(Outcome.Damage);
        staticText = "it deals damage equal to its power to any target";
    }

    private WarstormSurgeEffect(final WarstormSurgeEffect effect) {
        super(effect);
    }

    @Override
    public WarstormSurgeEffect copy() {
        return new WarstormSurgeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID creatureId = (UUID) getValue("damageSource");
        Permanent creature = game.getPermanentOrLKIBattlefield(creatureId);
        if (creature != null) {
            int amount = creature.getPower().getValue();
            UUID target = source.getTargets().getFirstTarget();
            Permanent targetCreature = game.getPermanent(target);
            if (targetCreature != null) {
                targetCreature.damage(amount, creature.getId(), source, game, false, true);
                return true;
            }
            Player player = game.getPlayer(target);
            if (player != null) {
                player.damage(amount, creature.getId(), source, game);
                return true;
            }
        }
        return false;
    }
}
