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
package mage.sets.mercadianmasques;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.SacrificeSourceUnlessPaysEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author fireshoes
 */
public class TerritorialDispute extends CardImpl {

    public TerritorialDispute(UUID ownerId) {
        super(ownerId, 217, "Territorial Dispute", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{4}{R}{R}");
        this.expansionSetCode = "MMQ";

        // At the beginning of your upkeep, sacrifice Territorial Dispute unless you sacrifice a land.
        this.addAbility(new BeginningOfUpkeepTriggeredAbility(Zone.BATTLEFIELD, 
                new SacrificeSourceUnlessPaysEffect(new SacrificeTargetCost(new TargetControlledPermanent(new FilterControlledLandPermanent("a land")))),
                TargetController.YOU, 
                false));
        
        // Players can't play lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new TerritorialDisputeEffect()));
    }

    public TerritorialDispute(final TerritorialDispute card) {
        super(card);
    }

    @Override
    public TerritorialDispute copy() {
        return new TerritorialDispute(this);
    }
}

class TerritorialDisputeEffect extends ContinuousRuleModifyingEffectImpl {

    public TerritorialDisputeEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Neutral);
        this.staticText = "Players can't play lands";
    }
    
    public TerritorialDisputeEffect(final TerritorialDisputeEffect effect) {
        super(effect);
    }

    @Override
    public TerritorialDisputeEffect copy() {
        return new TerritorialDisputeEffect(this);
    }
    
    @Override
    public boolean apply(Game game, Ability source) {
        return true;
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.PLAY_LAND;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        return true;
    }
    
}