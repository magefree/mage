package mage.cards.a;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AbundantHarvest extends CardImpl {

    public AbundantHarvest(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{G}");

        // Choose land or nonland. Reveal cards from the top of your library until you reveal a card of the chosen kind. Put that card into your hand and the rest on the bottom of your library in a random order.
        this.getSpellAbility().addEffect(new AbundantHarvestEffect());
    }

    private AbundantHarvest(final AbundantHarvest card) {
        super(card);
    }

    @Override
    public AbundantHarvest copy() {
        return new AbundantHarvest(this);
    }
}

class AbundantHarvestEffect extends OneShotEffect {

    AbundantHarvestEffect() {
        super(Outcome.Benefit);
        staticText = "Choose land or nonland. Reveal cards from the top of your library " +
                "until you reveal a card of the chosen kind. Put that card into your hand " +
                "and the rest on the bottom of your library in a random order.";
    }

    private AbundantHarvestEffect(final AbundantHarvestEffect effect) {
        super(effect);
    }

    @Override
    public AbundantHarvestEffect copy() {
        return new AbundantHarvestEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        boolean land = player.chooseUse(
                Outcome.Neutral, "Choose land or nonland", null,
                "Land", "Nonland", source, game
        );
        Cards toReveal = new CardsImpl();
        Card toHand = null;
        for (Card card : player.getLibrary().getCards(game)) {
            if (card == null) {
                continue;
            }
            toReveal.add(card);
            if (card.isLand(game) == land) {
                toHand = card;
                break;
            }
        }
        player.revealCards(source, toReveal, game);
        if (toHand != null) {
            toReveal.remove(toHand);
            player.moveCards(toHand, Zone.HAND, source, game);
        }
        player.putCardsOnBottomOfLibrary(toReveal, game, source, false);
        return true;
    }
}
