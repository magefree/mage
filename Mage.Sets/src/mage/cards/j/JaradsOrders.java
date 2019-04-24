
package mage.cards.j;

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
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author LevelX2
 */
public final class JaradsOrders extends CardImpl {

    public JaradsOrders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2}{B}{G}");

        // Search your library for up to two creature cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new JaradsOrdersEffect());
    }

    public JaradsOrders(final JaradsOrders card) {
        super(card);
    }

    @Override
    public JaradsOrders copy() {
        return new JaradsOrders(this);
    }
}
class JaradsOrdersEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put into your hand");

    public JaradsOrdersEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two creature cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle your library";
    }

    public JaradsOrdersEffect(final JaradsOrdersEffect effect) {
        super(effect);
    }

    @Override
    public JaradsOrdersEffect copy() {
        return new JaradsOrdersEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {        
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            TargetCardInLibrary target = new TargetCardInLibrary(0, 2, new FilterCreatureCard("creature cards"));
            if (controller.searchLibrary(target, source, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards revealed = new CardsImpl();
                    for (UUID cardId: target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        revealed.add(card);
                    }
                    controller.revealCards("Jarad's Orders", revealed, game);
                    if (target.getTargets().size() == 2) {
                        TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                        controller.choose(Outcome.Benefit, revealed, target2, game);
                        Card card = revealed.get(target2.getFirstTarget(), game);
                        controller.moveCards(card, Zone.HAND, source, game);
                        revealed.remove(card);
                        card = revealed.getCards(game).iterator().next();
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    } else if (target.getTargets().size() == 1) {
                        Card card = revealed.getCards(game).iterator().next();
                        controller.moveCards(card, Zone.HAND, source, game);
                    }

                }
                controller.shuffleLibrary(source, game);
                return true;
            }
            controller.shuffleLibrary(source, game);
        }
        return false;

    }

}
