package mage.abilities.common;

import mage.MageObject;
import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ZoneChangeEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 * @author TheElk801
 */
public class GodEternalDiesTriggeredAbility extends TriggeredAbilityImpl {

    public GodEternalDiesTriggeredAbility() {
        super(Zone.ALL, null, true);
    }

    private GodEternalDiesTriggeredAbility(GodEternalDiesTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ZONE_CHANGE) {
            ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
            return zEvent.getFromZone() == Zone.BATTLEFIELD
                    && (zEvent.getToZone() == Zone.GRAVEYARD
                    || zEvent.getToZone() == Zone.EXILED);
        }
        return false;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        ZoneChangeEvent zEvent = (ZoneChangeEvent) event;
        if (zEvent.getTargetId().equals(this.getSourceId())) {
            this.getEffects().clear();
            this.addEffect(new GodEternalEffect(new MageObjectReference(zEvent.getTarget(), game)));
            return true;
        }
        return false;
    }

    @Override
    public boolean isInUseableZone(Game game, MageObject source, GameEvent event) {
        Permanent sourcePermanent = null;
        if (game.getState().getZone(getSourceId()) == Zone.BATTLEFIELD) {
            sourcePermanent = game.getPermanent(getSourceId());
        } else {
            if (game.getShortLivingLKI(getSourceId(), Zone.BATTLEFIELD)) {
                sourcePermanent = (Permanent) game.getLastKnownInformation(getSourceId(), Zone.BATTLEFIELD);
            }
        }
        if (sourcePermanent == null) {
            return false;
        }
        return hasSourceObjectAbility(game, sourcePermanent, event);
    }

    @Override
    public GodEternalDiesTriggeredAbility copy() {
        return new GodEternalDiesTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "When {this} dies or is put into exile from the battlefield, "
                + "you may put it into its owner's library third from the top.";
    }
}

class GodEternalEffect extends OneShotEffect {

    private final MageObjectReference mor;

    GodEternalEffect(MageObjectReference mor) {
        super(Outcome.Benefit);
        this.mor = mor;
    }

    private GodEternalEffect(final GodEternalEffect effect) {
        super(effect);
        this.mor = effect.mor;
    }

    @Override
    public GodEternalEffect copy() {
        return new GodEternalEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Card card = game.getCard(mor.getSourceId());
        if (card == null || card.getZoneChangeCounter(game) - 1 != mor.getZoneChangeCounter()) {
            return false;
        }
        return player.putCardOnTopXOfLibrary(card, game, source, 3, true);
    }
}
