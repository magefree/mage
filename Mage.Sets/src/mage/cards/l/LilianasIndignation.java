
package mage.cards.l;

import java.util.Set;
import java.util.UUID;
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
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public final class LilianasIndignation extends CardImpl {

    public LilianasIndignation(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{X}{B}");

        // Put the top X cards of your library into your graveyard. Target player loses 2 life for each creature card put into your graveyard this way.
        this.getSpellAbility().addEffect(new LilianasIndignationEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public LilianasIndignation(final LilianasIndignation card) {
        super(card);
    }

    @Override
    public LilianasIndignation copy() {
        return new LilianasIndignation(this);
    }
}

class LilianasIndignationEffect extends OneShotEffect {

    public LilianasIndignationEffect() {
        super(Outcome.LoseLife);
        this.staticText = "Put the top X cards of your library into your graveyard. Target player loses 2 life for each creature card put into your graveyard this way";
    }

    public LilianasIndignationEffect(final LilianasIndignationEffect effect) {
        super(effect);
    }

    @Override
    public LilianasIndignationEffect copy() {
        return new LilianasIndignationEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            int x = source.getManaCostsToPay().getX();
            if (x > 0) {
                Cards cardsToGraveyard = new CardsImpl();
                cardsToGraveyard.addAll(controller.getLibrary().getTopCards(game, x));
                if (!cardsToGraveyard.isEmpty()) {
                    Set<Card> movedCards = controller.moveCardsToGraveyardWithInfo(cardsToGraveyard.getCards(game), source, game, Zone.LIBRARY);
                    Cards cardsMoved = new CardsImpl();
                    cardsMoved.addAll(movedCards);
                    int creatures = cardsMoved.count(new FilterCreatureCard(), game);
                    if (creatures > 0) {
                        Player targetPlayer = game.getPlayer(getTargetPointer().getFirst(game, source));
                        if (targetPlayer != null) {
                            targetPlayer.loseLife(creatures * 2, game, false);
                        }

                    }
                }
            }
            return true;
        }
        return false;
    }
}
