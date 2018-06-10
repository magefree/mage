
package mage.cards.c;

import java.util.UUID;
import mage.ObjectColor;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ColorPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

/**
 *
 * @author jeffwadsworth
 */
public final class Conflux extends CardImpl {

    public Conflux(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.SORCERY},"{3}{W}{U}{B}{R}{G}");


        // Search your library for a white card, a blue card, a black card, a red card, and a green card. Reveal those cards and put them into your hand. Then shuffle your library.
        this.getSpellAbility().addEffect(new ConfluxEffect());

    }

    public Conflux(final Conflux card) {
        super(card);
    }

    @Override
    public Conflux copy() {
        return new Conflux(this);
    }
}

class ConfluxEffect extends OneShotEffect {

    public ConfluxEffect() {
        super(Outcome.DrawCard);
        this.staticText = "Search your library for a white card, a blue card, a black card, a red card, and a green card. Reveal those cards and put them into your hand. Then shuffle your library";
    }

    public ConfluxEffect(final ConfluxEffect effect) {
        super(effect);
    }

    @Override
    public ConfluxEffect copy() {
        return new ConfluxEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player you = game.getPlayer(source.getControllerId());
        Cards cards = new CardsImpl();
        FilterCard filterWhite = new FilterCard("white card");
        filterWhite.add(new ColorPredicate(ObjectColor.WHITE));
        FilterCard filterBlue = new FilterCard("blue card");
        filterBlue.add(new ColorPredicate(ObjectColor.BLUE));
        FilterCard filterBlack = new FilterCard("black card");
        filterBlack.add(new ColorPredicate(ObjectColor.BLACK));
        FilterCard filterRed = new FilterCard("red card");
        filterRed.add(new ColorPredicate(ObjectColor.RED));
        FilterCard filterGreen = new FilterCard("green card");
        filterGreen.add(new ColorPredicate(ObjectColor.GREEN));
        TargetCardInLibrary targetWhite = new TargetCardInLibrary(filterWhite);
        TargetCardInLibrary targetBlue = new TargetCardInLibrary(filterBlue);
        TargetCardInLibrary targetBlack = new TargetCardInLibrary(filterBlack);
        TargetCardInLibrary targetRed = new TargetCardInLibrary(filterRed);
        TargetCardInLibrary targetGreen = new TargetCardInLibrary(filterGreen);

        if (you != null && you.getLibrary().hasCards()) {
            if (you.searchLibrary(targetWhite, game)) {
                if (!targetWhite.getTargets().isEmpty()) {
                    for (UUID cardId : targetWhite.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().hasCards()) {
            if (you.searchLibrary(targetBlue, game)) {
                if (!targetBlue.getTargets().isEmpty()) {
                    for (UUID cardId : targetBlue.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().hasCards()) {
            if (you.searchLibrary(targetBlack, game)) {
                if (!targetBlack.getTargets().isEmpty()) {
                    for (UUID cardId : targetBlack.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().hasCards()) {
            if (you.searchLibrary(targetRed, game)) {
                if (!targetRed.getTargets().isEmpty()) {
                    for (UUID cardId : targetRed.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null && you.getLibrary().hasCards()) {
            if (you.searchLibrary(targetGreen, game)) {
                if (!targetGreen.getTargets().isEmpty()) {
                    for (UUID cardId : targetGreen.getTargets()) {
                        Card card = you.getLibrary().remove(cardId, game);
                        if (card != null) {
                            cards.add(card);
                        }
                    }
                }
            }
        }
        if (you != null) {
            you.revealCards("Conflux", cards, game);
            for (Card card : cards.getCards(game)) {
                card.moveToZone(Zone.HAND, source.getSourceId(), game, true);
            }
            you.shuffleLibrary(source, game);
        }
        return true;
    }
}
