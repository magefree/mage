
package mage.cards.f;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
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
import mage.target.common.TargetOpponent;

/**
 *
 * @author LevelX2
 */
public final class FortunesFavor extends CardImpl {

    public FortunesFavor(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.INSTANT},"{3}{U}");

        // Target opponent looks at the top four cards of your library and separates them into a face-down pile and a face-up pile. Put one pile into your hand and the other into your graveyard.
        getSpellAbility().addEffect(new FortunesFavorEffect());
        getSpellAbility().addTarget(new TargetOpponent());
    }

    private FortunesFavor(final FortunesFavor card) {
        super(card);
    }

    @Override
    public FortunesFavor copy() {
        return new FortunesFavor(this);
    }
}

class FortunesFavorEffect extends OneShotEffect {

    public FortunesFavorEffect() {
        super(Outcome.Benefit);
        this.staticText = "Target opponent looks at the top four cards of your library and separates them into a face-down pile and a face-up pile. Put one pile into your hand and the other into your graveyard";
    }

    public FortunesFavorEffect(final FortunesFavorEffect effect) {
        super(effect);
    }

    @Override
    public FortunesFavorEffect copy() {
        return new FortunesFavorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Player targetOpponent = game.getPlayer(getTargetPointer().getFirst(game, source));
        MageObject sourceObject = source.getSourceObject(game);
        if (controller != null && targetOpponent != null && sourceObject != null) {
            Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));

            TargetCard target = new TargetCard(0, Integer.MAX_VALUE, Zone.LIBRARY, new FilterCard("cards for the face-down pile"));
            targetOpponent.choose(outcome, cards, target, source, game);
            Cards faceDownPile = new CardsImpl(target.getTargets());
            cards.removeAll(target.getTargets());
            controller.revealCards(sourceObject.getIdName() + " - cards in face-up pile", cards, game);
            game.informPlayers(targetOpponent.getLogName() + " puts " + faceDownPile.size() + " card(s) into the face-down pile");
            if (controller.chooseUse(outcome, "Choose a pile to put in your hand.", null, "Face-down", "Face-up", source, game)) {
                controller.moveCards(faceDownPile, Zone.HAND, source, game);
                controller.moveCards(cards, Zone.GRAVEYARD, source, game);
            } else {
                controller.moveCards(faceDownPile, Zone.GRAVEYARD, source, game);
                controller.moveCards(cards, Zone.HAND, source, game);
            }
            return true;
        }
        return false;
    }
}
