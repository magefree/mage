package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.cards.Card;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInHand;

public class BrainstormEffect extends OneShotEffect {

    public BrainstormEffect() {
        super(Outcome.DrawCard);
        staticText = "draw three cards, then put two cards from your hand on top of your library in any order";
    }

    public BrainstormEffect(final BrainstormEffect effect) {
        super(effect);
    }

    @Override
    public BrainstormEffect copy() {
        return new BrainstormEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player != null) {
            player.drawCards(3, source, game);
            putOnLibrary(player, source, game);
            putOnLibrary(player, source, game);
            return true;
        }
        return false;
    }

    private boolean putOnLibrary(Player player, Ability source, Game game) {
        TargetCardInHand target = new TargetCardInHand();
        if (target.canChoose(player.getId(), source, game)) {
            player.chooseTarget(Outcome.ReturnToHand, target, source, game);
            Card card = player.getHand().get(target.getFirstTarget(), game);
            if (card != null) {
                return player.moveCardToLibraryWithInfo(card, source, game, Zone.HAND, true, false);
            }
        }
        return false;
    }
}
