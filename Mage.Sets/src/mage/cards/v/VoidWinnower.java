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
package mage.cards.v;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.RestrictionEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class VoidWinnower extends CardImpl {

    public VoidWinnower(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{9}");
        this.subtype.add("Eldrazi");

        this.power = new MageInt(11);
        this.toughness = new MageInt(9);

        // Your opponent can't cast spells with even converted mana costs. (Zero is even.)
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidWinnowerCantCastEffect()));

        // Your opponents can't block with creatures with even converted mana costs.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new VoidWinnowerCantBlockEffect()));
    }

    public VoidWinnower(final VoidWinnower card) {
        super(card);
    }

    @Override
    public VoidWinnower copy() {
        return new VoidWinnower(this);
    }
}

class VoidWinnowerCantCastEffect extends ContinuousRuleModifyingEffectImpl {

    public VoidWinnowerCantCastEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "Your opponent can't cast spells with even converted mana costs. <i>(Zero is even.)</i>";
    }

    public VoidWinnowerCantCastEffect(final VoidWinnowerCantCastEffect effect) {
        super(effect);
    }

    @Override
    public VoidWinnowerCantCastEffect copy() {
        return new VoidWinnowerCantCastEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public String getInfoMessage(Ability source, GameEvent event, Game game) {
        MageObject mageObject = game.getObject(source.getSourceId());
        if (mageObject != null) {
            return "You can't cast spells with even converted mana costs (" + mageObject.getIdName() + ").";
        }
        return null;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == EventType.CAST_SPELL_LATE;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(event.getPlayerId())) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null) {
                // the low bit will always be set on an odd number.
                return (spell.getConvertedManaCost() & 1) == 0;
            }
        }
        return false;
    }
}

class VoidWinnowerCantBlockEffect extends RestrictionEffect {

    public VoidWinnowerCantBlockEffect() {
        super(Duration.WhileOnBattlefield);
        staticText = "Your opponents can't block with creatures with even converted mana costs";
    }

    public VoidWinnowerCantBlockEffect(final VoidWinnowerCantBlockEffect effect) {
        super(effect);
    }

    @Override
    public VoidWinnowerCantBlockEffect copy() {
        return new VoidWinnowerCantBlockEffect(this);
    }

    @Override
    public boolean applies(Permanent permanent, Ability source, Game game) {
        if (game.getOpponents(source.getControllerId()).contains(permanent.getControllerId())) {
            // the low bit will always be set on an odd number.
            return (permanent.getConvertedManaCost() & 1) == 0;
        }
        return false;
    }

    @Override
    public boolean canBlock(Permanent attacker, Permanent blocker, Ability source, Game game) {
        return false;
    }
}
