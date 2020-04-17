package mage.abilities.effects.keyword;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;

import java.util.UUID;

/*
 * @author emerald000
 */
public class CompanionEffect extends AsThoughEffectImpl {

    public CompanionEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "Once during the game, you may cast your chosen companion from your sideboard";
    }

    private CompanionEffect(final CompanionEffect effect) {
        super(effect);
    }

    @Override
    public CompanionEffect copy() {
        return new CompanionEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        return objectId.equals(source.getSourceId()) && affectedControllerId.equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }
}
