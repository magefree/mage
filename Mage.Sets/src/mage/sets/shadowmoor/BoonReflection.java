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
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ReplacementEffectImpl;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;

/**
 *
 * @author jeffwadsworth
 *
 */
public class BoonReflection extends CardImpl<BoonReflection> {

    public BoonReflection(UUID ownerId) {
        super(ownerId, 5, "Boon Reflection", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{W}");
        this.expansionSetCode = "SHM";

        this.color.setWhite(true);

        // If you would gain life, you gain twice that much life instead.
        this.addAbility(new SimpleStaticAbility(Constants.Zone.BATTLEFIELD, new BoonReflectionEffect()));
    }

    public BoonReflection(final BoonReflection card) {
        super(card);
    }

    @Override
    public BoonReflection copy() {
        return new BoonReflection(this);
    }
}

class BoonReflectionEffect extends ReplacementEffectImpl<BoonReflectionEffect> {

    public BoonReflectionEffect() {
        super(Constants.Duration.WhileOnBattlefield, Constants.Outcome.Benefit);
        staticText = "If you would gain life, you gain twice that much life instead";
    }

    public BoonReflectionEffect(final BoonReflectionEffect effect) {
        super(effect);
    }

    @Override
    public BoonReflectionEffect copy() {
        return new BoonReflectionEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean replaceEvent(GameEvent event, Ability source, Game game) {
        return apply(game, source);
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        switch (event.getType()) {
            case GAIN_LIFE:
                if (event.getPlayerId().equals(source.getControllerId()) && (source.getControllerId() != null)) {
                    event.setAmount(event.getAmount() * 2);
                }
        }
        return false;
    }
}