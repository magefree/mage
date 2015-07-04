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
package mage.sets.betrayersofkamigawa;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class OgreMarauder extends CardImpl {

    public OgreMarauder(UUID ownerId) {
        super(ownerId, 75, "Ogre Marauder", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        this.expansionSetCode = "BOK";
        this.subtype.add("Ogre");
        this.subtype.add("Warrior");

        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Ogre Marauder attacks, it gains "Ogre Marauder can't be blocked" until end of turn unless defending player sacrifices a creature.
        this.addAbility(new AttacksTriggeredAbility(new OgreMarauderEffect(), false));
    }

    public OgreMarauder(final OgreMarauder card) {
        super(card);
    }

    @Override
    public OgreMarauder copy() {
        return new OgreMarauder(this);
    }
}

class OgreMarauderEffect extends OneShotEffect {

    public OgreMarauderEffect() {
        super(Outcome.Benefit);
        this.staticText = "it gains \"{this} can't be blocked\" until end of turn unless defending player sacrifices a creature";
    }

    public OgreMarauderEffect(final OgreMarauderEffect effect) {
        super(effect);
    }

    @Override
    public OgreMarauderEffect copy() {
        return new OgreMarauderEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        UUID defendingPlayerId = game.getCombat().getDefendingPlayerId(source.getSourceId(), game);
        MageObject sourceObject = game.getObject(source.getSourceId());
        Player defender = game.getPlayer(defendingPlayerId);
        if (defender != null && sourceObject != null) {
            Cost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
            if (cost.canPay(source, source.getSourceId(), defendingPlayerId, game) &&
                    defender.chooseUse(Outcome.LoseAbility, "Sacrifice a creature to prevent that " + sourceObject.getLogName() + " can't be blocked?", source, game)) {
                if (!cost.pay(source, game, source.getSourceId(), defendingPlayerId, false)) {
                    // cost was not payed - so source can't be blocked
                    ContinuousEffect effect = new CantBeBlockedSourceEffect(Duration.EndOfTurn);
                    game.addEffect(effect, source);
                }
            }
            return true;
        }
        return false;
    }
}
