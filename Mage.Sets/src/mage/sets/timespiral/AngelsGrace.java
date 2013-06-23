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
package mage.sets.timespiral;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.SplitSecondAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class AngelsGrace extends CardImpl<AngelsGrace> {

    public AngelsGrace(UUID ownerId) {
        super(ownerId, 3, "Angel's Grace", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "TSP";

        this.color.setWhite(true);

        // Split second
        this.addAbility(SplitSecondAbility.getInstance());
        
        // You can't lose the game this turn and your opponents can't win the game this turn. Until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead.
        this.getSpellAbility().addEffect(new AngelsGraceEffect());
        this.getSpellAbility().addEffect(new AngelsGraceReplacementEffect());
    }

    public AngelsGrace(final AngelsGrace card) {
        super(card);
    }

    @Override
    public AngelsGrace copy() {
        return new AngelsGrace(this);
    }
}

class AngelsGraceEffect extends ReplacementEffectImpl<AngelsGraceEffect> {

    public AngelsGraceEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "You can't lose the game this turn and your opponents can't win the game this turn";
    }

    public AngelsGraceEffect(final AngelsGraceEffect effect) {
        super(effect);
    }

    @Override
    public AngelsGraceEffect copy() {
        return new AngelsGraceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return true;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ((event.getType() == EventType.WINS && game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) ||
            (event.getType() == EventType.LOSES && event.getPlayerId().equals(source.getControllerId()))) {
            return true;
        }
        return false;
    }

}

class AngelsGraceReplacementEffect extends ReplacementEffectImpl<AngelsGraceReplacementEffect> {

    public AngelsGraceReplacementEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
        staticText = "Until end of turn, damage that would reduce your life total to less than 1 reduces it to 1 instead";
    }

    public AngelsGraceReplacementEffect(final AngelsGraceReplacementEffect effect) {
        super(effect);
    }

    @Override
    public AngelsGraceReplacementEffect copy() {
        return new AngelsGraceReplacementEffect(this);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getType().equals(GameEvent.EventType.DAMAGE_CAUSES_LIFE_LOSS)
                && event.getPlayerId().equals(source.getControllerId())) {
            Player controller = game.getPlayer(source.getControllerId());
            if (controller != null
                    && (controller.getLife() - event.getAmount()) < 1 ) {
                event.setAmount(controller.getLife() - 1);
            }
        }
        return false;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return false;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return false;
    }

}
