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
import mage.abilities.Ability;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantAttackAnyPlayerAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.filter.common.FilterCreaturePermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.players.Player;
import mage.target.TargetPlayer;

/**
 *
 * @author LevelX2
 */
public class OrimsChant extends CardImpl {

    public OrimsChant(UUID ownerId) {
        super(ownerId, 11, "Orim's Chant", Rarity.RARE, new CardType[]{CardType.INSTANT}, "{W}");
        this.expansionSetCode = "PLS";

        // Kicker {W} (You may pay an additional {W} as you cast this spell.)
        this.addAbility(new KickerAbility("{W}"));

        // Target player can't cast spells this turn.
        this.getSpellAbility().addTarget(new TargetPlayer());
        this.getSpellAbility().addEffect(new OrimsChantCantCastEffect());

        // If Orim's Chant was kicked, creatures can't attack this turn.
        this.getSpellAbility().addEffect(new OrimsChantEffect());
    }

    public OrimsChant(final OrimsChant card) {
        super(card);
    }

    @Override
    public OrimsChant copy() {
        return new OrimsChant(this);
    }

}

class OrimsChantCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public OrimsChantCantCastEffect() {
        super(Duration.EndOfTurn, Outcome.Benefit);
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
    public boolean checksEventType(GameEvent event, Game game) {
        return GameEvent.EventType.CAST_SPELL.equals(event.getType());
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return event.getPlayerId().equals(getTargetPointer().getFirst(game, source));
    }
}

class OrimsChantEffect extends OneShotEffect {

    public OrimsChantEffect() {
        super(Outcome.Benefit);
        this.staticText = "If {this} was kicked, creatures can't attack this turn";
    }

    public OrimsChantEffect(final OrimsChantEffect effect) {
        super(effect);
    }

    @Override
    public OrimsChantEffect copy() {
        return new OrimsChantEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null && KickedCondition.getInstance().apply(game, source)) {
            game.addEffect(new CantAttackAnyPlayerAllEffect(Duration.EndOfTurn, new FilterCreaturePermanent("creatures")), source);
            return true;
        }
        return false;
    }
}
