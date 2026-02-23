package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

/**
 * @author TheElk801
 */
public class ReturnExiledCardToHandEffect extends OneShotEffect {

    public ReturnExiledCardToHandEffect() {
        super(Outcome.Benefit);
        staticText = "return the exiled card to its owner's hand";
    }

    private ReturnExiledCardToHandEffect(final ReturnExiledCardToHandEffect effect) {
        super(effect);
    }

    @Override
    public ReturnExiledCardToHandEffect copy() {
        return new ReturnExiledCardToHandEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        ExileZone exileZone = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source));
        return player != null && exileZone != null && player.moveCards(exileZone, Zone.HAND, source, game);
    }
}
