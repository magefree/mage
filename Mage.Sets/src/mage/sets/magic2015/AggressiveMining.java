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
package mage.sets.magic2015;

import java.util.UUID;
import mage.abilities.Ability;
import mage.abilities.common.LimitedTimesPerTurnActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.Cost;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetControlledPermanent;

/**
 *
 * @author Quercitron
 */
public class AggressiveMining extends CardImpl {

    public AggressiveMining(UUID ownerId) {
        super(ownerId, 127, "Aggressive Mining", Rarity.RARE, new CardType[]{CardType.ENCHANTMENT}, "{3}{R}");
        this.expansionSetCode = "M15";

        this.color.setRed(true);

        // You can't play lands.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new AggressiveMiningEffect()));
        
        // Sacrifice a land: Draw two cards.  Activate this ability only once each turn.
        Cost cost = new SacrificeTargetCost(new TargetControlledPermanent(new TargetControlledPermanent(new FilterControlledLandPermanent("a land"))));
        this.addAbility(new LimitedTimesPerTurnActivatedAbility(Zone.BATTLEFIELD, new DrawCardSourceControllerEffect(2), cost));
    }

    public AggressiveMining(final AggressiveMining card) {
        super(card);
    }

    @Override
    public AggressiveMining copy() {
        return new AggressiveMining(this);
    }
}

class AggressiveMiningEffect extends ContinuousRuleModifyingEffectImpl {

    public AggressiveMiningEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        this.staticText = "You can't play lands";
    }
    
    public AggressiveMiningEffect(final AggressiveMiningEffect effect) {
        super(effect);
    }

    @Override
    public AggressiveMiningEffect copy() {
        return new AggressiveMiningEffect(this);
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
        return event.getPlayerId().equals(source.getControllerId());
    }
    
}
