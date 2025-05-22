package mage.cards.s;

import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.FlashbackAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Snort extends CardImpl {

    public Snort(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{R}");

        // Each player may discard their hand and draw five cards. Then Snort deals 5 damage to each opponent who discarded their hand this way.
        this.getSpellAbility().addEffect(new SnortEffect());

        // Flashback {5}{R}
        this.addAbility(new FlashbackAbility(this, new ManaCostsImpl<>("{5}{R}")));
    }

    private Snort(final Snort card) {
        super(card);
    }

    @Override
    public Snort copy() {
        return new Snort(this);
    }
}

class SnortEffect extends OneShotEffect {

    SnortEffect() {
        super(Outcome.Benefit);
        staticText = "each player may discard their hand and draw five cards. " +
                "Then {this} deals 5 damage to each opponent who discarded their hand this way";
    }

    private SnortEffect(final SnortEffect effect) {
        super(effect);
    }

    @Override
    public SnortEffect copy() {
        return new SnortEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Set<UUID> toDiscard = new HashSet<>();
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Player player = game.getPlayer(playerId);
            if (player == null) {
                continue;
            } else if (player.chooseUse(
                    Outcome.DrawCard,
                    source.isControlledBy(playerId)
                            ? "Discard your hand and draw five cards?"
                            : "Discard your hand, draw five cards, and be dealt 5 damage?",
                    source, game
            )) {
                game.informPlayers(player.getLogName() + " chooses to discard and draw five");
                toDiscard.add(playerId);
            } else {
                game.informPlayers(player.getLogName() + " chooses not to discard");
            }
        }
        for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
            Optional.ofNullable(playerId)
                    .filter(toDiscard::contains)
                    .map(game::getPlayer)
                    .ifPresent(player -> {
                        player.discard(player.getHand(), false, source, game);
                        player.drawCards(5, source, game);
                    });
        }
        for (UUID playerId : game.getOpponents(source.getControllerId())) {
            Optional.ofNullable(playerId)
                    .filter(toDiscard::contains)
                    .map(game::getPlayer)
                    .ifPresent(player -> player.damage(5, source, game));
        }
        return !toDiscard.isEmpty();
    }
}
