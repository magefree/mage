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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Duration;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;

/**
 *
 * @author LevelX2
 */
public class OrbOfDreams extends CardImpl<OrbOfDreams> {

    public OrbOfDreams(UUID ownerId) {
        super(ownerId, 156, "Orb of Dreams", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{3}");
        this.expansionSetCode = "BOK";
        
        // Permanents enter the battlefield tapped.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new OrbOfDreamsEffect()));
    }

    public OrbOfDreams(final OrbOfDreams card) {
        super(card);
    }

    @Override
    public OrbOfDreams copy() {
        return new OrbOfDreams(this);
    }
    
    private class OrbOfDreamsEffect extends ReplacementEffectImpl<OrbOfDreamsEffect> {

        OrbOfDreamsEffect() {
            super(Duration.WhileOnBattlefield, Constants.Outcome.Tap, false);
            staticText = "Permanents enter the battlefield tapped";
        }

        OrbOfDreamsEffect(final OrbOfDreamsEffect effect) {
            super(effect);
        }

        @Override
        public OrbOfDreamsEffect copy() {
            return new OrbOfDreamsEffect(this);
        }

        @Override
        public boolean replaceEvent(GameEvent event, Ability source, Game game) {
            Permanent permanent = game.getPermanent(event.getTargetId());
            if (permanent != null) {
                permanent.setTapped(true);
            }
            return false;
        }

        @Override
        public boolean applies(GameEvent event, Ability source, Game game) {
            if (event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD) {
                return true;
            }
            return false;
        }

        @Override
        public boolean apply(Game game, Ability source) {
            return false;
        }
    } 
}

