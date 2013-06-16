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

package mage.sets.returntoravnica;

import java.util.UUID;

import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.stack.Spell;

/**
 *
 * @author LevelX2
 */
public class LobberCrew extends CardImpl<LobberCrew> {

    public LobberCrew (UUID ownerId) {
        super(ownerId, 99, "Lobber Crew", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{2}{R}");
        this.expansionSetCode = "RTR";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");
        this.color.setRed(true);
        this.power = new MageInt(0);
        this.toughness = new MageInt(4);

        // Defender
        this.addAbility(DefenderAbility.getInstance());
        // {T}: Lobber Crew deals 1 damage to each opponent.
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new DamagePlayersEffect(1, TargetController.OPPONENT), new TapSourceCost()));
        // Whenever you cast a multicolored spell, untap Lobber Crew.
        this.addAbility(new LobberCrewTriggeredAbility());
    }

    public LobberCrew (final LobberCrew card) {
        super(card);
    }

    @Override
    public LobberCrew copy() {
        return new LobberCrew(this);
    }
}

class LobberCrewTriggeredAbility extends TriggeredAbilityImpl<LobberCrewTriggeredAbility> {
    public LobberCrewTriggeredAbility() {
        super(Zone.BATTLEFIELD, new UntapSourceEffect());
    }

    public LobberCrewTriggeredAbility(final LobberCrewTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public LobberCrewTriggeredAbility copy() {
        return new LobberCrewTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
         if (event.getType() == GameEvent.EventType.SPELL_CAST) {
            Spell spell = game.getStack().getSpell(event.getTargetId());
            if (spell != null && spell.getColor().isMulticolored() && event.getPlayerId().equals(getControllerId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever you cast a multicolored spell, untap {this}.";
    }
}