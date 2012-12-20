/*
 *  Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without modification, are
 *  permitted provided that the following conditions are met:
 *
 *     1. Redistributions of source code must retain the above copyright notice, this list of
 *        conditions and the following disclaimer.
 *
 *     2. Redistributions in binary form must reproduce the above copyright notice, this list
 *        of conditions and the following disclaimer in the documentation and/or other materials
 *        provided with the distribution.
 *
 *  THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
 *  WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
 *  FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 *  CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 *  SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
 *  ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
 *  ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 *  The views and conclusions contained in the software and documentation are those of the
 *  authors and should not be interpreted as representing official policies, either expressed
 *  or implied, of BetaSteward_at_googlemail.com.
 */

package mage.abilities.keyword;

import mage.Constants;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
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
        return "Totem Armor <i>(If enchanted creature would be destroyed, instead remove all damage from it and destroy this Aura.)</i>";
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
