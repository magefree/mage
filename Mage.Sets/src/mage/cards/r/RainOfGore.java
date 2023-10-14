
package mage.cards.r;

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
import mage.game.stack.StackObject;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class RainOfGore extends CardImpl {

    public RainOfGore(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{B}{R}");


        // If a spell or ability would cause its controller to gain life, that player loses that much life instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new RainOfGoreEffect()));

    }

    private RainOfGore(final RainOfGore card) {
        super(card);
    }

    @Override
    public RainOfGore copy() {
        return new RainOfGore(this);
    }
}


class RainOfGoreEffect extends ReplacementEffectImpl {

    public RainOfGoreEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If a spell or ability would cause its controller to gain life, that player loses that much life instead";
    }

    private RainOfGoreEffect(final RainOfGoreEffect effect) {
        super(effect);
    }

    @Override
    public RainOfGoreEffect copy() {
        return new RainOfGoreEffect(this);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.loseLife(event.getAmount(), game, source, false);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (!game.getStack().isEmpty()) {
            StackObject stackObject = game.getStack().getFirst();
            return stackObject.isControlledBy(event.getPlayerId());
        }
        return false;
    }
}