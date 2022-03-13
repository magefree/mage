package mage.cards.g;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author North
 */
public final class GravePact extends CardImpl {

    public GravePact(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{1}{B}{B}{B}");


        // Whenever a creature you control dies, each other player sacrifices a creature.
        this.addAbility(new GravePactTriggeredAbility());
    }

    private GravePact(final GravePact card) {
        super(card);
    }

    @Override
    public GravePact copy() {
        return new GravePact(this);
    }
}

class GravePactTriggeredAbility extends TriggeredAbilityImpl {

    public GravePactTriggeredAbility() {
        super(Zone.BATTLEFIELD, new GravePactEffect());
    }

    public GravePactTriggeredAbility(final GravePactTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public GravePactTriggeredAbility copy() {
        return new GravePactTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent permanent = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            return permanent != null && permanent.isControlledBy(this.getControllerId()) && permanent.isCreature(game);
        }
        return false;
    }

    @Override
    public String getTriggerPhrase() {
        return "Whenever a creature you control dies, " ;
    }
}

class GravePactEffect extends OneShotEffect {

    public GravePactEffect() {
        super(Outcome.Sacrifice);
        this.staticText = "each other player sacrifices a creature";
    }

    public GravePactEffect(final GravePactEffect effect) {
        super(effect);
    }

    @Override
    public GravePactEffect copy() {
        return new GravePactEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<UUID> perms = new ArrayList<>();
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null && !playerId.equals(source.getControllerId())) {
                    TargetControlledCreaturePermanent target = new TargetControlledCreaturePermanent();
                    target.setNotTarget(true);
                    if (target.canChoose(player.getId(), source, game)) {
                        player.chooseTarget(Outcome.Sacrifice, target, source, game);
                        perms.addAll(target.getTargets());
                    }
                }
            }
            for (UUID permID : perms) {
                Permanent permanent = game.getPermanent(permID);
                if (permanent != null) {
                    permanent.sacrifice(source, game);
                }
            }
            return true;
        }
        return false;
    }
}
