
package mage.cards.n;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterCard;
import mage.filter.common.FilterNonlandCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author L_J
 */
public final class NoxiousVapors extends CardImpl {

    public NoxiousVapors(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{1}{B}{B}");

        // Each player reveals their hand, chooses one card of each color from it, then discards all other nonland cards.
        this.getSpellAbility().addEffect(new NoxiousVaporsEffect());

    }

    public NoxiousVapors(final NoxiousVapors card) {
        super(card);
    }

    @Override
    public NoxiousVapors copy() {
        return new NoxiousVapors(this);
    }
}

class NoxiousVaporsEffect extends OneShotEffect {

    private static final FilterNonlandCard filter = new FilterNonlandCard();

    public NoxiousVaporsEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each player reveals their hand, chooses one card of each color from it, then discards all other nonland cards";
    }

    public NoxiousVaporsEffect(final NoxiousVaporsEffect effect) {
        super(effect);
    }

    @Override
    public NoxiousVaporsEffect copy() {
        return new NoxiousVaporsEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    player.revealCards(player.getName() + "'s hand", player.getHand(), game);
                }
            }
            
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player player = game.getPlayer(playerId);
                if (player != null) {
                    Set<Card> chosenCards = new HashSet<>();
                    chooseCardForColor(ObjectColor.WHITE, chosenCards, player, game, source);
                    chooseCardForColor(ObjectColor.BLUE, chosenCards, player, game, source);
                    chooseCardForColor(ObjectColor.BLACK, chosenCards, player, game, source);
                    chooseCardForColor(ObjectColor.RED, chosenCards, player, game, source);
                    chooseCardForColor(ObjectColor.GREEN, chosenCards, player, game, source);
                    
                    Set<Card> cards = player.getHand().getCards(game);
                    for (Card card : cards) {
                        if (card != null && !chosenCards.contains(card) && filter.match(card, game)) {
                            player.discard(card, source, game);
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }
    
    private void chooseCardForColor(ObjectColor color, Set<Card> chosenCards, Player player, Game game, Ability source) {
        FilterCard filter = new FilterCard();
        filter.add(new ColorPredicate(color));
        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.Benefit, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                chosenCards.add(card);
            }
        }
    }
}
