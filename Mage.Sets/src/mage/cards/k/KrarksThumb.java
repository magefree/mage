
package mage.cards.k;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.RandomUtil;

/**
 *
 * @author LevelX2
 */
public final class KrarksThumb extends CardImpl {

    public KrarksThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ARTIFACT},"{2}");
        addSuperType(SuperType.LEGENDARY);

        // If you would flip a coin, instead flip two coins and ignore one.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KrarksThumbEffect()));
    }

    public KrarksThumb(final KrarksThumb card) {
        super(card);
    }

    @Override
    public KrarksThumb copy() {
        return new KrarksThumb(this);
    }
}

class KrarksThumbEffect extends ReplacementEffectImpl {

    KrarksThumbEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would flip a coin, instead flip two coins and ignore one";
    }

    KrarksThumbEffect(final KrarksThumbEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            // because second flip is ignored it may not be done by the player method
            boolean secondCoinFlip = RandomUtil.nextBoolean();
            if (!game.isSimulation()) {
                game.informPlayers("[Flip a coin] " + player.getLogName() + (secondCoinFlip ? " won (head)." : " lost (tail)."));
            }
            if (player.chooseUse(outcome, "Ignore the first coin flip?", source, game)) {
                event.setFlag(secondCoinFlip);
                game.informPlayers(player.getLogName() + " ignores the first coin flip.");
            } else {
                game.informPlayers(player.getLogName() + " ignores the second coin flip.");
            }
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.FLIP_COIN;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return source.isControlledBy(event.getPlayerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public KrarksThumbEffect copy() {
        return new KrarksThumbEffect(this);
    }
}
