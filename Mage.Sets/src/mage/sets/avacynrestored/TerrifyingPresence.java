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
package mage.sets.avacynrestored;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.PreventionEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author North
 */
public class TerrifyingPresence extends CardImpl<TerrifyingPresence> {

    public TerrifyingPresence(UUID ownerId) {
        super(ownerId, 196, "Terrifying Presence", Rarity.COMMON, new CardType[]{CardType.INSTANT}, "{1}{G}");
        this.expansionSetCode = "AVR";

        this.color.setGreen(true);

        // Prevent all combat damage that would be dealt by creatures other than target creature this turn.
        this.getSpellAbility().addEffect(new TerrifyingPresenceEffect());
        this.getSpellAbility().addTarget(new TargetCreaturePermanent(true));
    }

    public TerrifyingPresence(final TerrifyingPresence card) {
        super(card);
    }

    @Override
    public TerrifyingPresence copy() {
        return new TerrifyingPresence(this);
    }
}

class TerrifyingPresenceEffect extends PreventionEffectImpl<TerrifyingPresenceEffect> {

    public TerrifyingPresenceEffect() {
        super(Duration.EndOfTurn, Integer.MAX_VALUE, true);
        this.staticText = "Prevent all combat damage that would be dealt by creatures other than target creature this turn";
    }

    public TerrifyingPresenceEffect(final TerrifyingPresenceEffect effect) {
        super(effect);
    }

    @Override
    public TerrifyingPresenceEffect copy() {
        return new TerrifyingPresenceEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return super.applies(event, source, game) && !event.getSourceId().equals(getTargetPointer().getFirst(game, source));
    }
}
