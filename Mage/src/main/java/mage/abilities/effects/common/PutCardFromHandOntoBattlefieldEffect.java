package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.common.FilterPermanentCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author magenoxx_at_gmail.com
 */
public class PutCardFromHandOntoBattlefieldEffect extends OneShotEffect {

    private final FilterCard filter;
    private final boolean useTargetController;
    private final boolean tapped;

    public PutCardFromHandOntoBattlefieldEffect() {
        this(new FilterPermanentCard("a permanent card"), false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter) {
        this(filter, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter, boolean useTargetController) {
        this(filter, useTargetController, false);
    }

    public PutCardFromHandOntoBattlefieldEffect(FilterCard filter, boolean useTargetController, boolean tapped) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
        this.useTargetController = useTargetController;
        this.tapped = tapped;
    }

    public PutCardFromHandOntoBattlefieldEffect(final PutCardFromHandOntoBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
        this.useTargetController = effect.useTargetController;
        this.tapped = effect.tapped;
    }

    @Override
    public PutCardFromHandOntoBattlefieldEffect copy() {
        return new PutCardFromHandOntoBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player;
        if (useTargetController) {
            player = game.getPlayer(getTargetPointer().getFirst(game, source));
        } else {
            player = game.getPlayer(source.getControllerId());
        }
        if (player == null) {
            return false;
        }
        if (player.chooseUse(Outcome.PutCardInPlay, "Put " + filter.getMessage() + " from your hand onto the battlefield?", source, game)) {
            TargetCardInHand target = new TargetCardInHand(filter);
            if (player.choose(Outcome.PutCardInPlay, target, source, game)) {
                Card card = game.getCard(target.getFirstTarget());
                if (card != null) {
                    return player.moveCards(card, Zone.BATTLEFIELD, source, game, tapped, false, false, null);
                }
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (this.staticText != null && !this.staticText.isEmpty()) {
            return staticText;
        }

        if (useTargetController) {
            return "that player may put " + filter.getMessage() + " from their hand onto the battlefield" + (this.tapped ? " tapped" : "");
        } else {
            return "you may put " + filter.getMessage() + " from your hand onto the battlefield" + (this.tapped ? " tapped" : "");
        }
    }
}
