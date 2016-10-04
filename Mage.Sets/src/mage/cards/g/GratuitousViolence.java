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
package mage.cards.g;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author emerald000
 */
public class GratuitousViolence extends CardImpl {

    public GratuitousViolence(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.ENCHANTMENT},"{2}{R}{R}{R}");

        // If a creature you control would deal damage to a creature or player, it deals double that damage to that creature or player instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GratuitousViolenceReplacementEffect()));
    }

    public GratuitousViolence(final GratuitousViolence card) {
        super(card);
    }

    @Override
    public GratuitousViolence copy() {
        return new GratuitousViolence(this);
    }
}

class GratuitousViolenceReplacementEffect extends ReplacementEffectImpl {

    GratuitousViolenceReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Damage);
        staticText = "If a creature you control would deal damage to a creature or player, it deals double that damage to that creature or player instead";
    }

    GratuitousViolenceReplacementEffect(final GratuitousViolenceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public GratuitousViolenceReplacementEffect copy() {
        return new GratuitousViolenceReplacementEffect(this);
    }

    @Override    
    public boolean checksEventType(GameEvent event, Game game) {
        switch(event.getType()) {
            case DAMAGE_CREATURE:
            case DAMAGE_PLAYER:
                return true;
            default:
                return false;
        }
    }
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        Permanent permanent = game.getPermanentOrLKIBattlefield(event.getSourceId());
        return permanent != null
                && permanent.getCardType().contains(CardType.CREATURE)
                && permanent.getControllerId().equals(source.getControllerId());
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        event.setAmount(2 * event.getAmount());
        return false;
    }
}
