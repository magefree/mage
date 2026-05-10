package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardsImpl;
import mage.constants.PutCards;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.EnumSet;

/**
 * @author xenohedron
 */
public class ReturnCardChosenFromGraveyardEffect extends OneShotEffect {

    private final boolean optional;
    private final FilterCard filter;
    private final PutCards putCards;
    private static final EnumSet<PutCards> supported = EnumSet.of(PutCards.HAND, PutCards.BATTLEFIELD, PutCards.BATTLEFIELD_TAPPED);

    public ReturnCardChosenFromGraveyardEffect(boolean optional, FilterCard filter, PutCards putCards) {
        super(putCards.getOutcome());
        this.optional = optional;
        this.filter = filter;
        if (!supported.contains(putCards)) {
            throw new IllegalArgumentException("Unsupported PutCards in ReturnCardChosenFromGraveyardEffect: " + putCards);
        }
        this.putCards = putCards;
    }

    protected ReturnCardChosenFromGraveyardEffect(final ReturnCardChosenFromGraveyardEffect effect) {
        super(effect);
        this.optional = effect.optional;
        this.filter = effect.filter;
        this.putCards = effect.putCards;
    }

    @Override
    public ReturnCardChosenFromGraveyardEffect copy() {
        return new ReturnCardChosenFromGraveyardEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.getGraveyard().count(filter, game) < 1) {
            return true;
        }
        TargetCard target = new TargetCardInYourGraveyard(
                optional ? 0 : 1, 1, filter, true
        );
        controller.choose(outcome, target, source, game);
        putCards.moveCards(controller, new CardsImpl(target.getTargets()), source, game);
        return true;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return (optional ? "you may " : "") + "return " + CardUtil.addArticle(filter.getMessage())
                + putCards.getMessage(false, false)
                .replaceFirst("[io]nto", " to");
    }
}
