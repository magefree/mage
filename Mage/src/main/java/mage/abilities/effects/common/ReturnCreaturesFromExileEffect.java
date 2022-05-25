package mage.abilities.effects.common;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.filter.common.FilterCreatureCard;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;

public class ReturnCreaturesFromExileEffect extends OneShotEffect {

    private UUID exileId;
    private boolean byOwner;

    public ReturnCreaturesFromExileEffect(UUID exileId, boolean byOwner, String description) {
        super(Outcome.PutCardInPlay);
        this.exileId = exileId;
        this.setText(description);
        this.byOwner = byOwner;
    }


    public ReturnCreaturesFromExileEffect(final ReturnCreaturesFromExileEffect effect) {
        super(effect);
        this.exileId = effect.exileId;
    }

    @Override
    public ReturnCreaturesFromExileEffect copy() {
        return new ReturnCreaturesFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        ExileZone exile = game.getExile().getExileZone(exileId);
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && exile != null) {
            controller.moveCards(exile.getCards(new FilterCreatureCard(), game), Zone.BATTLEFIELD, source, game, false, false, this.byOwner, null);
            return true;
        }
        return false;
    }
}
