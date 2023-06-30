
package mage.cards.w;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.ExileSourceCost;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

/**
 *
 * @author jeffwadsworth
 */
public final class WatchersOfTheDead extends CardImpl {

    public WatchersOfTheDead(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");
        this.subtype.add(SubType.CAT);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Exile Watchers of the Dead: Each opponent chooses 2 cards in their graveyard and exiles the rest.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new WatchersOfTheDeadEffect(), new ExileSourceCost()));

    }

    private WatchersOfTheDead(final WatchersOfTheDead card) {
        super(card);
    }

    @Override
    public WatchersOfTheDead copy() {
        return new WatchersOfTheDead(this);
    }
}

class WatchersOfTheDeadEffect extends OneShotEffect {

    public WatchersOfTheDeadEffect() {
        super(Outcome.Benefit);
        this.staticText = "Each opponent chooses 2 cards in their graveyard and exiles the rest";
    }

    public WatchersOfTheDeadEffect(final WatchersOfTheDeadEffect effect) {
        super(effect);
    }

    @Override
    public WatchersOfTheDeadEffect copy() {
        return new WatchersOfTheDeadEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getLastKnownInformation(source.getSourceId(), Zone.BATTLEFIELD);
        if (controller != null) {
            for (UUID opponentId : game.getState().getPlayersInRange(controller.getId(), game)) {
                Player opponent = game.getPlayer(opponentId);
                if (opponent != null
                        && !opponent.equals(controller)) {
                    TargetCard target = new TargetCardInYourGraveyard(2, 2, new FilterCard());
                    target.setNotTarget(true);
                    Cards cardsInGraveyard = opponent.getGraveyard();
                    if (cardsInGraveyard.size() > 1) {
                        opponent.choose(outcome, cardsInGraveyard, target, source, game);
                        for (Card cardInGraveyard : cardsInGraveyard.getCards(game)) {
                            if (!target.getTargets().contains(cardInGraveyard.getId())) {
                                opponent.moveCardToExileWithInfo(cardInGraveyard, CardUtil.getCardExileZoneId(game, source.getId()),
                                        sourceObject.getLogName(), source, game, Zone.GRAVEYARD, true);
                            }
                        }
                    }

                }
            }
            return true;
        }
        return false;
    }
}
