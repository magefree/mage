package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.PrepareCard;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/** Cast permission for the prepare-spell part of a linked copy in exile. */
public class PrepareCastFromExileEffect extends AsThoughEffectImpl {

    public PrepareCastFromExileEffect() {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        staticText = "its controller may cast the prepared copy from exile because this permanent is prepared";
    }

    protected PrepareCastFromExileEffect(PrepareCastFromExileEffect effect) {
        super(effect);
    }

    @Override
    public PrepareCastFromExileEffect copy() {
        return new PrepareCastFromExileEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        UUID copyId = getTargetPointer().getFirst(game, source);
        Card copy = game.getCard(copyId);
        UUID permanentId = copyId == null ? null
                : (UUID) game.getState().getValue("PreparePermanent" + copyId);
        Permanent permanent = game.getPermanent(permanentId);
        if (copy == null || permanent == null || !permanent.isPrepared()
                || game.getState().getZone(copyId) != Zone.EXILED) {
            discard();
            return false;
        }
        return copy instanceof PrepareCard
                && objectId.equals(((PrepareCard) copy).getSpellCard().getId())
                && affectedControllerId.equals(permanent.getControllerId());
    }
}
