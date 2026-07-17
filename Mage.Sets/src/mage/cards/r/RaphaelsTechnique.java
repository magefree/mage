package mage.cards.r;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.SneakAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class RaphaelsTechnique extends CardImpl {

    public RaphaelsTechnique(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{4}{R}{R}");

        // Sneak {2}{R}
        this.addAbility(new SneakAbility(this, "{2}{R}"));

        // Each player may discard their hand and draw seven cards.
        this.getSpellAbility().addEffect(new RaphaelsTechniqueEffect());
    }

    private RaphaelsTechnique(final RaphaelsTechnique card) {
        super(card);
    }

    @Override
    public RaphaelsTechnique copy() {
        return new RaphaelsTechnique(this);
    }
}

class RaphaelsTechniqueEffect extends OneShotEffect {

    RaphaelsTechniqueEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard their hand and draw seven cards";
    }

    private RaphaelsTechniqueEffect(final RaphaelsTechniqueEffect effect) {
        super(effect);
    }

    @Override
    public RaphaelsTechniqueEffect copy() {
        return new RaphaelsTechniqueEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        List<Player> wheelers = new ArrayList<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player != null && player.chooseUse(
                    Outcome.DrawCard, "Discard your hand and draw seven?", source, game
            )) {
                game.informPlayers(player.getName() + " chooses to discard their hand and draw seven");
                wheelers.add(player);
            }
        }
        for (Player player : wheelers) {
            player.discard(player.getHand(), false, source, game);
            player.drawCards(7, source, game);
        }
        return true;
    }
}
