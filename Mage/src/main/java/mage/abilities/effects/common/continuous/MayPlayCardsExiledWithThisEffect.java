package mage.abilities.effects.common.continuous;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.util.CardUtil;

import java.util.UUID;

public class MayPlayCardsExiledWithThisEffect extends AsThoughEffectImpl {

    public MayPlayCardsExiledWithThisEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play cards exiled with {this}";
    }

    private MayPlayCardsExiledWithThisEffect(final MayPlayCardsExiledWithThisEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public MayPlayCardsExiledWithThisEffect copy() {
        return new MayPlayCardsExiledWithThisEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            ExileZone exileZone = game.getExile().getExileZone(CardUtil.getCardExileZoneId(game, source));
            return exileZone != null && exileZone.contains(objectId);
        }
        return false;
    }
}
