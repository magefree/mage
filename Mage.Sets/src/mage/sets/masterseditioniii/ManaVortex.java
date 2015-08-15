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
package mage.sets.masterseditioniii;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.StateTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.SacrificeEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;
import mage.game.stack.StackObject;
import mage.players.Player;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class ManaVortex extends CardImpl {
    
    public static final FilterLandPermanent filter = new FilterLandPermanent();

    public ManaVortex(UUID ownerId) {
        super(ownerId, 44, "Mana Vortex", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{1}{U}{U}");
        this.expansionSetCode = "ME3";

        // When you cast Mana Vortex, counter it unless you sacrifice a land.
        this.addAbility(new CastSourceTriggeredAbility(new CounterSourceEffect()));
        
        // At the beginning of each player's upkeep, that player sacrifices a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(new SacrificeEffect(new FilterLandPermanent(), 1, "that player"),
            TargetController.ANY, false));
        
        // When there are no lands on the battlefield, sacrifice Mana Vortex.
        this.addAbility(new ManaVortexStateTriggeredAbility());
    }

    public ManaVortex(final ManaVortex card) {
        super(card);
    }

    @Override
    public ManaVortex copy() {
        return new ManaVortex(this);
    }
}

class CounterSourceEffect extends OneShotEffect {

    public CounterSourceEffect() {
        super(Outcome.Detriment);
    }

    public CounterSourceEffect(final CounterSourceEffect effect) {
        super(effect);
    }

    @Override
    public CounterSourceEffect copy() {
        return new CounterSourceEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        
        StackObject spell = null;
        for(StackObject objet : game.getStack()){
            if(objet instanceof Spell && objet.getSourceId().equals(source.getSourceId())){
                spell = objet;
            }
        }
        if(spell != null){
                Player controller = game.getPlayer(source.getControllerId());
                if(controller.chooseUse(Outcome.Detriment, "Sacrifice a land to not counter " + spell.getName() + "?", source, game)){
                    SacrificeTargetCost cost = new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent()));
                    if(cost.pay(source, game, source.getSourceId(), source.getControllerId(), false)){
                        game.informPlayers(controller.getLogName() + " sacrifices a land to not counter " + spell.getName() + ".");
                        return true;
                    }
                    else {
                        game.getStack().counter(spell.getId(), source.getSourceId(), game);
                    }
                }
            return true;
        }
        return false;
    }

    @Override
    public String getText(Mode mode) {
        if (staticText != null && !staticText.isEmpty()) {
            return staticText;
        }
        return "counter it unless you sacrifice a land";
    }
}

class ManaVortexStateTriggeredAbility extends StateTriggeredAbility {

    public ManaVortexStateTriggeredAbility() {
        super(Zone.BATTLEFIELD, new SacrificeSourceEffect());
    }

    public ManaVortexStateTriggeredAbility(final ManaVortexStateTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public ManaVortexStateTriggeredAbility copy() {
        return new ManaVortexStateTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return game.getBattlefield().count(ManaVortex.filter, this.getSourceId(), this.getControllerId(), game) == 0;
    }

    @Override
    public String getRule() {
        return new StringBuilder("When there are no lands on the battlefield, ").append(super.getRule()).toString() ;
    }

}