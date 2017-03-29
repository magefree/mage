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
package mage.cards.c;

import mage.abilities.Ability;
import mage.abilities.common.AsEntersBattlefieldAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.ChooseOpponentEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author MarcoMarin
 */
public class CursedRack extends CardImpl {

    public CursedRack(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{4}");

        // As Cursed Rack enters the battlefield, choose an opponent.
        this.addAbility(new AsEntersBattlefieldAbility(new ChooseOpponentEffect(Outcome.Detriment)));
        // The chosen player's maximum hand size is four.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new CursedRackHandSizeEffect()));

    }

    public CursedRack(final CursedRack card) {
        super(card);
    }

    @Override
    public CursedRack copy() {
        return new CursedRack(this);
    }
}

class CursedRackHandSizeEffect extends ContinuousEffectImpl {

    public CursedRackHandSizeEffect() {
        super(Duration.WhileOnBattlefield, Layer.PlayerEffects, SubLayer.NA, Outcome.Benefit);
        staticText = "The chosen player's maximum hand size is four";
    }

    public CursedRackHandSizeEffect(final CursedRackHandSizeEffect effect) {
        super(effect);
    }

    @Override
    public CursedRackHandSizeEffect copy() {
        return new CursedRackHandSizeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID playerId = (UUID) game.getState().getValue(source.getSourceId() + ChooseOpponentEffect.VALUE_KEY);
        Player opponent = game.getPlayer(playerId);
        if (opponent != null) {
            opponent.setMaxHandSize(4);
            return true;
        }
        return false;
    }
}
