package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

/**
 * @author magenoxx_at_gmail.com
 */
public class PutCreatureOnBattlefieldEffect extends OneShotEffect {

    private final FilterCreatureCard filter;

    public PutCreatureOnBattlefieldEffect() {
        this(new FilterCreatureCard("a creature card"));
    }

    public PutCreatureOnBattlefieldEffect(FilterCreatureCard filter) {
        super(Outcome.PutCreatureInPlay);
        this.filter = filter;
    }

    public PutCreatureOnBattlefieldEffect(final PutCreatureOnBattlefieldEffect effect) {
        super(effect);
        this.filter = effect.filter.copy();
    }

    @Override
    public PutCreatureOnBattlefieldEffect copy() {
        return new PutCreatureOnBattlefieldEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        String choiceText = "Put " + filter.getMessage() + " from your hand onto the battlefield?";
        if (player == null || !player.chooseUse(Outcome.PutCreatureInPlay, choiceText, source, game)) {
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
