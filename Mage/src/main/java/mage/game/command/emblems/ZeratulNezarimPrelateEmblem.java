
package mage.game.command.emblems;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.AsThoughEffectImpl;
import mage.constants.AsThoughEffectType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.command.Emblem;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author NinthWorld
 */
public class ZeratulNezarimPrelateEmblem extends Emblem {

    public static final String EXILE_KEY = "Zeratul, Nezarim Prelate";

    public ZeratulNezarimPrelateEmblem() {
        this.setName("Emblem Zeratul");
        this.getAbilities().add(new SimpleStaticAbility(Zone.COMMAND, new ZeratulNezarimPrelateEmblemEffect()));
        this.setExpansionSetCodeForImage("DDSC");
    }
}

// From ColfenorsPlansPlayCardEffect
class ZeratulNezarimPrelateEmblemEffect extends AsThoughEffectImpl {

    public ZeratulNezarimPrelateEmblemEffect() {
        super(AsThoughEffectType.PLAY_FROM_NOT_OWN_HAND_ZONE, Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "You may play cards exiled with Zeratul, Nezarim Prelate";
    }

    public ZeratulNezarimPrelateEmblemEffect(final ZeratulNezarimPrelateEmblemEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public ZeratulNezarimPrelateEmblemEffect copy() {
        return new ZeratulNezarimPrelateEmblemEffect(this);
    }

    @Override
    public boolean applies(UUID objectId, Ability source, UUID affectedControllerId, Game game) {
        if (affectedControllerId.equals(source.getControllerId()) && game.getState().getZone(objectId) == Zone.EXILED) {
            ExileZone zone = game.getExile().getExileZone(CardUtil.getExileZoneId(ZeratulNezarimPrelateEmblem.EXILE_KEY, game));
            return zone != null && zone.contains(objectId);
        }
        return false;
    }
}
