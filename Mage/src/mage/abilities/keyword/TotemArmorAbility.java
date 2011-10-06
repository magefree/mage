package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

public class TotemArmorAbility extends SimpleStaticAbility {
    public TotemArmorAbility() {
        super(Constants.Zone.BATTLEFIELD, new TotemArmorEffect());
    }

    public TotemArmorAbility(final TotemArmorAbility ability) {
        super(ability);
    }

    @Override
    public SimpleStaticAbility copy() {
        return new TotemArmorAbility(this);
    }

    @Override
    public String getRule() {
        return "Totem Armor";
    }
}

class TotemArmorEffect extends ReplacementEffectImpl<TotemArmorEffect> {
    TotemArmorEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
    }

    TotemArmorEffect(final TotemArmorEffect effect) {
        super(effect);
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (sourcePermanent != null) {
            Permanent equipedPermanent = game.getPermanent(event.getTargetId());
            if (equipedPermanent != null) {
                equipedPermanent.removeAllDamage(game);
                sourcePermanent.destroy(source.getSourceId(), game, false);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() == GameEvent.EventType.DESTROY_PERMANENT) {
            Permanent sourcePermanent = game.getPermanent(source.getSourceId());
            if (sourcePermanent != null && event.getTargetId().equals(sourcePermanent.getAttachedTo())) {
                return true;
            }
        }
        return false;
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
