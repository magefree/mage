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
package mage.sets.shadowmoor;

import java.util.UUID;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.ManaType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.ManaEvent;

/**
 *
 * @author jeffwadsworth
 */
public class ManaReflection extends CardImpl {

    public ManaReflection(UUID ownerId) {
        super(ownerId, 122, "Mana Reflection", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{G}{G}");
        this.expansionSetCode = "SHM";

        this.color.setGreen(true);

        // If you tap a permanent for mana, it produces twice as much of that mana instead.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new ManaReflectionReplacementEffect()));

    }

    public ManaReflection(final ManaReflection card) {
        super(card);
    }

    @Override
    public ManaReflection copy() {
        return new ManaReflection(this);
    }
}

class ManaReflectionReplacementEffect extends ReplacementEffectImpl {

    ManaReflectionReplacementEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Benefit);
        staticText = "If you tap a permanent for mana, it produces twice as much of that mana instead";
    }

    ManaReflectionReplacementEffect(ManaReflectionReplacementEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        Mana mana = ((ManaEvent) event).getMana();
        if (mana.getBlack() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.BLACK, mana.getBlack()* 2);
        }
        if (mana.getBlue() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.BLUE, mana.getBlue() * 2);
        }
        if (mana.getWhite() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.WHITE, mana.getWhite() * 2);
        }
        if (mana.getGreen() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.GREEN, mana.getGreen() * 2);
        }
        if (mana.getRed() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.RED, mana.getRed() * 2);
        }
        if (mana.getColorless() > 0) {
            ((ManaEvent) event).getMana().set(ManaType.COLORLESS, mana.getColorless() * 2);
        }
        return false;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TAPPED_FOR_MANA;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {       
        return event.getPlayerId().equals(source.getControllerId())
                && game.getPermanentOrLKIBattlefield(event.getSourceId()) != null;
    }

    @Override
    public ManaReflectionReplacementEffect copy() {
        return new ManaReflectionReplacementEffect(this);
    }
}