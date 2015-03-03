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
import mage.abilities.Ability;
import mage.abilities.effects.Effect;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.CantGainLifeTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class FlamesOfTheBloodHand extends CardImpl {

    public FlamesOfTheBloodHand(UUID ownerId) {
        super(ownerId, 101, "Flames of the Blood Hand", Rarity.UNCOMMON, new CardType[]{CardType.INSTANT}, "{2}{R}");
        this.expansionSetCode = "BOK";

        this.color.setRed(true);

        // Flames of the Blood Hand deals 4 damage to target player. The damage can't be prevented.
        this.getSpellAbility().addEffect(new DamageTargetEffect(4, false));
        // If that player would gain life this turn, that player gains no life instead.
        this.getSpellAbility().addEffect(new FlamesOfTheBloodHandReplacementEffect());
        this.getSpellAbility().addTarget(new TargetPlayer());
    }

    public FlamesOfTheBloodHand(final FlamesOfTheBloodHand card) {
        super(card);
    }

    @Override
    public FlamesOfTheBloodHand copy() {
        return new FlamesOfTheBloodHand(this);
    }
}

class FlamesOfTheBloodHandReplacementEffect extends ReplacementEffectImpl {

    public FlamesOfTheBloodHandReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "If that player would gain life this turn, that player gains no life instead";
    }

    public FlamesOfTheBloodHandReplacementEffect(final FlamesOfTheBloodHandReplacementEffect effect) {
        super(effect);
    }

    @Override
    public FlamesOfTheBloodHandReplacementEffect copy() {
        return new FlamesOfTheBloodHandReplacementEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.GAIN_LIFE;
    }    
    
    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(getTargetPointer().getFirst(game, source));
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

}