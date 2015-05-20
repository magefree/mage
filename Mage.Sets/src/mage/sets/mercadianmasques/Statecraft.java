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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class Statecraft extends CardImpl {

    public Statecraft(UUID ownerId) {
        super(ownerId, 106, "Statecraft", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{U}");
        this.expansionSetCode = "MMQ";


        // Prevent all combat damage that would be dealt to and dealt by creatures you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new StatecraftPreventionEffect()));
    }

    public Statecraft(final Statecraft card) {
        super(card);
    }

    @Override
    public Statecraft copy() {
        return new Statecraft(this);
    }
}

class StatecraftPreventionEffect extends PreventionEffectImpl {
    
    private static final FilterControlledCreaturePermanent filter = new FilterControlledCreaturePermanent();
    
    StatecraftPreventionEffect() {
        super(Duration.WhileOnBattlefield, Integer.MAX_VALUE, true);
        this.staticText = "Prevent all combat damage that would be dealt to and dealt by creatures you control";
    }

    StatecraftPreventionEffect(final StatecraftPreventionEffect effect) {
        super(effect);
    }

    @Override
    public StatecraftPreventionEffect copy() {
        return new StatecraftPreventionEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (super.applies(event, source, game)) {
            Permanent sourcePermanent = game.getPermanent(event.getSourceId());
            if (sourcePermanent != null && filter.match(sourcePermanent, source.getSourceId(), source.getControllerId(), game)) {
                return true;
            }
            Permanent targetPermanent = game.getPermanent(event.getTargetId());
            if (targetPermanent != null && filter.match(targetPermanent, source.getSourceId(), source.getControllerId(), game)) {
                return true;
            }
        }
        return false;
    }
}
