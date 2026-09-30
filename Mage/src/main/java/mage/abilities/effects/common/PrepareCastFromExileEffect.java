package mage.abilities.effects.common;

import mage.abilities.Ability;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.cards.Card;
import mage.cards.PreparedSpellCopy;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.permanent.Permanent;

import java.util.UUID;

/**
 * Cast permission for the prepare-spell part of a linked copy in exile.
 *
 * @author nandmp
 */
public class PrepareCastFromExileEffect extends AsThoughEffectImpl {

    private final UUID copyId;
    private final UUID permanentId;

    public PrepareCastFromExileEffect(UUID copyId, UUID permanentId) {
        super(AsThoughEffectType.CAST_FROM_NOT_OWN_HAND_ZONE, Duration.Custom, Outcome.Benefit);
        this.copyId = copyId;
        this.permanentId = permanentId;
        staticText = "its controller may cast the prepared copy from exile because this permanent is prepared";
    }

    protected PrepareCastFromExileEffect(PrepareCastFromExileEffect effect) {
        super(effect);
        this.copyId = effect.copyId;
        this.permanentId = effect.permanentId;
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
        Card copy = game.getCard(copyId);
        Permanent permanent = game.getPermanent(permanentId);
        if (copy == null || permanent == null || !permanent.isPrepared()
                || game.getState().getZone(copyId) != Zone.EXILED) {
            discard();
            return false;
        }
        return copy instanceof PreparedSpellCopy
                && objectId.equals(copyId)
                && affectedControllerId.equals(permanent.getControllerId());
    }
}
