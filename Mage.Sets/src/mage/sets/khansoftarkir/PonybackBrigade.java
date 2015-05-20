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
package mage.sets.khansoftarkir;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.TriggeredAbilityImpl;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.keyword.MorphAbility;
import mage.cards.CardImpl;
import mage.constants.CardType;
import mage.constants.Rarity;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.game.events.GameEvent.EventType;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.GoblinToken;
import mage.game.permanent.token.Token;

/**
 *
 * @author LevelX2
 */
public class PonybackBrigade extends CardImpl {

    public PonybackBrigade(UUID ownerId) {
        super(ownerId, 191, "Ponyback Brigade", Rarity.COMMON, new CardType[]{CardType.CREATURE}, "{3}{R}{W}{B}");
        this.expansionSetCode = "KTK";
        this.subtype.add("Goblin");
        this.subtype.add("Warrior");

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // When Ponyback Brigade enters the battlefield or is turned face up, put three 1/1 red Goblin creature tokens onto the battlefield.
        this.addAbility(new PonybackBrigadeAbility(new GoblinToken(expansionSetCode)));

        // Morph {2}{R}{W}{B}
        this.addAbility(new MorphAbility(this, new ManaCostsImpl("{2}{R}{W}{B}")));
    }

    public PonybackBrigade(final PonybackBrigade card) {
        super(card);
    }

    @Override
    public PonybackBrigade copy() {
        return new PonybackBrigade(this);
    }
}

class PonybackBrigadeAbility extends TriggeredAbilityImpl {

    public PonybackBrigadeAbility(Token token) {
        super(Zone.BATTLEFIELD, new CreateTokenEffect(token, 3), false);
        this.setWorksFaceDown(true);
    }

    public PonybackBrigadeAbility(final PonybackBrigadeAbility ability) {
        super(ability);
    }

    @Override
    public PonybackBrigadeAbility copy() {
        return new PonybackBrigadeAbility(this);
    }

    @Override
    public boolean checkEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.TURNEDFACEUP || event.getType() == GameEvent.EventType.ENTERS_THE_BATTLEFIELD;
    }

    @Override
    public boolean checkTrigger(GameEvent event, Game game) {
        if (event.getType() == EventType.TURNEDFACEUP && event.getTargetId().equals(this.getSourceId())) {
            return true;
        }
        if (event.getType() == EventType.ENTERS_THE_BATTLEFIELD && event.getTargetId().equals(this.getSourceId()) ) {
            Permanent sourcePermanent = game.getPermanent(getSourceId());
            if (sourcePermanent != null && !sourcePermanent.isFaceDown(game)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getRule() {
        return "When {this} enters the battlefield or is turned face up, " + super.getRule();
    }
}
