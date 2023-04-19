package mage.cards.k;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.choices.ChooseFriendsAndFoes;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.players.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class KhorvathsFury extends CardImpl {

    public KhorvathsFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{4}{R}");

        // For each player, choose friend or foe. Each friend discards all cards from their hand, then draws that many cards plus one. Khorvath's Fury deals damage to each foe equal to the number of cards in their hand.
        this.getSpellAbility().addEffect(new KhorvathsFuryEffect());
    }

    private KhorvathsFury(final KhorvathsFury card) {
        super(card);
    }

    @Override
    public KhorvathsFury copy() {
        return new KhorvathsFury(this);
    }
}

class KhorvathsFuryEffect extends OneShotEffect {

    KhorvathsFuryEffect() {
        super(Outcome.Benefit);
        this.staticText = "For each player, choose friend or foe. "
                + "Each friend discards all cards from their hand, "
                + "then draws that many cards plus one."
                + " {this} deals damage to each foe equal to the number of cards in their hand";
    }

    KhorvathsFuryEffect(final KhorvathsFuryEffect effect) {
        super(effect);
    }

    @Override
    public KhorvathsFuryEffect copy() {
        return new KhorvathsFuryEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        ChooseFriendsAndFoes choice = new ChooseFriendsAndFoes();
        choice.chooseFriendOrFoe(controller, source, game);
        Map<UUID, Integer> cardsToDraw = new HashMap<>();
        for (Player player : choice.getFriends()) {
            if (player != null) {
                int cardsInHand = player.getHand().size();
                if (cardsInHand > 0) {
                    player.discard(cardsInHand, false, false, source, game);
                    cardsToDraw.put(player.getId(), cardsInHand);
                }
            }
        }
        for (Player player : choice.getFriends()) {
            if (player != null) {
                // If a friend has zero cards in hand, that player discards nothing and draws one card. (2018-06-08)
                player.drawCards(cardsToDraw.getOrDefault(player.getId(), 0) + 1, source, game);
            }
        }
        for (Player player : choice.getFoes()) {
            if (player != null) {
                player.damage(player.getHand().size(), source.getSourceId(), source, game);
            }
        }
        return true;
    }
}
