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
package mage.cards.w;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author TheElk801
 */
public final class WellLaidPlans extends CardImpl {

    public WellLaidPlans(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT}, "{2}{U}");

        // Prevent all damage that would be dealt to a creature by another creature if they share a color.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new WellLaidPlansPreventionEffect()));
    }

    public WellLaidPlans(final WellLaidPlans card) {
        super(card);
    }

    @Override
    public WellLaidPlans copy() {
        return new WellLaidPlans(this);
    }
}

class WellLaidPlansPreventionEffect extends PreventionEffectImpl {

    public WellLaidPlansPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, false, false);
        this.staticText = "Prevent all damage that would be dealt to a creature by another creature if they share a color";
    }

    public WellLaidPlansPreventionEffect(final WellLaidPlansPreventionEffect effect) {
        super(effect);
    }

    @Override
    public WellLaidPlansPreventionEffect copy() {
        return new WellLaidPlansPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType() != GameEvent.EventType.DAMAGE_CREATURE) {
            return false;
        }
        Permanent damager = game.getPermanentOrLKIBattlefield(event.getSourceId());
        Permanent damaged = game.getPermanentOrLKIBattlefield(event.getTargetId());
        if (damager == null || damaged == null
                || !damager.isCreature() || !damaged.isCreature()) {
            return false;
        }
        return !damager.getColor(game).intersection(damaged.getColor(game)).isColorless();
    }
}
