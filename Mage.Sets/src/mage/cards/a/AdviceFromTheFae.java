
package mage.cards.a;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerIdPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;

/**
 *
 * @author jeffwadsworth
 */
public final class AdviceFromTheFae extends CardImpl {

    public AdviceFromTheFae(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{2/U}{2/U}{2/U}");

        // <i>({2U} can be paid with any two mana or with {U}. This card's converted mana cost is 6.)</i>
        // Look at the top five cards of your library. If you control more creatures than each other player, put two of those cards into your hand. Otherwise, put one of them into your hand. Then put the rest on the bottom of your library in any order.
        this.getSpellAbility().addEffect(new AdviceFromTheFaeEffect());

    }

    private AdviceFromTheFae(final AdviceFromTheFae card) {
        super(card);
    }

    @Override
    public AdviceFromTheFae copy() {
        return new AdviceFromTheFae(this);
    }
}

class AdviceFromTheFaeEffect extends OneShotEffect {

    public AdviceFromTheFaeEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Look at the top five cards of your library. If you control more creatures than each other player, put two of those cards into your hand. Otherwise, put one of them into your hand. Then put the rest on the bottom of your library in any order";
    }

    public AdviceFromTheFaeEffect(final AdviceFromTheFaeEffect effect) {
        super(effect);
    }

    @Override
    public AdviceFromTheFaeEffect copy() {
        return new AdviceFromTheFaeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject mageObject = game.getObject(source);
        if (controller != null && mageObject != null) {
            Set<Card> topCards = controller.getLibrary().getTopCards(game, 5);
            Cards cardsFromLibrary = new CardsImpl();
            for (Card card : topCards) {
                cardsFromLibrary.add(card);
            }
            controller.lookAtCards(mageObject.getIdName(), cardsFromLibrary, game);
            int max = 0;
            for (UUID playerId : game.getState().getPlayersInRange(controller.getId(), game)) {
                FilterCreaturePermanent filter = new FilterCreaturePermanent();
                filter.add(new ControllerIdPredicate(playerId));
                if (!Objects.equals(playerId, controller.getId())) {
                    if (max < game.getBattlefield().countAll(filter, playerId, game)) {
                        max = game.getBattlefield().countAll(filter, playerId, game);
                    }
                }
            }
            boolean moreCreatures = game.getBattlefield().countAll(new FilterControlledCreaturePermanent(), controller.getId(), game) > max;
            TargetCard target = new TargetCard(moreCreatures ? 2 : 1, Zone.LIBRARY, new FilterCard());
            if (controller.choose(Outcome.DrawCard, cardsFromLibrary, target, game)) {
                cardsFromLibrary.removeAll(target.getTargets());
                controller.moveCards(new CardsImpl(target.getTargets()), Zone.HAND, source, game);
            }
            controller.putCardsOnBottomOfLibrary(cardsFromLibrary, game, source, true);
            return true;
        }
        return false;
    }
}
