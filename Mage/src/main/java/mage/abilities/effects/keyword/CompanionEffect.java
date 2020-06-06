package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;

/*
 * @author emerald000
 */
public class CompanionEffect extends OneShotEffect {

    public CompanionEffect() {
        super(Outcome.DrawCard);
        staticText = "put {this} into your hand";
    }

    private CompanionEffect(final CompanionEffect effect) {
        super(effect);
    }

    @Override
    public CompanionEffect copy() {
        return new CompanionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        Card card = (Card) source.getSourceObjectIfItStillExists(game);
        if (controller != null && card != null && game.getState().getZone(card.getId()) == Zone.OUTSIDE) {
            return controller.moveCards(card, Zone.HAND, source, game);
        }
        return false;
    }
}
