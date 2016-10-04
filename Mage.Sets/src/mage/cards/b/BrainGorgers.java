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
package mage.sets.planarchaos;

import java.util.UUID;
import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.keyword.MadnessAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public class BrainGorgers extends CardImpl {

    public BrainGorgers(UUID ownerId) {
        super(ownerId, 65, "Brain Gorgers", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.expansionSetCode = "PLC";
        this.subtype.add("Zombie");
        this.power = new MageInt(4);
        this.toughness = new MageInt(2);

        // When you cast Brain Gorgers, any player may sacrifice a creature. If a player does, counter Brain Gorgers.
        this.addAbility(new CastSourceTriggeredAbility(new BrainGorgersCounterSourceEffect()));

        // Madness {1}{B}
        this.addAbility(new MadnessAbility(this, new ManaCostsImpl<>("{1}{B}")));
    }

    public BrainGorgers(final BrainGorgers card) {
        super(card);
    }

    @Override
    public BrainGorgers copy() {
        return new BrainGorgers(this);
    }
}

class BrainGorgersCounterSourceEffect extends OneShotEffect {

    public BrainGorgersCounterSourceEffect() {
        super(Outcome.AIDontUseIt);
        staticText = "any player may sacrifice a creature. If a player does, counter {source}";
    }

    public BrainGorgersCounterSourceEffect(final BrainGorgersCounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public BrainGorgersCounterSourceEffect copy() {
        return new BrainGorgersCounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        if (sourceObject != null) {
            SacrificeTargetCost cost = new SacrificeTargetCost(new TargetControlledCreaturePermanent());
            for (UUID playerId : game.getState().getPlayerList(source.getControllerId())) {
                cost.clearPaid();
                Player player = game.getPlayer(playerId);
                if (cost.canPay(source, source.getSourceId(), player.getId(), game)
                        && player.chooseUse(outcome, "Sacrifice a creature to counter " + sourceObject.getIdName() + "?", source, game)) {
                    if (cost.pay(source, game, source.getSourceId(), player.getId(), false, null)) {
                        game.informPlayers(player.getLogName() + " sacrifices a creature to counter " + sourceObject.getIdName() + ".");
                        Spell spell = game.getStack().getSpell(source.getSourceId());
                        if (spell != null) {
                            game.getStack().counter(spell.getId(), source.getSourceId(), game);
                        }
                    }
                }
            }
            return true;
        }
        return false;

    }
}
