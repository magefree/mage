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
package mage.sets.ravnica;

import java.util.UUID;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Controllable;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author Dilnu
 */
public class LightOfSanction extends CardImpl {

    public LightOfSanction(UUID ownerId) {
        super(ownerId, 24, "Light of Sanction", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{W}{W}");
        this.expansionSetCode = "RAV";

        // Prevent all damage that would be dealt to creatures you control by sources you control.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new LightOfSanctionEffect()));
    }

    public LightOfSanction(final LightOfSanction card) {
        super(card);
    }

    @Override
    public LightOfSanction copy() {
        return new LightOfSanction(this);
    }
}

class LightOfSanctionEffect extends PreventionEffectImpl {

    public LightOfSanctionEffect() {
        super(Duration.EndOfGame);
        this.staticText = "Prevent all damage that would be dealt to creatures you control by sources you control.";
        consumable = false;
    }

    public LightOfSanctionEffect(LightOfSanctionEffect effect) {
        super(effect);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_CREATURE)) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null && permanent.getControllerId().equals(source.getControllerId())) {
                MageObject damageSource = game.getObject(event.getSourceId());
                if (damageSource instanceof Controllable) {
                    return ((Controllable) damageSource).getControllerId().equals(source.getControllerId());
                }
                else if (damageSource instanceof Card) {
                    return ((Card) damageSource).getOwnerId().equals(source.getControllerId());
                }
            }
        }
        return false;
    }

    @Override
    public LightOfSanctionEffect copy() {
        return new LightOfSanctionEffect(this);
    }
}