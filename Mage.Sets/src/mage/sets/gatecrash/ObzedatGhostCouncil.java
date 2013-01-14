/*
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
package mage.sets.gatecrash;

import java.util.UUID;
import mage.Constants;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.DelayedTriggeredAbility;
import mage.abilities.common.BeginningOfYourEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ExileSourceEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.common.LoseLifeTargetEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderOwnerControlSourceEffect;
import mage.abilities.effects.common.ReturnToBattlefieldUnderYourControlSourceEffect;
import mage.abilities.effects.common.continious.GainAbilitySourceEffect;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetOpponent;

/**
 *
 * @author Plopman
 */
public class ObzedatGhostCouncil extends CardImpl<ObzedatGhostCouncil> {

    public ObzedatGhostCouncil(UUID ownerId) {
        super(ownerId, 182, "Obzedat, Ghost Council", Rarity.MYTHIC, new CardType[]{CardType.CREATURE}, "{1}{W}{W}{B}{B}");
        this.expansionSetCode = "GTC";
        this.subtype.add("Spirit");
        this.subtype.add("Advisor");
        this.supertype.add("Legendary");

        this.color.setBlack(true);
        this.color.setWhite(true);
        
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        //When Obzedat, Ghost Council enters the battlefield, target opponent loses 2 life and you gain 2 life.
        Ability ability = new EntersBattlefieldTriggeredAbility(new LoseLifeTargetEffect(2));
        ability.addEffect(new GainLifeEffect(2));
        ability.addTarget(new TargetOpponent(true));
        this.addAbility(ability);
        //At the beginning of your end step you may exile Obzedat. If you do, return it to the battlefield under it's owner's control at the beginning of your next upkeep. It gains haste.
        Ability ability2 = new BeginningOfYourEndStepTriggeredAbility(new ExileSourceEffect(), true);
        ability2.addEffect(new CreateDelayedTriggeredAbilityEffect(new BeginningOfYourUpkeepdelayTriggeredAbility()));
        this.addAbility(ability2);
    }

    public ObzedatGhostCouncil(final ObzedatGhostCouncil card) {
        super(card);
    }

    @Override
    public ObzedatGhostCouncil copy() {
        return new ObzedatGhostCouncil(this);
    }
}

class BeginningOfYourUpkeepdelayTriggeredAbility extends DelayedTriggeredAbility<BeginningOfYourUpkeepdelayTriggeredAbility> {

    public BeginningOfYourUpkeepdelayTriggeredAbility() {
        this(new ReturnToBattlefieldUnderOwnerControlSourceEffect(), Constants.TargetController.YOU);
        this.addEffect(new GainAbilitySourceEffect(HasteAbility.getInstance(), Constants.Duration.EndOfTurn));
    }

    public BeginningOfYourUpkeepdelayTriggeredAbility(Effect effect, Constants.TargetController targetController) {
        super(effect);
    }

    public BeginningOfYourUpkeepdelayTriggeredAbility(BeginningOfYourUpkeepdelayTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.UPKEEP_STEP_PRE && event.getPlayerId().equals(this.controllerId)) {
            return true;
        }
        return false;
    }

    @Override
    public BeginningOfYourUpkeepdelayTriggeredAbility copy() {
        return new BeginningOfYourUpkeepdelayTriggeredAbility(this);
    }

    @Override
    public String getRule() {
        return "If you do, return it to the battlefield under it's owner's control at the beginning of your next upkeep. It gains haste";
    }
}
