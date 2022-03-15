package mage.cards.r;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterLandCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetCardInLibrary;
import mage.target.common.TargetOpponent;

import java.util.Set;
import java.util.UUID;

/**
 * @author North
 */
public final class RealmsUncharted extends CardImpl {

    public RealmsUncharted(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{2}{G}");

        // Search your library for four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new RealmsUnchartedEffect());
    }

    private RealmsUncharted(final RealmsUncharted card) {
        super(card);
    }

    @Override
    public RealmsUncharted copy() {
        return new RealmsUncharted(this);
    }
}

class RealmsUnchartedEffect extends OneShotEffect {

    public RealmsUnchartedEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for up to four land cards with different names and reveal them. An opponent chooses two of those cards. Put the chosen cards into your graveyard and the rest into your hand. Then shuffle";
    }

    public RealmsUnchartedEffect(final RealmsUnchartedEffect effect) {
        super(effect);
    }

    @Override
    public RealmsUnchartedEffect copy() {
        return new RealmsUnchartedEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        MageObject sourceObject = game.getObject(source.getSourceId());
        if (controller == null || sourceObject == null) {
            return false;
        }

        RealmsUnchartedTarget target = new RealmsUnchartedTarget();
        if (controller.searchLibrary(target, source, game)) {
            if (!target.getTargets().isEmpty()) {
                Cards cards = new CardsImpl();
                for (UUID cardId : target.getTargets()) {
                    Card card = controller.getLibrary().getCard(cardId, game);
                    if (card != null) {
                        cards.add(card);
                    }
                }
                controller.revealCards(sourceObject.getName(), cards, game);

                CardsImpl cardsToKeep = new CardsImpl();
                if (cards.size() > 2) {
                    cardsToKeep.addAll(cards);

                    Player opponent;
                    Set<UUID> opponents = game.getOpponents(controller.getId());
                    if (opponents.size() == 1) {
                        opponent = game.getPlayer(opponents.iterator().next());
                    } else {
                        Target targetOpponent = new TargetOpponent(true);
                        controller.chooseTarget(Outcome.Detriment, targetOpponent, source, game);
                        opponent = game.getPlayer(targetOpponent.getFirstTarget());
                    }
                    TargetCard targetDiscard = new TargetCard(2, Zone.LIBRARY, new FilterCard("cards to put in graveyard"));
                    if (opponent != null && opponent.choose(Outcome.Discard, cards, targetDiscard, game)) {
                        cardsToKeep.removeAll(targetDiscard.getTargets());
                        cards.removeAll(cardsToKeep);
                    }
                }
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
                controller.moveCards(cardsToKeep, Zone.HAND, source, game);
            }
            controller.shuffleLibrary(source, game);
            return true;
        }
        controller.shuffleLibrary(source, game);
        return false;
    }
}

class RealmsUnchartedTarget extends TargetCardInLibrary {

    public RealmsUnchartedTarget() {
        super(0, 4, new FilterLandCard("land cards with different names"));
    }

    public RealmsUnchartedTarget(final RealmsUnchartedTarget target) {
        super(target);
    }

    @Override
    public RealmsUnchartedTarget copy() {
        return new RealmsUnchartedTarget(this);
    }

    @Override
    public boolean canTarget(UUID playerId, UUID id, Ability source, Cards cards, Game game) {
        Card card = cards.get(id, game);
        if (card != null) {
            for (UUID targetId : this.getTargets()) {
                Card iCard = game.getCard(targetId);
                if (iCard != null && iCard.getName().equals(card.getName())) {
                    return false;
                }
            }
            return filter.match(card, playerId, game);
        }
        return false;
    }
}
