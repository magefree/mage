package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.constants.Duration;
import mage.constants.Layer;
import mage.constants.Outcome;
import mage.constants.SubLayer;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;

public class LookAtCardsExiledWithThisEffect extends ContinuousEffectImpl {

    public LookAtCardsExiledWithThisEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "You may look at cards exiled with {this}";
    }

    private LookAtCardsExiledWithThisEffect(final LookAtCardsExiledWithThisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null || !source.isControlledBy(controller.getId())) {
            return false;
        }
        int zcc = CardUtil.getActualSourceObjectZoneChangeCounter(game, source);
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, zcc));
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        exile.letPlayerSeeCards(controller.getId(), exile.getCards(game));
        return true;
    }

    @Override
    public LookAtCardsExiledWithThisEffect copy() {
        return new LookAtCardsExiledWithThisEffect(this);
    }
}
