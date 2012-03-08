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
package mage.sets.darkascension;

import java.util.UUID;
import mage.Constants.CardType;
import mage.Constants.Rarity;
import mage.Constants.TargetController;
import mage.Constants.Zone;
import mage.MageInt;
import mage.abilities.TriggeredAbility;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.condition.common.TwoOrMoreSpellsWereCastLastTurnCondition;
import mage.abilities.decorator.ConditionalTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.targetpointer.FixedTarget;

/**
 *
 * @author North
 */
public class TovolarsMagehunter extends CardImpl<TovolarsMagehunter> {

    public TovolarsMagehunter(UUID ownerId) {
        super(ownerId, 98, "Tovolar's Magehunter", Rarity.RARE, new CardType[]{CardType.CREATURE}, "");
        this.expansionSetCode = "DKA";
        this.subtype.add("Werewolf");

        this.color.setRed(true);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // this card is the second face of double-faced card
        this.nightCard = true;
        this.canTransform = true;

        // Whenever an opponent casts a spell, Tovolar's Magehunter deals 2 damage to that player.
        this.addAbility(new TovolarsMagehunterTriggeredAbility());
        // At the beginning of each upkeep, if a player cast two or more spells last turn, transform Tovolar's Magehunter.
        TriggeredAbility ability = new BeginningOfUpkeepTriggeredAbility(new TransformSourceEffect(false), TargetController.ANY, false);
        this.addAbility(new ConditionalTriggeredAbility(ability,
                TwoOrMoreSpellsWereCastLastTurnCondition.getInstance(),
                TransformAbility.TWO_OR_MORE_SPELLS_TRANSFORM_RULE));
    }

    public TovolarsMagehunter(final TovolarsMagehunter card) {
        super(card);
    }

    @Override
    public TovolarsMagehunter copy() {
        return new TovolarsMagehunter(this);
    }
}

class TovolarsMagehunterTriggeredAbility extends TriggeredAbilityImpl<TovolarsMagehunterTriggeredAbility> {

    public TovolarsMagehunterTriggeredAbility() {
        super(Zone.BATTLEFIELD, new DamageTargetEffect(2), false);
    }

    public TovolarsMagehunterTriggeredAbility(final TovolarsMagehunterTriggeredAbility ability) {
        super(ability);
    }

    @Override
    public TovolarsMagehunterTriggeredAbility copy() {
        return new TovolarsMagehunterTriggeredAbility(this);
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == GameEvent.EventType.SPELL_CAST && game.getOpponents(controllerId).contains(event.getPlayerId())) {
            this.getEffects().get(0).setTargetPointer(new FixedTarget(event.getPlayerId()));
            return true;
        }
        return false;
    }

    @Override
    public String getRule() {
        return "Whenever an opponent casts a spell, {this} deals 2 damage to that player.";
    }
}
