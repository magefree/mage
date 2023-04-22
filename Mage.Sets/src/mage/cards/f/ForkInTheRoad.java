
package mage.cards.f;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class ForkInTheRoad extends CardImpl {

    public ForkInTheRoad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{1}{G}");

        // Search your library for up to two basic land cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle your library.
        this.getSpellAbility().addEffect(new ForkInTheRoadEffect());
    }

    private ForkInTheRoad(final ForkInTheRoad card) {
        super(card);
    }

    @Override
    public ForkInTheRoad copy() {
        return new ForkInTheRoad(this);
    }
}

class ForkInTheRoadEffect extends OneShotEffect {

    protected static final FilterCard filter = new FilterCard("card to put into your hand");

    public ForkInTheRoadEffect() {
        super(Outcome.PutLandInPlay);
        staticText = "Search your library for up to two basic land cards and reveal them. Put one into your hand and the other into your graveyard. Then shuffle";
    }

    public ForkInTheRoadEffect(final ForkInTheRoadEffect effect) {
        super(effect);
    }

    @Override
    public ForkInTheRoadEffect copy() {
        return new ForkInTheRoadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source);
        if (controller == null || sourceObject == null) {
            return false;
        }
        TargetCardInLibrary target = new TargetCardInLibrary(0, 2, StaticFilters.FILTER_CARD_BASIC_LAND);
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards revealed = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = game.getCard(cardId);
                    if (card != null) {
                        revealed.add(card);
                    }
                }
                controller.revealCards(sourceObject.getIdName(), revealed, game);
                if (!target.getTargets().isEmpty()) {
                    TargetCard target2 = new TargetCard(Zone.LIBRARY, filter);
                    controller.choose(Outcome.Benefit, revealed, target2, source, game);
                    Card card = revealed.get(target2.getFirstTarget(), game);
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                        revealed.remove(card);
                    }
                    if (!revealed.isEmpty()) {
                        card = revealed.getCards(game).iterator().next();
                        if (card != null) {
                            controller.moveCards(card, Zone.GRAVEYARD, source, game);
                        }
                    }
                } else if (target.getTargets().size() == 1 && !revealed.isEmpty()) {
                    Card card = revealed.getCards(game).iterator().next();
                    if (card != null) {
                        controller.moveCards(card, Zone.HAND, source, game);
                    }
                }

            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;

    }

}
