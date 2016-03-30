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
package mage.sets.shadowsoverinnistrad;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.common.EntersBattlefieldAllTriggeredAbility;
import mage.abilities.common.delayed.AtTheBeginOfNextEndStepDelayedTriggeredAbility;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.CreateDelayedTriggeredAbilityEffect;
import mage.abilities.effects.common.ReturnSourceFromGraveyardToBattlefieldEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.permanent.ControllerPredicate;
import mage.game.Game;
import mage.game.events.EntersTheBattlefieldEvent;
import mage.game.events.GameEvent;

/**
 *
 * @author LevelX2
 */
public class PrizedAmalgam extends CardImpl {
    
    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("a creature");
    static {
        filter.add(new ControllerPredicate(TargetController.YOU));
    }

    public PrizedAmalgam(UUID ownerId) {
        super(ownerId, 249, "Prized Amalgam", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{1}{U}{B}");
        this.expansionSetCode = "SOI";
        this.subtype.add("Zombie");
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, return Prized Amalgam from your graveyard to the battlefield tapped at the beginning of the next end step.
        this.addAbility(new PrizedAmalgamTriggerdAbility(new CreateDelayedTriggeredAbilityEffect(
                new AtTheBeginOfNextEndStepDelayedTriggeredAbility(new ReturnSourceFromGraveyardToBattlefieldEffect(true))), filter));
    }

    public PrizedAmalgam(final PrizedAmalgam card) {
        super(card);
    }

    @Override
    public PrizedAmalgam copy() {
        return new PrizedAmalgam(this);
    }
}

class PrizedAmalgamTriggerdAbility extends EntersBattlefieldAllTriggeredAbility {

    public PrizedAmalgamTriggerdAbility(Effect effect, FilterPermanent filter) {
        super(Zone.GRAVEYARD, effect, filter, false);
    }

    public PrizedAmalgamTriggerdAbility(EntersBattlefieldAllTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public EntersBattlefieldAllTriggeredAbility copy() {
        return new PrizedAmalgamTriggerdAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        return super.checkTrigger(event, game) && ((EntersTheBattlefieldEvent) event).getFromZone().equals(Zone.GRAVEYARD);
    }

    @Override
    public String getRule() {
        return "Whenever a creature enters the battlefield, if it entered from your graveyard or you cast it from your graveyard, return {this} from your graveyard to the battlefield tapped at the beginning of the next end step.";
    }

}
