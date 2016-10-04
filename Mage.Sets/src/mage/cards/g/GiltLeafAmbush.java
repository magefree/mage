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
package mage.cards.g;

import java.util.UUID;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.abilities.Ability;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ClashEffect;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.DeathtouchAbility;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.ElfToken;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author Styxo
 */
public class GiltLeafAmbush extends CardImpl {

    public GiltLeafAmbush(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.TRIBAL,CardType.INSTANT},"{2}{G}");
        this.subtype.add("Elf");

        // Put two 1/1 green Elf Warrior creature tokens into play. Clash with an opponent. If you win, those creatures gain deathtouch until end of turn
        this.getSpellAbility().addEffect(new GiltLeafAmbushCreateTokenEffect());
    }

    public GiltLeafAmbush(final GiltLeafAmbush card) {
        super(card);
    }

    @Override
    public GiltLeafAmbush copy() {
        return new GiltLeafAmbush(this);
    }
}

class GiltLeafAmbushCreateTokenEffect extends OneShotEffect {

    public GiltLeafAmbushCreateTokenEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "Put two 1/1 green Elf Warrior creature tokens into play. Clash with an opponent. If you win, those creatures gain deathtouch until end of turn";
    }

    public GiltLeafAmbushCreateTokenEffect(final GiltLeafAmbushCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public GiltLeafAmbushCreateTokenEffect copy() {
        return new GiltLeafAmbushCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            CreateTokenEffect effect = new CreateTokenEffect(new ElfToken(), 2);
            effect.apply(game, source);
            if (ClashEffect.getInstance().apply(game, source)) {
                for (UUID tokenId : effect.getLastAddedTokenIds()) {
                    Permanent token = game.getPermanent(tokenId);
                    if (token != null) {
                        ContinuousEffect continuousEffect = new GainAbilityTargetEffect(DeathtouchAbility.getInstance(), Duration.EndOfTurn);
                        continuousEffect.setTargetPointer(new FixedTarget(tokenId));
                        game.addEffect(continuousEffect, source);
                    }
                }
            }
            return true;
        }
        return false;
    }
}
