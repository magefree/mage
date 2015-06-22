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
package mage.sets.sorinvstibalt;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbility;
import mage.abilities.common.CantBlockAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.common.ManaWasSpentCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;
import mage.players.Player;
import mage.target.TargetPlayer;
import mage.watchers.common.ManaSpentToCastWatcher;

/**
 *
 * @author ilcartographer
 */
public class RevenantPatriarch extends CardImpl {

    public RevenantPatriarch(UUID ownerId) {
        super(ownerId, 16, "Revenant Patriarch", Rarity.UNCOMMON, new CardType[]{CardType.CREATURE}, "{4}{B}");
        this.expansionSetCode = "DDK";
        this.subtype.add("Spirit");
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // When Revenant Patriarch enters the battlefield, if {W} was spent to cast it, target player skips his or her next combat phase.
        TriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new TargetPlayerSkipNextCombatEffect(), false);
        ability.addTarget(new TargetPlayer());
        this.addAbility(new ConditionalTriggeredAbility(ability, new ManaWasSpentCondition(ColoredManaSymbol.W), 
                "if {W} was spent to cast it, target player skips his or her next combat phase."), new ManaSpentToCastWatcher());   
        // Revenant Patriarch can't block.
        this.addAbility(new CantBlockAbility());
    }

    public RevenantPatriarch(final RevenantPatriarch card) {
        super(card);
    }

    @Override
    public RevenantPatriarch copy() {
        return new RevenantPatriarch(this);
    }
}

class TargetPlayerSkipNextCombatEffect extends OneShotEffect {
    
    public TargetPlayerSkipNextCombatEffect() {
        super(Outcome.Detriment);
    }

    public TargetPlayerSkipNextCombatEffect(final TargetPlayerSkipNextCombatEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(getTargetPointer().getFirst(game, source));
        
        if (player != null) {
            game.getState().getTurnMods().add(new TurnMod(player.getId(), TurnPhase.COMBAT, null, true));
            return true;
        }
        
        return false;
    }

    @Override
    public TargetPlayerSkipNextCombatEffect copy() {
        return new TargetPlayerSkipNextCombatEffect(this);
    }
}
