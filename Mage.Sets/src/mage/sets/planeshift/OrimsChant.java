/*
* Copyright 2010 BetaSteward_at_googlemail.com. All rights reserved.
*
* Redistribution and use in source and binary forms, with or without modification, are
* permitted provided that the following conditions are met:
*
*    1. Redistributions of source code must retain the above copyright notice, this list of
*       conditions and the following disclaimer.
*
*    2. Redistributions in binary form must reproduce the above copyright notice, this list
*       of conditions and the following disclaimer in the documentation and/or other materials
*       provided with the distribution.
*
* THIS SOFTWARE IS PROVIDED BY BetaSteward_at_googlemail.com ``AS IS'' AND ANY EXPRESS OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
* FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL BetaSteward_at_googlemail.com OR
* CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
* CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
* SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
* ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
* NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
* ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*
* The views and conclusions contained in the software and documentation are those of the
* authors and should not be interpreted as representing official policies, either expressed
* or implied, of BetaSteward_at_googlemail.com.
*/

package mage.sets.planeshift;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class OrimsChant extends CardImpl<OrimsChant> {

    public OrimsChant(UUID ownerId) {
        super(ownerId, 11, "Orim's Chant", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "PLS";
        this.color.setWhite(true);

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));

        // Target player can't cast spells this turn.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new OrimsChantCantCastEffect());

        // If Orim's Chant was kicked, creatures can't attack this turn.
        this.getSpellAbility().addEffect(new OrimsChantCantAttackEffect());
    }

    public OrimsChant(final OrimsChant card) {
        super(card);
    }

    @Override
    public OrimsChant copy() {
        return new OrimsChant(this);
    }

}

class OrimsChantCantCastEffect extends ReplacementEffectImpl<OrimsChantCantCastEffect> {

    public OrimsChantCantCastEffect() {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = "Target player can't cast spells this turn";
    }

    public OrimsChantCantCastEffect(final OrimsChantCantCastEffect effect) {
        super(effect);
    }

    @Override
    public OrimsChantCantCastEffect copy() {
        return new OrimsChantCantCastEffect(this);
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
        if (event.getType() == GameEvent.EventType.CAST_SPELL ) {
            Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
            if (player != null && player.getId().equals(event.getPlayerId())) {
                return true;
            }
        }
        return false;
    }
}

class OrimsChantCantAttackEffect extends ReplacementEffectImpl<OrimsChantCantAttackEffect> {

    private static final String effectText = "If Orim's Chant was kicked, creatures can't attack this turn";

    OrimsChantCantAttackEffect ( ) {
        super(Constants.Duration.EndOfTurn, Constants.Outcome.Benefit);
        staticText = effectText;
    }

    OrimsChantCantAttackEffect ( OrimsChantCantAttackEffect effect ) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        throw new UnsupportedOperationException("Not supported.");
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER) {
            return true;
        }
        return false;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if ( event.getType() == GameEvent.EventType.DECLARE_ATTACKER && KickedCondition.getInstance().apply(game, source)) {
            return true;
        }
        return false;
    }

    @Override
    public OrimsChantCantAttackEffect copy() {
        return new OrimsChantCantAttackEffect(this);
    }

}
