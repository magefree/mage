package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.Set;
import java.util.UUID;

/**
 * @author LevelX2
 */
public final class JaradsOrders extends CardImpl {

    public JaradsOrders(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{2}{B}{G}");

        // Search your library for up to two creature cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new JaradsOrdersEffect());
    }

    private JaradsOrders(final JaradsOrders card) {
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
        staticText = "Search your library for up to two creature cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle";
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
                    for (UUID cardId : target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        revealed.add(card);
                    }
                    controller.revealCards("Jarad's Orders", revealed, game);
                    if (target.getTargets().size() == 2) {
                        TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                        controller.choose(Outcome.Benefit, revealed, target2, source, game);
                        Card card = revealed.get(target2.getFirstTarget(), game);
                        controller.moveCards(card, Zone.HAND, source, game);
                        revealed.remove(card);
                        Set<Card> cards = revealed.getCards(game);
                        card = cards.isEmpty() ? null : cards.iterator().next();
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    } else if (target.getTargets().size() == 1) {
                        Set<Card> cards = revealed.getCards(game);
                        Card card = cards.isEmpty() ? null : cards.iterator().next();
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
