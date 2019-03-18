
package mage.cards.f;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.ComparisonType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.CardTypePredicate;
import mage.filter.predicate.mageobject.ConvertedManaCostPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class FiremindsForesight extends CardImpl {

    public FiremindsForesight(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{5}{U}{R}");


        // Search your library for an instant card with converted mana cost 3, reveal it,
        // and put it into your hand. Then repeat this process for instant cards with
        // converted mana costs 2 and 1. Then shuffle your library.
        this.getSpellAbility().addEffect(new FiremindsForesightSearchEffect());
    }

    public FiremindsForesight(final FiremindsForesight card) {
        super(card);
    }

    @Override
    public FiremindsForesight copy() {
        return new FiremindsForesight(this);
    }
}

class FiremindsForesightSearchEffect extends  OneShotEffect {

    public FiremindsForesightSearchEffect() {
        super(Outcome.DrawCard);
        staticText = "Search your library for an instant card with converted mana cost 3, reveal it, and put it into your hand. Then repeat this process for instant cards with converted mana costs 2 and 1. Then shuffle your library";
    }

    public FiremindsForesightSearchEffect(final FiremindsForesightSearchEffect effect) {
        super(effect);
    }

    @Override
    public FiremindsForesightSearchEffect copy() {
        return new FiremindsForesightSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card sourceCard = game.getCard(source.getSourceId());
        if (player == null || sourceCard == null) {
            return false;
        }
        int cardsCount;
        Cards cardToReveal = new CardsImpl();
        Cards cardsInLibrary = new CardsImpl();
        cardsInLibrary.addAll(player.getLibrary().getCards(game));

        for (int cmc=3; cmc > 0; cmc--) {
            FilterCard filter = new FilterCard("instant card with converted mana cost " + cmc);
            filter.add(new CardTypePredicate(CardType.INSTANT));
            filter.add(new ConvertedManaCostPredicate(ComparisonType.EQUAL_TO, cmc));


            cardsCount = cardsInLibrary.count(filter, game);
            if (cardsCount > 0) {
                TargetCardInLibrary target = new TargetCardInLibrary(0, 1, filter);
                if (player.searchLibrary(target, game)) {
                    for (UUID cardId: target.getTargets()) {
                        Card card = player.getLibrary().remove(cardId, game);
                        if (card != null){
                            card.moveToZone(Zone.HAND, source.getSourceId(), game, false);
                            game.informPlayers(sourceCard.getName()+": " + player.getLogName() + " chose " + card.getName() );
                            cardsInLibrary.remove(card);
                            cardToReveal.add(card);
                            player.revealCards(sourceCard.getName(), cardToReveal, game);
                        }
                    }
                }
            } else {
                player.lookAtCards(filter.getMessage(), cardsInLibrary, game);
            }
        }

        player.shuffleLibrary(source, game);
        return true;
    }
}