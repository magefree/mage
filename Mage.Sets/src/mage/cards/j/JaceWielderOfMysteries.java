package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.LoyaltyAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.MillCardsTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JaceWielderOfMysteries extends CardImpl {

    public JaceWielderOfMysteries(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.PLANESWALKER}, "{1}{U}{U}{U}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.JACE);
        this.setStartingLoyalty(4);

        // If you would draw a card while your library has no cards in it, you win the game instead.
        this.addAbility(new SimpleStaticAbility(new JaceWielderOfMysteriesContinuousEffect()));

        // +1 Target player puts the top two cards of their library into their graveyard. Draw a card.
        Ability ability = new LoyaltyAbility(new MillCardsTargetEffect(2), 1);
        ability.addTarget(new TargetPlayer());
        ability.addEffect(new DrawCardSourceControllerEffect(1));
        this.addAbility(ability);

        // -8: Draw seven cards. Then if your library has no cards in it, you win the game.
        this.addAbility(new LoyaltyAbility(new JaceWielderOfMysteriesEffect(), -8));
    }

    private JaceWielderOfMysteries(final JaceWielderOfMysteries card) {
        super(card);
    }

    @Override
    public JaceWielderOfMysteries copy() {
        return new JaceWielderOfMysteries(this);
    }
}

class JaceWielderOfMysteriesContinuousEffect extends ReplacementEffectImpl {

    JaceWielderOfMysteriesContinuousEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you would draw a card while your library has no cards in it, you win the game instead";
    }

    private JaceWielderOfMysteriesContinuousEffect(final JaceWielderOfMysteriesContinuousEffect effect) {
        super(effect);
    }

    @Override
    public JaceWielderOfMysteriesContinuousEffect copy() {
        return new JaceWielderOfMysteriesContinuousEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Player player = game.getPlayer(event.getPlayerId());
        if (player != null) {
            player.won(game);
        }
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DRAW_CARD;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getPlayerId().equals(source.getControllerId())) {
            Player player = game.getPlayer(event.getPlayerId());
            if (player != null && !player.hasLost() && !player.getLibrary().hasCards()) {
                return true;
            }
        }
        return false;
    }
}

class JaceWielderOfMysteriesEffect extends OneShotEffect {

    JaceWielderOfMysteriesEffect() {
        super(Outcome.Benefit);
        staticText = "Draw seven cards. Then if your library has no cards in it, you win the game.";
    }

    private JaceWielderOfMysteriesEffect(final JaceWielderOfMysteriesEffect effect) {
        super(effect);
    }

    @Override
    public JaceWielderOfMysteriesEffect copy() {
        return new JaceWielderOfMysteriesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        player.drawCards(7, source, game);
        if (!player.getLibrary().hasCards()) {
            player.won(game);
        }
        return true;
    }
}
