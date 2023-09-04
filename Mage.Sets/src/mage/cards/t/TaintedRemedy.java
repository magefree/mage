
package mage.cards.t;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class TaintedRemedy extends CardImpl {

    public TaintedRemedy(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{B}");

        // If an opponent would gain life, that player loses that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TaintedRemedyReplacementEffect()));

    }

    private TaintedRemedy(final TaintedRemedy card) {
        super(card);
    }

    @Override
    public TaintedRemedy copy() {
        return new TaintedRemedy(this);
    }
}

class TaintedRemedyReplacementEffect extends ReplacementEffectImpl {

    public TaintedRemedyReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If an opponent would gain life, that player loses that much life instead";
    }

    private TaintedRemedyReplacementEffect(final TaintedRemedyReplacementEffect effect) {
        super(effect);
    }

    @Override
    public TaintedRemedyReplacementEffect copy() {
        return new TaintedRemedyReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            return controller.hasOpponent(event.getPlayerId(), game);
        }
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player opponent = game.getPlayer(event.getPlayerId());
        if (opponent != null) {
            opponent.loseLife(event.getAmount(), game, source, false);
        }
        return true;
    }

}
