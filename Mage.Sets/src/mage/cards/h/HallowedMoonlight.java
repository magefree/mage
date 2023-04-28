
package mage.cards.h;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.watchers.common.PermanentWasCastWatcher;

/**
 *
 * @author LevelX2
 */
public final class HallowedMoonlight extends CardImpl {

    public HallowedMoonlight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{1}{W}");

        // Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead.
        this.getSpellAbility().addEffect(new HallowedMoonlightEffect());
        this.getSpellAbility().addWatcher(new PermanentWasCastWatcher());
        // Draw a card.
        this.getSpellAbility().addEffect(new DrawCardSourceControllerEffect(1).concatBy("<br>"));
    }

    private HallowedMoonlight(final HallowedMoonlight card) {
        super(card);
    }

    @Override
    public HallowedMoonlight copy() {
        return new HallowedMoonlight(this);
    }
}

class HallowedMoonlightEffect extends ReplacementEffectImpl {

    public HallowedMoonlightEffect() {
        super(Duration.EndOfTurn, Outcome.Exile);
        staticText = "Until end of turn, if a creature would enter the battlefield and it wasn't cast, exile it instead";
    }

    public HallowedMoonlightEffect(final HallowedMoonlightEffect effect) {
        super(effect);
    }

    @Override
    public HallowedMoonlightEffect copy() {
        return new HallowedMoonlightEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        Card targetCard = game.getCard(event.getTargetId());
        if (targetCard == null) {
            targetCard = ((EntersTheBattlefieldEvent) event).getTarget();
        }
        if (controller != null && targetCard != null) {
            controller.moveCards(targetCard, Zone.EXILED, source, game, false, false, false, null);
            return true;
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        EntersTheBattlefieldEvent entersTheBattlefieldEvent = (EntersTheBattlefieldEvent) event;
        if (entersTheBattlefieldEvent.getTarget().isCreature(game)) {
            PermanentWasCastWatcher watcher = game.getState().getWatcher(PermanentWasCastWatcher.class);
            if (watcher != null && !watcher.wasPermanentCastThisTurn(event.getTargetId())) {
                return true;
            }
        }
        return false;
    }
}
