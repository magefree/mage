
package mage.cards.d;

import java.util.Set;
import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.token.ZombieToken;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public final class DreadSummons extends CardImpl {

    public DreadSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{X}{B}{B}");

        // Each player puts the top X cards of their library into their graveyard. For each creature card put into a graveyard this way, you create a tapped 2/2 black Zombie creature token.
        getSpellAbility().addEffect(new DreadSummonsEffect());
    }

    public DreadSummons(final DreadSummons card) {
        super(card);
    }

    @Override
    public DreadSummons copy() {
        return new DreadSummons(this);
    }
}

class DreadSummonsEffect extends OneShotEffect {

    public DreadSummonsEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player puts the top X cards of their library into their graveyard. For each creature card put into a graveyard this way, you create a tapped 2/2 black Zombie creature token";
    }

    public DreadSummonsEffect(final DreadSummonsEffect effect) {
        super(effect);
    }

    @Override
    public DreadSummonsEffect copy() {
        return new DreadSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int numberOfCards = source.getManaCostsToPay().getX();
            if (numberOfCards > 0) {
                int numberOfCreatureCards = 0;
                for (UUID playerId : game.getState().getPlayersInRange(source.getControllerId(), game)) {
                    Player player = game.getPlayer(playerId);
                    if (player != null) {
                        Set<Card> movedCards = player.moveCardsToGraveyardWithInfo(player.getLibrary().getTopCards(game, numberOfCards), source, game, Zone.LIBRARY);
                        for (Card card : movedCards) {
                            if (card.isCreature()) {
                                numberOfCreatureCards++;
                            }
                        }
                    }
                }
                if (numberOfCreatureCards > 0) {
                    return new CreateTokenEffect(new ZombieToken(), numberOfCreatureCards, true, false).apply(game, source);
                }
            }
            return true;
        }
        return false;
    }
}
