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
package mage.sets.antiquities;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.PhaseStep;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.target.common.TargetArtifactPermanent;
import mage.target.common.TargetControlledCreaturePermanent;

/**
 *
 * @author fireshoes
 */
public class GateToPhyrexia extends CardImpl {

    public GateToPhyrexia(UUID ownerId) {
        super(ownerId, 46, "Gate to Phyrexia", Rarity.UNCOMMON, new CardType[]{CardType.ENCHANTMENT}, "{B}{B}");
        this.expansionSetCode = "ATQ";

        // Sacrifice a creature: Destroy target artifact. Activate this ability only during your upkeep and only once each turn.
        Ability ability = new GateToPhyrexiaAbility(new DestroyTargetEffect(), new SacrificeTargetCost(new TargetControlledCreaturePermanent()));
        ability.addTarget(new TargetArtifactPermanent());
        this.addAbility(ability);
    }

    public GateToPhyrexia(final GateToPhyrexia card) {
        super(card);
    }

    @Override
    public GateToPhyrexia copy() {
        return new GateToPhyrexia(this);
    }
}

class GateToPhyrexiaAbility extends LimitedTimesPerTurnActivatedAbility {

    public GateToPhyrexiaAbility(Effect effect, Cost cost) {
        super(Zone.BATTLEFIELD, effect, cost);
    }

    public GateToPhyrexiaAbility(final GateToPhyrexiaAbility ability) {
        super(ability);
    }

    @Override
    public GateToPhyrexiaAbility copy() {
        return new GateToPhyrexiaAbility(this);
    }

    @Override
    public boolean canActivate(UUID playerId, Game game) {
        if (!game.getActivePlayerId().equals(controllerId) || !PhaseStep.UPKEEP.equals(game.getStep().getType())) {
            return false;
        }
        return super.canActivate(playerId, game);
    }

    @Override
    public String getRule() {
         return "Sacrifice a creature: Destroy target artifact. Activate this ability only during your upkeep and only once each turn.";
    }
}
