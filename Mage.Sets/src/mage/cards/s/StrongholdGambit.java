
package mage.cards.s;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.cards.CardsImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 *
 * @author LevelX2
 */
public final class StrongholdGambit extends CardImpl {

    public StrongholdGambit(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{R}");

        // Each player chooses a card in their hand. Then each player reveals their chosen card. The owner of each creature card revealed this way with the lowest converted mana cost puts it onto the battlefield.
        getSpellAbility().addEffect(new StrongholdGambitEffect());
    }

    private StrongholdGambit(final StrongholdGambit card) {
        super(card);
    }

    @Override
    public StrongholdGambit copy() {
        return new StrongholdGambit(this);
    }
}

class StrongholdGambitEffect extends OneShotEffect {

    public StrongholdGambitEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Each player chooses a card in their hand. Then each player reveals their chosen card. The owner of each creature card revealed this way with the lowest mana value puts it onto the battlefield";
    }

    public StrongholdGambitEffect(final StrongholdGambitEffect effect) {
        super(effect);
    }

    @Override
    public StrongholdGambitEffect copy() {
        return new StrongholdGambitEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller != null && sourceObject != null) {
            Map<UUID, UUID> chosenCards = new LinkedHashMap<>();
            for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                Player player = game.getPlayer(playerId);
                if (player != null && !player.getHand().isEmpty()) {
                    TargetCardInHand target = new TargetCardInHand();
                    if (player.choose(Outcome.Benefit, target, source, game)) {
                        chosenCards.put(playerId, target.getFirstTarget());
                    }
                }
            }
            int lowestCMC = Integer.MAX_VALUE;
            for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                Player player = game.getPlayer(playerId);
                if (player != null && chosenCards.containsKey(playerId)) {
                    Card card = game.getCard(chosenCards.get(playerId));
                    if (card != null) {
                        Cards cardsToReveal = new CardsImpl(card);
                        player.revealCards(sourceObject.getIdName() + " (" + player.getName() + ')', cardsToReveal, game);
                        if (card.isCreature(game)
                                && lowestCMC > card.getManaValue()) {
                            lowestCMC = card.getManaValue();
                        }
                    }
                }
            }
            if (lowestCMC < Integer.MAX_VALUE) {
                Cards creaturesToBattlefield = new CardsImpl();
                for (UUID playerId : game.getState().getPlayerList(controller.getId())) {
                    Player player = game.getPlayer(playerId);
                    if (player != null && chosenCards.containsKey(playerId)) {
                        Card card = game.getCard(chosenCards.get(playerId));
                        if (card != null) {
                            if (card.isCreature(game)
                                    && lowestCMC == card.getManaValue()) {
                                creaturesToBattlefield.add(card);
                            }
                        }
                    }
                }
                controller.moveCards(creaturesToBattlefield.getCards(game), Zone.BATTLEFIELD, source, game, false, false, true, null);
            }
            return true;
        }
        return false;
    }
}
