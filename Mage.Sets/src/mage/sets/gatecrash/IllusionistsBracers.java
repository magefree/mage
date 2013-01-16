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
import mage.Constants.CardType;
import mage.Constants.Outcome;
import mage.Constants.Rarity;
import mage.Constants.Zone;
import mage.abilities.Ability;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.GenericManaCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.keyword.EquipAbility;
import mage.abilities.mana.ManaAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.permanent.Permanent;
import mage.game.stack.StackAbility;
import mage.players.Player;

/**
*
* @author LevelX2
*/
public class IllusionistsBracers extends CardImpl<IllusionistsBracers> {

    public IllusionistsBracers(UUID ownerId) {
       super(ownerId, 231, "Illusionist's Bracers", Rarity.RARE, new CardType[]{CardType.ARTIFACT}, "{2}");
       this.expansionSetCode = "GTC";
       this.subtype.add("Equipment");

       // Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.
       this.addAbility(new AbilityActivatedTriggeredAbility());

       // Equip 3
       this.addAbility(new EquipAbility(Outcome.BoostCreature, new GenericManaCost(3)));
    }

    public IllusionistsBracers(final IllusionistsBracers card) {
        super(card);
    }

    @Override
    public IllusionistsBracers copy() {
        return new IllusionistsBracers(this);
    }
}

class AbilityActivatedTriggeredAbility extends TriggeredAbilityImpl<AbilityActivatedTriggeredAbility> {
    AbilityActivatedTriggeredAbility() {
        super(Zone.BATTLEFIELD, new CopyActivatedAbilityEffect());
    }

    AbilityActivatedTriggeredAbility(final AbilityActivatedTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public AbilityActivatedTriggeredAbility copy() {
        return new AbilityActivatedTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.ACTIVATED_ABILITY) {
            Permanent equipment = game.getPermanent(this.getSourceId());
            if (equipment != null && equipment.getAttachedTo() != null && equipment.getAttachedTo().equals(event.getSourceId())) {
                StackAbility stackAbility = (StackAbility) game.getStack().getStackObject(event.getSourceId());
                if (!(stackAbility.getStackAbility() instanceof ManaAbility)) {
                    Effect effect = this.getEffects().get(0);
                    effect.setValue("stackAbility", stackAbility.getStackAbility());
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public String getRule() {
       return "Whenever an ability of equipped creature is activated, if it isn't a mana ability, copy that ability. You may choose new targets for the copy.";
    }
}

class CopyActivatedAbilityEffect extends OneShotEffect<CopyActivatedAbilityEffect> {

    public CopyActivatedAbilityEffect() {
        super(Outcome.Benefit);
        this.staticText = "copy that ability. You may choose new targets for the copy";
    }

    public CopyActivatedAbilityEffect(final CopyActivatedAbilityEffect effect) {
        super(effect);
    }

    @Override
    public CopyActivatedAbilityEffect copy() {
        return new CopyActivatedAbilityEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Ability ability = (Ability) getValue("stackAbility");
        Player controller = game.getPlayer(source.getControllerId());
        Permanent sourcePermanent = game.getPermanent(source.getSourceId());
        if (ability != null && controller != null && sourcePermanent != null) {
            Ability newAbility = ability.copy();
            newAbility.newId();
            game.getStack().push(new StackAbility(newAbility, source.getControllerId()));
            if (newAbility.getTargets().size() > 0) {
                if (controller.chooseUse(newAbility.getEffects().get(0).getOutcome(), "Choose new targets?", game)) {
                    newAbility.getTargets().clearChosen();
                    if (newAbility.getTargets().chooseTargets(newAbility.getEffects().get(0).getOutcome(), source.getControllerId(), newAbility, game) == false) {
                        return false;
                    }
                }
            }
            game.informPlayers(new StringBuilder(sourcePermanent.getName()).append(": ").append(controller.getName()).append(" copied activated ability").toString());
            return true;
        }
        return false;
    }
}
