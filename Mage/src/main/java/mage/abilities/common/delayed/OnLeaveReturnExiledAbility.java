package mage.abilities.common.delayed;

import java.util.LinkedHashSet;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.players.Player;
import mage.util.CardUtil;

/**
 *
 * Returns the exiled cards/permanents as source leaves battlefield
 *
 * Uses no stack
 *
 * 11/4/2015: In a multiplayer game, if Grasp of Fate's owner leaves the game,
 * the exiled cards will return to the battlefield. Because the one-shot effect
 * that returns the cards isn't an ability that goes on the stack, it won't
 * cease to exist along with the leaving player's spells and abilities on the
 * stack.
 *
 * @author LevelX2
 */
public class OnLeaveReturnExiledAbility extends DelayedTriggeredAbility {

    public OnLeaveReturnExiledAbility() {
        this(Zone.BATTLEFIELD);
    }

    public OnLeaveReturnExiledAbility(Zone zone) {
        super(new ReturnExiledPermanentsEffect(zone), Duration.OneUse);
        this.usesStack = false;
        this.setRuleVisible(false);
    }

    public OnLeaveReturnExiledAbility(final OnLeaveReturnExiledAbility ability) {
        super(ability);
    }

    @Override
    public OnLeaveReturnExiledAbility copy() {
        return new OnLeaveReturnExiledAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ZONE_CHANGE;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getTargetId().equals(this.getSourceId())) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            if (zEvent.getFromZone() == Zone.BATTLEFIELD) {
                return true;
            }
        }
        return false;
    }
}

class ReturnExiledPermanentsEffect extends OneShotEffect {

    private final Zone zone;

    ReturnExiledPermanentsEffect(Zone zone) {
        super(Outcome.Benefit);
        this.zone = zone;
        this.staticText = "Return exiled permanents";
    }

    public ReturnExiledPermanentsEffect(final ReturnExiledPermanentsEffect effect) {
        super(effect);
        this.zone = effect.zone;
    }

    @Override
    public ReturnExiledPermanentsEffect copy() {
        return new ReturnExiledPermanentsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null && controller != null) {
            ExileZone exile = getExileIfPossible(game, source);
            if (exile != null) {
                return controller.moveCards(new LinkedHashSet<>(exile.getCards(game)), zone, source, game, false, false, true, null);
            }
        }
        return false;
    }

    private ExileZone getExileIfPossible(final Game game, final Ability source) {
        UUID exileZone = CardUtil.getExileZoneId(game, source.getSourceId(), source.getSourceObjectZoneChangeCounter());

        if (exileZone != null) {
            ExileZone exile = game.getExile().getExileZone(exileZone);
            if (exile == null) {
                // try without ZoneChangeCounter - that is useful for tokens
                exileZone = CardUtil.getCardExileZoneId(game, source);
                if (exileZone != null) {
                    return game.getExile().getExileZone(exileZone);
                }
            }
            return exile;
        }

        return null;
    }
}
