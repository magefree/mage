package mage.cards.s;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.keyword.TheRingTemptsYouEffect;
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
import mage.target.Target;
import mage.target.TargetCard;
import mage.target.common.TargetOpponent;

import java.util.UUID;

/**
 *
 * @author Susucr
 */
public final class SauronsRansom extends CardImpl {

    public SauronsRansom(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{B}");

        // Choose an opponent. They look at the top four cards of your library and separate
        // them into a face-down pile and a face-up pile. Put one pile into your hand and
        // the other into your graveyard. The Ring tempts you.
        getSpellAbility().addEffect(new SauronsRansomEffect());
        getSpellAbility().addEffect(new TheRingTemptsYouEffect());
    }

    private SauronsRansom(final SauronsRansom card) {
        super(card);
    }

    @Override
    public SauronsRansom copy() {
        return new SauronsRansom(this);
    }
}

class SauronsRansomEffect extends OneShotEffect {

    public SauronsRansomEffect() {
        super(Outcome.Benefit);
        this.staticText = "Choose an opponent. They look at the top four cards of your library and separate " +
            "them into a face-down pile and a face-up pile. Put one pile into your hand and " +
            "the other into your graveyard";
    }

    public SauronsRansomEffect(final SauronsRansomEffect effect) {
        super(effect);
    }

    @Override
    public SauronsRansomEffect copy() {
        return new SauronsRansomEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Target targetOpponent = new TargetOpponent(true);
        targetOpponent.choose(Outcome.Neutral, source.getControllerId(), source.getSourceId(), source, game);
        Player opponent = game.getPlayer(targetOpponent.getFirstTarget());
        if (opponent == null) {
            return false;
        }

        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject == null) {
            return false;
        }

        Cards cards = new CardsImpl(controller.getLibrary().getTopCards(game, 4));

        TargetCard targetFaceDownPile = new TargetCard(
                0, Integer.MAX_VALUE, Zone.LIBRARY,
                new FilterCard("cards for the face-down pile"));

        opponent.choose(outcome, cards, targetFaceDownPile, source, game);
        Cards faceDownPile = new CardsImpl(targetFaceDownPile.getTargets());
        cards.removeAll(targetFaceDownPile.getTargets());

        controller.revealCards(sourceObject.getIdName() + " - cards in face-up pile", cards, game);
        game.informPlayers(opponent.getLogName() + " puts " + faceDownPile.size() + " card(s) into the face-down pile");

        boolean pileChosen =
            controller.chooseUse(
                outcome, "Choose a pile to put in your hand.", null,
                "Face-down", "Face-up", source, game);

        if (pileChosen) { // Face-down was chosen
            controller.moveCards(faceDownPile, Zone.HAND, source, game);
            controller.moveCards(cards, Zone.GRAVEYARD, source, game);
        } else { // Face-up was chosen
            controller.moveCards(faceDownPile, Zone.GRAVEYARD, source, game);
            controller.moveCards(cards, Zone.HAND, source, game);
        }

        return true;
    }
}
