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
 * 702.87. Totem Armor
 *
 * 702.87a Totem armor is a static ability that appears on some Auras. "Totem armor" means "
 * If enchanted permanent would be destroyed, instead remove all damage marked on it and
 * destroy this Aura."
 *
 * @author Loki
 *
 */

public class TotemArmorAbility extends SimpleStaticAbility {

    public TotemArmorAbility() {
        super(Zone.BATTLEFIELD, new TotemArmorEffect());
    }

    private TotemArmorAbility(final TotemArmorAbility ability) {
        super(ability);
    }

    @Override
    public TotemArmorAbility copy() {
        return new TotemArmorAbility(this);
    }

    @Override
    public String getRule() {
        return "totem armor <i>(If enchanted creature would be destroyed, instead remove all damage from it and destroy this Aura.)</i>";
    }
}

class TotemArmorEffect extends ReplacementEffectImpl {

    TotemArmorEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
    }

    private TotemArmorEffect(final TotemArmorEffect effect) {
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
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public TotemArmorEffect copy() {
        return new TotemArmorEffect(this);
    }
}
