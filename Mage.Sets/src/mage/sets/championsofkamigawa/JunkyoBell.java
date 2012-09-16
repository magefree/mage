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

package mage.sets.championsofkamigawa;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.dynamicvalue.common.PermanentsOnBattlefieldCount;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeTargetEffect;
import mage.abilities.effects.common.continious.BoostTargetEffect;
import mage.cards.CardImpl;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.common.FilterSpiritOrArcaneCard;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetControlledCreaturePermanent;
import mage.target.targetpointer.FixedTarget;

/**
 * @author LevelX
 */
public class JunkyoBell extends CardImpl<JunkyoBell> {

    public JunkyoBell(UUID ownerId) {
        super(ownerId, 258, "Junkyo Bell", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{4}");
        this.expansionSetCode = "CHK";
        
        // At the beginning of your upkeep, you may have target creature you control get +X/+X until end of turn, 
        // where X is the number of creatures you control. If you do, sacrifice that creature at the beginning of the next end step.
        PermanentsOnBattlefieldCount amount = new PermanentsOnBattlefieldCount(new FilterControlledCreaturePermanent());
        Ability ability = new BeginningOfUpkeepTriggeredAbility(new BoostTargetEffect(amount, amount, Constants.Duration.EndOfTurn), Constants.TargetController.YOU, true);
        ability.addTarget(new TargetControlledCreaturePermanent());
        ability.addEffect(new JunkyoBellSacrificeEffect());
        this.addAbility(ability);
    }

    public JunkyoBell(final JunkyoBell card) {
        super(card);
    }

    @Override
    public JunkyoBell copy() {
        return new JunkyoBell(this);
    }

    
private class JunkyoBellSacrificeEffect extends OneShotEffect<JunkyoBellSacrificeEffect> {

    public JunkyoBellSacrificeEffect() {
        super(Constants.Outcome.Sacrifice);
        this.staticText = "If you do, sacrifice that creature at the beginning of the next end step";
    }

    public JunkyoBellSacrificeEffect(final JunkyoBellSacrificeEffect effect) {
        super(effect);
    }

    @Override
    public JunkyoBellSacrificeEffect copy() {
        return new JunkyoBellSacrificeEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent creature = game.getPermanent(source.getFirstTarget());
        if (creature != null) {
            SacrificeTargetEffect sacrificeEffect = new SacrificeTargetEffect("sacrifice boosted " + creature.getName());
            sacrificeEffect.setTargetPointer(new FixedTarget(source.getFirstTarget()));
            DelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(sacrificeEffect);
            delayedAbility.setSourceId(source.getSourceId());
            delayedAbility.setControllerId(source.getControllerId());
            game.addDelayedTriggeredAbility(delayedAbility);
            return true;
        }
        return false;
    }
}
}