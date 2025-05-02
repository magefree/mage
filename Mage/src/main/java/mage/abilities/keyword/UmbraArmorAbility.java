package mage.abilities.keyword;

import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/*
 * 702.87. Umbra Armor
 *
 * 702.87a Umbra armor is a static ability that appears on some Auras. "Umbra armor" means "
 * If enchanted permanent would be destroyed, instead remove all damage marked on it and
 * destroy this Aura."
 *
 * @author Loki
 *
 */

public class UmbraArmorAbility extends SimpleStaticAbility {

    public UmbraArmorAbility() {
        super(Zone.BATTLEFIELD, new UmbraArmorEffect());
    }

    private UmbraArmorAbility(final UmbraArmorAbility ability) {
        super(ability);
    }

    @Override
    public UmbraArmorAbility copy() {
        return new UmbraArmorAbility(this);
    }

    @Override
    public String getRule() {
        return "umbra armor <i>(If enchanted creature would be destroyed, instead remove all damage from it and destroy this Aura.)</i>";
    }
}

class UmbraArmorEffect extends ReplacementEffectImpl {

    UmbraArmorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    private UmbraArmorEffect(final UmbraArmorEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        Permanent enchantedPermanent = game.getPermanent(event.getTargetId());
        if (sourcePermanent == null || enchantedPermanent == null) {
            return false;
        }
        enchantedPermanent.removeAllDamage(game);
        sourcePermanent.destroy(source, game, false);
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.DESTROY_PERMANENT;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = source.getSourcePermanentIfItStillExists(game);
        return sourcePermanent != null && event.getTargetId().equals(sourcePermanent.getAttachedTo());
    }

    @Override
    public UmbraArmorEffect copy() {
        return new UmbraArmorEffect(this);
    }
}
