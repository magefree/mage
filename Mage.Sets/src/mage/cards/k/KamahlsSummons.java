
package mage.cards.k;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.game.permanent.token.BearToken;
import mage.game.permanent.token.TokenImpl;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author L_J
 */
public final class KamahlsSummons extends CardImpl {

    public KamahlsSummons(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{G}");

        // Each player may reveal any number of creature cards from their hand. Then each player creates a 2/2 green Bear creature token for each card he or she revealed this way.
        getSpellAbility().addEffect(new KamahlsSummonsEffect());
    }

    public KamahlsSummons(final KamahlsSummons card) {
        super(card);
    }

    @Override
    public KamahlsSummons copy() {
        return new KamahlsSummons(this);
    }
}

class KamahlsSummonsEffect extends OneShotEffect {

    private static final FilterCard filter = new FilterCreatureCard();

    public KamahlsSummonsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player may reveal any number of creature cards from their hand. Then each player creates a 2/2 green Bear creature token for each card he or she revealed this way";
    }

    public KamahlsSummonsEffect(final KamahlsSummonsEffect effect) {
        super(effect);
    }

    @Override
    public KamahlsSummonsEffect copy() {
        return new KamahlsSummonsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller != null && sourceObject != null) {
            Map<UUID,Integer> revealedCards = new HashMap<>();
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    if (player.getHand().count(filter, game) > 0) {
                        TargetCardInHand target = new TargetCardInHand(0, Integer.MAX_VALUE, filter);
                        if (player.choose(outcome, target, source.getSourceId(), game)) {
                            Cards cards = new CardsImpl(target.getTargets());
                            controller.revealCards(sourceObject.getIdName(), cards, game);
                            revealedCards.put(playerId, target.getTargets().size());
                        }
                    }
                }
            }
            Token token = new BearToken();
            for (UUID playerId : revealedCards.keySet()) {
                int value = revealedCards.get(playerId);
                if (value > 0) {
                    token.putOntoBattlefield(value, game, source.getSourceId(), playerId);   
                }
            }
            return true;
        }
        return false;
    }
}
