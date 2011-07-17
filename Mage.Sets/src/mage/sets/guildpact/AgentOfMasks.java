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
package mage.sets.guildpact;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.game.Game;

/**
 *
 * @author Loki
 */
public class AgentOfMasks extends CardImpl<AgentOfMasks> {

    public AgentOfMasks(UUID ownerId) {
        super(ownerId, 100, "Agent of Masks", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{3}{W}{B}");
        this.expansionSetCode = "GPT";
        this.subtype.add("Human");
        this.subtype.add("Advisor");
        this.color.setBlack(true);
        this.color.setWhite(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new AgentOfMasksEffect(), Constants.TargetController.YOU, false));
    }

    public AgentOfMasks(final AgentOfMasks card) {
        super(card);
    }

    @Override
    public AgentOfMasks copy() {
        return new AgentOfMasks(this);
    }
}

class AgentOfMasksEffect extends OneShotEffect<AgentOfMasksEffect> {
    public AgentOfMasksEffect() {
        super(Constants.Outcome.Damage);
    }

    public AgentOfMasksEffect(final AgentOfMasksEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int loseLife = 0;
        for (UUID opponentId : game.getOpponents(source.getControllerId())) {
            loseLife += game.getPlayer(opponentId).loseLife(1, game);
        }
        if (loseLife > 0)
            game.getPlayer(source.getControllerId()).gainLife(loseLife, game);
        return true;
    }

    @Override
    public AgentOfMasksEffect copy() {
        return new AgentOfMasksEffect(this);
    }

    @Override
    public String getText(Ability source) {
        return "each opponent loses 1 life. You gain life equal to the life lost this way";
    }
}