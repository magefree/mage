
package mage.cards.f;

import java.util.List;
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
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author TheElk801
 */
public final class FinalParting extends CardImpl {

    public FinalParting(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.SORCERY}, "{3}{B}{B}");

        // Search your library for two cards. Put one into your hand and the other into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new FinalPartingEffect());
    }

    public FinalParting(final FinalParting card) {
        super(card);
    }

    @Override
    public FinalParting copy() {
        return new FinalParting(this);
    }
}

class FinalPartingEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put into your hand");

    public FinalPartingEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for two cards. Put one into your hand and the other into your graveyard. Then shuffle your library";
    }

    public FinalPartingEffect(final FinalPartingEffect effect) {
        super(effect);
    }

    @Override
    public FinalPartingEffect copy() {
        return new FinalPartingEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            // Unlike Jarad's Orders, which this mostly copies, you can't fail to find
            TargetCardInLibrary target = new TargetCardInLibrary(2, 2, new FilterCard());
            if (controller.searchLibrary(target, game)) {
                if (!target.getTargets().isEmpty()) {
                    Cards searched = new CardsImpl();
                    for (UUID cardId : (List<UUID>) target.getTargets()) {
                        Card card = controller.getLibrary().getCard(cardId, game);
                        searched.add(card);
                    }
                    if (target.getTargets().size() == 2) {
                        TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                        controller.choose(Outcome.Benefit, searched, target2, game);
                        Card card = searched.get(target2.getFirstTarget(), game);
                        controller.moveCards(card, Zone.HAND, source, game);
                        searched.remove(card);
                        card = searched.getCards(game).iterator().next();
                        controller.moveCards(card, Zone.GRAVEYARD, source, game);
                    } else if (target.getTargets().size() == 1) {
                        Card card = searched.getCards(game).iterator().next();
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
