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
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.delayed.AtTheEndOfCombatDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GeminiEngineTwinToken;
import mage.game.permanent.token.Token;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author TheElk801
 */
public class GeminiEngine extends CardImpl {

    public GeminiEngine(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{6}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Whenever Gemini Engine attacks, create a colorless Construct artifact creature token named Twin that's attacking. Its power is equal to Gemini Engine's power and its toughness is equal to Gemini Engine's toughness. Sacrifice the token at end of combat.
        this.addAbility(new AttacksTriggeredAbility(new GeminiEngineCreateTokenEffect(), false));
    }

    public GeminiEngine(final GeminiEngine card) {
        super(card);
    }

    @Override
    public GeminiEngine copy() {
        return new GeminiEngine(this);
    }
}

class GeminiEngineCreateTokenEffect extends OneShotEffect {

    GeminiEngineCreateTokenEffect() {
        super(Outcome.Benefit);
        this.staticText = "create a colorless Construct artifact creature token named Twin that's attacking. Its power is equal to {this}'s power and its toughness is equal to {this}'s toughness. Sacrifice the token at end of combat.";
    }

    GeminiEngineCreateTokenEffect(final GeminiEngineCreateTokenEffect effect) {
        super(effect);
    }

    @Override
    public GeminiEngineCreateTokenEffect copy() {
        return new GeminiEngineCreateTokenEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Permanent permanent = game.getPermanentOrLKIBattlefield(source.getSourceId());
        Token token;
        if (permanent != null) {
            token = new GeminiEngineTwinToken(permanent.getPower().getValue(), permanent.getToughness().getValue());
        } else {
            token = new GeminiEngineTwinToken(0, 0);
        }
        token.putOntoBattlefield(1, game, source.getSourceId(), source.getControllerId(), false, true);
        for (UUID tokenId : token.getLastAddedTokenIds()) {
            Permanent tokenPerm = game.getPermanent(tokenId);
            if (tokenPerm != null) {
                Effect effect = new SacrificeTargetEffect("sacrifice " + tokenPerm.getLogName(), player.getId());
                effect.setTargetPointer(new FixedTarget(tokenPerm, game));
                game.addDelayedTriggeredAbility(new AtTheEndOfCombatDelayedTriggeredAbility(effect), source);
            }
        }
        return true;
    }
}
