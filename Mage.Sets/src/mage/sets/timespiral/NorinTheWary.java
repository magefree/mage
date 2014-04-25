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
package mage.sets.timespiral;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.delayed.AtEndOfTurnDelayedTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ReturnFromExileEffect;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.players.Player;

/**
 *
 * @author LevelX2
 */
public class NorinTheWary extends CardImpl<NorinTheWary> {

    public NorinTheWary(UUID ownerId) {
        super(ownerId, 171, "Norin the Wary", Rarity.RARE, new CardType[]{CardType.CREATURE}, "{R}");
        this.expansionSetCode = "TSP";
        this.supertype.add("Legendary");
        this.subtype.add("Human");
        this.subtype.add("Warrior");

        this.color.setRed(true);
        this.power = new MageInt(2);
        this.toughness = new MageInt(1);

        // When a player casts a spell or a creature attacks, exile Norin the Wary. Return it to the battlefield under its owner's control at the beginning of the next end step.
        this.addAbility(new NorinTheWaryTriggeredAbility());
        
    }

    public NorinTheWary(final NorinTheWary card) {
        super(card);
    }

    @Override
    public NorinTheWary copy() {
        return new NorinTheWary(this);
    }
}

class NorinTheWaryTriggeredAbility extends TriggeredAbilityImpl<NorinTheWaryTriggeredAbility> {

    public NorinTheWaryTriggeredAbility() {
        super(Zone.BATTLEFIELD, new NorinTheWaryRemovingEffect(), false);
    }

    public NorinTheWaryTriggeredAbility(final NorinTheWaryTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        switch(event.getType()) {
            case SPELL_CAST:
            case ATTACKER_DECLARED:
                return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return new StringBuilder("When a player casts a spell or a creature attacks, ").append(super.getRule()).toString();
    }

    @Override
    public NorinTheWaryTriggeredAbility copy() {
        return new NorinTheWaryTriggeredAbility(this);
    }
}

class NorinTheWaryRemovingEffect extends OneShotEffect<NorinTheWaryRemovingEffect> {

    private static final String effectText = "exile {this}. Return it to the battlefield under its owner's control at the beginning of the next end step";

    NorinTheWaryRemovingEffect () {
        super(Outcome.Benefit);
        staticText = effectText;
    }

    NorinTheWaryRemovingEffect(NorinTheWaryRemovingEffect effect) {
        super(effect);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            Permanent permanent = game.getPermanent(source.getSourceId());
            if (permanent != null) {
                if (controller.moveCardToExileWithInfo(permanent, source.getSourceId(), permanent.getName(), source.getSourceId(), game, Zone.BATTLEFIELD)) {
                    //create delayed triggered ability
                    AtEndOfTurnDelayedTriggeredAbility delayedAbility = new AtEndOfTurnDelayedTriggeredAbility(
                            new ReturnFromExileEffect(source.getSourceId(), Zone.BATTLEFIELD));
                    delayedAbility.setSourceId(source.getSourceId());
                    delayedAbility.setControllerId(source.getControllerId());
                    game.addDelayedTriggeredAbility(delayedAbility);
                    return true;
                }
            }            
        }
        return false;
    }

    @Override
    public NorinTheWaryRemovingEffect copy() {
        return new NorinTheWaryRemovingEffect(this);
    }

}
