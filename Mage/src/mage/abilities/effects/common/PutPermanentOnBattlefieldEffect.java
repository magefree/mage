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
public class PutPermanentOnBattlefieldEffect extends OneShotEffect {

    private final FilterCard filter;

    public PutPermanentOnBattlefieldEffect() {
        this(new FilterPermanentCard("a permanent card"));
    }

    public PutPermanentOnBattlefieldEffect(FilterCard filter) {
        super(Outcome.PutCardInPlay);
        this.filter = filter;
    }

    public PutPermanentOnBattlefieldEffect(final PutPermanentOnBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public PutPermanentOnBattlefieldEffect copy() {
        return new PutPermanentOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        String choiceText = "Put " + filter.getMessage() + " from your hand onto the battlefield?";
        if (player == null || !player.chooseUse(Outcome.PutCardInPlay, choiceText, source, game)) {
            return false;
        }

        TargetCardInHand target = new TargetCardInHand(filter);
        if (player.choose(Outcome.PutCreatureInPlay, target, source.getSourceId(), game)) {
            Card card = game.getCard(target.getFirstTarget());
            if (card != null) {
                player.putOntoBattlefieldWithInfo(card, game, Zone.HAND, source.getSourceId());
                return true;
            }
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if(this.staticText != null && !this.staticText.isEmpty()) {
            return staticText;
        }

        return "You may put " + filter.getMessage() + " from your hand onto the battlefield";
    }
}
