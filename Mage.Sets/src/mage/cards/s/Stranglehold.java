
package mage.cards.s;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class Stranglehold extends CardImpl {

    public Stranglehold(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{3}{R}");

        // Your opponents can't search libraries.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new OpponentsCantSearchLibarariesEffect()));

        // If an opponent would begin an extra turn, that player skips that turn instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StrangleholdSkipExtraTurnsEffect()));
    }

    private Stranglehold(final Stranglehold card) {
        super(card);
    }

    @Override
    public Stranglehold copy() {
        return new Stranglehold(this);
    }
}

class OpponentsCantSearchLibarariesEffect extends ContinuousRuleModifyingEffectImpl {

    public OpponentsCantSearchLibarariesEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit, true, false);
        staticText = "Your opponents can't search libraries";
    }

    public OpponentsCantSearchLibarariesEffect(final OpponentsCantSearchLibarariesEffect effect) {
        super(effect);
    }

    @Override
    public OpponentsCantSearchLibarariesEffect copy() {
        return new OpponentsCantSearchLibarariesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source);
        if (mageObject != null) {
            return "You can't search libraries (" + mageObject.getLogName() + " in play).";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return EventType.SEARCH_LIBRARY == event.getType();
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }
}

class StrangleholdSkipExtraTurnsEffect extends ReplacementEffectImpl {

    public StrangleholdSkipExtraTurnsEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "If an opponent would begin an extra turn, that player skips that turn instead";
    }

    public StrangleholdSkipExtraTurnsEffect(final StrangleholdSkipExtraTurnsEffect effect) {
        super(effect);
    }

    @Override
    public StrangleholdSkipExtraTurnsEffect copy() {
        return new StrangleholdSkipExtraTurnsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        MageObject sourceObject = game.getObject(source);
        if (player != null && sourceObject != null) {
            game.informPlayers(sourceObject.getLogName() + ": Extra turn of " + player.getLogName() + " skipped");
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.EXTRA_TURN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        return controller != null && controller.hasOpponent(event.getPlayerId(), game);
    }

}
