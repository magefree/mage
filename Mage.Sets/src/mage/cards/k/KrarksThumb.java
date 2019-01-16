
package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.FlipCoinEvent;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.util.CardUtil;
import mage.util.RandomUtil;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class KrarksThumb extends CardImpl {

    public KrarksThumb(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{2}");
        addSuperType(SuperType.LEGENDARY);

        // If you would flip a coin, instead flip two coins and ignore one.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new KrarksThumbEffect()));
    }

    private KrarksThumb(final KrarksThumb card) {
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

    private KrarksThumbEffect(final KrarksThumbEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player == null || !player.getId().equals(source.getControllerId())) {
            return false;
        }
        FlipCoinEvent flipEvent = (FlipCoinEvent) event;
        boolean secondFlip = RandomUtil.nextBoolean();
        game.informPlayers(player.getLogName() + " flipped a " + flipEvent.getResultName()
                + " and a " + CardUtil.booleanToFlipName(secondFlip)
        );
        boolean chosenFlip = player.chooseUse(
                Outcome.Benefit, "Choose which coin you want",
                "(You chose " + flipEvent.getChosenName() + ")",
                flipEvent.getResultName(), CardUtil.booleanToFlipName(secondFlip), source, game
        );
        if (!chosenFlip) {
            flipEvent.setResult(secondFlip);
        }
        game.informPlayers(player.getLogName() + " chooses to keep " + flipEvent.getResultName());
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
