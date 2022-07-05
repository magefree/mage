

package mage.cards.v;

import java.util.UUID;

import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
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

/**
 * @author Loki
 */
public final class VigilForTheLost extends CardImpl {

    public VigilForTheLost(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{3}{W}");

        this.addAbility(new VigilForTheLostTriggeredAbility());
    }

    private VigilForTheLost(final VigilForTheLost card) {
        super(card);
    }

    @Override
    public VigilForTheLost copy() {
        return new VigilForTheLost(this);
    }

}

class VigilForTheLostTriggeredAbility extends TriggeredAbilityImpl {
    VigilForTheLostTriggeredAbility() {
        super(Zone.BATTLEFIELD, new VigilForTheLostEffect());
    }

    VigilForTheLostTriggeredAbility(final VigilForTheLostTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public VigilForTheLostTriggeredAbility copy() {
        return new VigilForTheLostTriggeredAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zoneChangeEvent = (ZoneChangeEvent) event;
        if (zoneChangeEvent.isDiesEvent()) {
            Permanent p = (Permanent) game.getLastKnownInformation(event.getTargetId(), Zone.BATTLEFIELD);
            if (p.isControlledBy(this.getControllerId()) && p.isCreature(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever a creature you control is put into a graveyard from the battlefield, you may pay {X}. If you do, you gain X life.";
    }
}

class VigilForTheLostEffect extends OneShotEffect {
    VigilForTheLostEffect() {
        super(Outcome.GainLife);
        staticText = "you may pay {X}. If you do, you gain X life";
    }

    VigilForTheLostEffect(final VigilForTheLostEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ManaCostsImpl cost = new ManaCostsImpl<>("{X}");
        cost.clearPaid();
        if (cost.payOrRollback(source, game, source, source.getControllerId())) {
            Player player = game.getPlayer(source.getControllerId());
            if (player != null) {
                player.gainLife(cost.getX(), game, source);
                return true;
            }
        }
        return false;

    }

    @Override
    public VigilForTheLostEffect copy() {
        return new VigilForTheLostEffect(this);
    }

}